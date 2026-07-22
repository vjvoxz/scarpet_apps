// ================================================================================
// Custom Nether Portals v1.1.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  Replaces vanilla's rectangle-only portal validation with a flood-fill
//  frame search, allowing (optionally) non-rectangular obsidian frames,
//  crying obsidian frames, and configurable min/max frame sizes.
//
//  Original datapack used AreaEffectCloud markers as a "visited" set for its
//  mcfunction flood fill. In Scarpet we just use a native map instead, which
//  is both simpler and doesn't leave any entities behind.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Custom Nether Portals" data pack/mod 
//  by Vanilla Tweaks.
//      https://vanillatweaks.net/
//  This script doesn't copy any of the pack/mod files but provides credit to the 
//  creator, their rights and license terms still apply to the underlying idea.
// --------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet port created by vjvoxz
//  - License: MIT (see https://mit-license.org/)
//  - This script was generated with assistance from Claude, an AI model created 
//    by Anthropic.
// --------------------------------------------------------------------------------
// See CHANGELOG.md for version history and README.md for full app info.
// ================================================================================

// Define subcommands for /custom_nether_portals
__config() -> {
    'scope' -> 'global',
    'stay_loaded' -> true,
    'command_permission' -> 'ops',
    'commands' -> {
        '' -> '_cmd_info',
        'non_rectangular <enabled>' -> '_cmd_set_non_rectangular',
        'crying_obsidian <enabled>' -> '_cmd_set_crying_obsidian',
        'min_size <value>' -> '_cmd_set_min_size',
        'max_size <value>' -> '_cmd_set_max_size',
        'uninstall' -> '_cmd_uninstall'
    },
    'arguments' -> {
        'enabled' -> {
            'type' -> 'bool'
        },
        'value' -> {
            'type' -> 'int',
            'min' -> 1,
            'max' -> 512
        }
    }
};

// ----------------------------------------------------------------------
// Settings
// ----------------------------------------------------------------------
// Default settings for the app, used when no settings.json exists
default_settings() -> {
    'non_rectangular' -> true,
    'crying_obsidian' -> true,
    'min_size' -> 10,
    'max_size' -> 84
};

// Saves the current settings to settings.json
save_settings() -> write_file('settings.json', encode_json(global_settings, true));

// Loads the settings from settings.json, or returns default settings if the file doesn't exist
__on_start() -> (
   defaults = default_settings(); 
   loaded = read_file('settings.json'); 
   
   global_settings = if (loaded != null, parse_json(loaded), copy(defaults));
   
   for (keys(defaults),
      if (!has(global_settings, _), put(global_settings, _, defaults: _)))
);

__on_close() -> save_settings();

// ----------------------------------------------------------------------
// Frame / flood-fill logic
// ----------------------------------------------------------------------

// A frame block is obsidian, or (if enabled) crying obsidian
is_frame(pos) -> (
    b = block(pos); b == 'obsidian' || (global_settings: 'crying_obsidian' && b == 'crying_obsidian')
);

// A position is "open" (part of the portal interior) if it's air, or the
// original fire block that triggered the ignition
is_open(pos) -> air(pos) || block(pos) == 'fire';

// Returns the 4 neighbours to explore within the plane of a given axis.
// axis == 'x' -> plane spans x/y, z fixed (classic east-west facing portal)
// axis == 'z' -> plane spans z/y, x fixed
axis_neighbours(pos, axis) -> (
    [px, py, pz] = pos;
    if (axis == 'x',
        [
            [px + 1, py, pz],
            [px - 1, py, pz],
            [px, py + 1, pz],
            [px, py - 1, pz]
        ],
        [
            [px, py, pz + 1],
            [px, py, pz - 1],
            [px, py + 1, pz],
            [px, py - 1, pz]
        ]
    )
);

// Checks that the interior forms a perfect filled rectangle within its
// bounding box - used when non_rectangular frames are disabled, mirroring
// the original datapack's diagonal-corner check.
is_rectangular(interior, axis) -> (
    coords = map(interior,
        if (axis == 'x', [_: 0, _: 1], [_: 2, _: 1])); us = map(coords, _: 0); vs = map(coords, _: 1); width = max(us) - min(us) + 1; height = max(vs) - min(vs) + 1; length(interior) == width * height
);

