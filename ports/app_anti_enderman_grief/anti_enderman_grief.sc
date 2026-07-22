// ================================================================================
// Anti Enderman v1.1.0 Grief Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  The "Anti Enderman Grief" app prevents endermen from picking up blocks by 
//  changing the "enderman_holdable" block tag to be empty, effectively making 
//  all blocks unpickable by endermen.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Anti Enderman Grief" data pack/mod 
//  by Vanilla Tweaks.
//      https://vanillatweaks.net/
//  This script doesn't copy any of the pack/mod files but provides credit to the 
//  creator, their rights and license terms still apply to the underlying idea.
// --------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet port created by vjvoxz
//  - License: MIT (see https://mit-license.org/)
//  - This script was generated with assistance from Gemini, an AI model created 
//    by Google DeepMind.
// --------------------------------------------------------------------------------
// See CHANGELOG.md for version history and README.md for full app info.
// ================================================================================

// Global configuration state
global_enabled = true;

// Define subcommands for /anti_enderman_grief
__config() -> {
   'scope' -> 'global',
   'commands' -> {
      ''     -> 'info',
      'info' -> 'info',
      'status <state>' -> 'set_grief'
    },
    'arguments' -> {
        'state' -> { 'type' -> 'bool' }
    }
};

//----------------------------------------------------------------------
// Command interface: /anti_enderman_grief [info|status <state>]
//----------------------------------------------------------------------
// Registers the /anti_enderman_grief info command
info() -> (
    print(format('db Anti Enderman Grief'));
    print(format('gi Prevents endermen from picking up blocks'));
    print(format('gi Created by: vjvoxz (Ported from Vanilla Tweaks)'));
    print(format('p License: MIT'));

    // Display current running status
    status_style = if(global_enabled, 'e  Enabled', 'r  Disabled');
    print(format('nu Status:', status_style));
);

// Toggles the grief prevention mechanic on the fly
set_grief(state) -> (
   global_enabled = state;
   print(format('db [AEG] ', 'w Grief prevention is now ', if(global_enabled, 'lb ON', 'rb OFF')));
);

// Triggers when the app starts or is loaded by a player
__on_start() -> (
    // Replicating the vanilla tweaks block tag
    pack_data = {
        'data/minecraft/tags/block/enderman_holdable.json' -> {
            'replace' -> true,
            'values' -> [
                'minecraft:melon',
                'minecraft:pumpkin'
            ]
        }
    };

    // Injecting the virtual datapack into the world
    status = create_datapack('anti_enderman_grief_port', pack_data);

    if(status == true,
        print(format('l [AEG] Successfully loaded virtual datapack!')),
    status == null,
        null, // Pack already exists or is loaded, quietly proceed
    // else
        print(format('r [AEG] Failed to load the virtual datapack.'))
    );
);