// Attempts to flood-fill a portal from fire_pos along the given axis.
// Returns the list of interior (portal) positions if valid, or null.
attempt_portal(fire_pos, axis) -> (
    visited = {}; interior = []; frame_found = false; valid = true; queue = [fire_pos]; put(visited, str(fire_pos), true); head = 0; steps = 0; hard_cap = max(global_settings: 'max_size' * 4, 1024);

    // Append-only BFS: we never remove from `queue`, just advance `head`.
    // This avoids relying on slice()/delete() negative-index semantics for
    // "popping", which don't behave like get()'s negative indexing and were
    // silently corrupting the search in the stack-based version.
    while (head < length(queue) && valid, hard_cap + 1,
        pos = queue: head; head += 1; steps += 1;
        if (steps > hard_cap,
            valid = false,
            if (is_frame(pos),
                frame_found = true,
                if (!is_open(pos),
                    valid = false,
                    put(interior, null, pos);
                    if (length(interior) > global_settings: 'max_size',
                        valid = false,
                        for (axis_neighbours(pos, axis),
                            nk = str(_);
                            if (!has(visited, nk),
                                put(visited, nk, true); put(queue, null, _)
                            )
                        )
                    )
                )
            )
        )
    );

    if (!valid || !frame_found || length(interior) < global_settings: 'min_size',
        null,
        if (!global_settings: 'non_rectangular' && !is_rectangular(interior, axis),
            null,
            interior
        )
    )
);

// ----------------------------------------------------------------------
// Ignition detection
// ----------------------------------------------------------------------

__on_player_right_clicks_block(player, item_tuple, hand, block, face, hitvec) -> (
    if (item_tuple,
        item_name = item_tuple: 0;
        if (item_name == 'flint_and_steel' || item_name == 'fire_charge',
            target = pos_offset(block, face); schedule(1, '_try_ignite', target)
        )
    )
);

_try_ignite(pos) -> (
    if (block(pos) == 'fire',
        axis = 'x'; result = attempt_portal(pos, axis);
        if (result == null,
            axis = 'z'; result = attempt_portal(pos, axis);
        );
        if (result != null,
            for (result, set(_, 'nether_portal', 'axis', axis))
        )
    )
);

// ----------------------------------------------------------------------
// Commands (/custom_nether_portals ...)
// ----------------------------------------------------------------------

_cmd_info() -> (
    print(player(), format(
        'db Custom Nether Portals'
    )); print(player(), format(
        'gi Ignite custom nether portals by allowing (configurable) non-rectangular obsidian frames, crying obsidian frames, and configurable min/max frame sizes.'
    )); print(player(), format(
        'f Non-rectangular frames: ', str('w %s', global_settings: 'non_rectangular')
    )); print(player(), format(
        'f Crying obsidian frames: ', str('w %s', global_settings: 'crying_obsidian')
    )); print(player(), format(
        'f Min frame size: ', str('w %d', global_settings: 'min_size')
    )); print(player(), format(
        'f Max frame size: ', str('w %d', global_settings: 'max_size')
    )); print(player(), format(
        'gi Created by: vjvoxz (Ported from Vanilla Tweaks)'
    )); print(player(), format(
        'p License: MIT'
    ));
);

_cmd_set_non_rectangular(enabled) -> (
    global_settings: 'non_rectangular' = enabled; save_settings(); print(player(),'db [CNP] ', 'Non-rectangular frames: ' + enabled);
);

_cmd_set_crying_obsidian(enabled) -> (
    global_settings: 'crying_obsidian' = enabled; save_settings(); print(player(),'db [CNP] ', 'Crying obsidian frames: ' + enabled);
);

_cmd_set_min_size(value) -> (
    global_settings: 'min_size' = value; save_settings(); print(player(),'db [CNP] ', 'Min frame size set to ' + value);
);

_cmd_set_max_size(value) -> (
    global_settings: 'max_size' = value; save_settings(); print(player(),'db [CNP] ', 'Max frame size set to ' + value);
);

_cmd_uninstall() -> (
    delete_file('settings.json'); print(player(),'db [CNP] ', 'Custom Nether Portals data cleared. Run /script unload custom_nether_portals to remove the app.');
);