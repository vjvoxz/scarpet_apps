// ================================================================================
// Boat Froster v1.1.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP: 
//  The frost walker enchantment (on a rider's boots) now also works
//  while that player is riding in a boat, turning nearby water into
//  frosted_ice as the boat travels over it. While the boat is stationary,
//  frosted ice created this way will not melt/decay. This special "won't
//  decay while not moving" protection ONLY applies to ice frozen from a boat.
//
//  - Frost Walker used normally on foot behaves exactly like vanilla and
//  still decays as usual.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Boat Froster" data pack/mod 
//  by enchanted-games, under license CC BY-NC 4.0.
//       https://modrinth.com/datapack/boat-froster
//       https://modrinth.com/user/enchanted-games
//  This script doesn't copy any of the pack/mod files but provides credit 
//  to the creator, their rights and license terms still apply to the 
//  underlying idea.
// --------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet port created by vjvoxz
//  - License: MIT (see https://mit-license.org/)
//  - This script was generated with assistance from Claude, an AI model created 
//    by Anthropic.
// --------------------------------------------------------------------------------
// See CHANGELOG.md for version history and README.md for full app info.
// ================================================================================

// Global configuration state
global_bf_enchant_id   = 'frost_walker';
global_bf_base_radius  = 2;     // vanilla-like footprint radius offset (level + 2)
global_bf_grace_ticks  = 60;    // how long a frozen tile stays protected after last refresh
global_bf_tiles        = {};    // 'dimension|x,y,z' -> ticks remaining of decay protection

// ---- DEBUG -------------------------------------------------------------
// Set to true to get chat + console messages tracing boat/enchant
// detection and freeze results - useful if this ever needs troubleshooting
// again on a future MC/Carpet version.
global_bf_debug        = false;
// Only print the expensive per-tile debug info once every N ticks per
// player, so a stationary boat doesn't flood the chat.
global_bf_debug_every  = 20;
global_bf_debug_clock  = {};    // player name -> next tick allowed to print

// Define subcommands for /boat_froster
__config() -> {
   'scope' -> 'global',
   'commands' -> {
      ''     -> '_bf_info',
      'info' -> '_bf_info',
      'debug <state>' -> '_bf_debug'
    },
    'arguments' -> {
        'state' -> { 'type' -> 'bool' }
    }
};

//----------------------------------------------------------------------
// Command interface: /boat_froster [info|debug <state>]
//----------------------------------------------------------------------
// Registers the /boat_froster info command
_bf_info() -> (
    print(format('db Boat Froster'));
    print(format('gi The frost walker enchantment now also works while that player is riding in a boat'));
    print(format('gi Created by: vjvoxz (Ported from enchanted-games data pack)'));
    print(format('p License: MIT'));
);

// Command handler: explicitly sets debug mode on/off
_bf_debug(state) -> (
   global_bf_debug = state;
   print(format('db [BF]', 'w Debug tracing is now ', if(global_bf_debug, 'lb ON', 'rb OFF')));
);

// Logging helper, used throughout the app wherever debug output is needed
_bf_log(p, msg) -> (
   if (global_bf_debug,
      print(p, format('db [BF] ', 'w ' + msg));
      logger('[BF] ' + msg);
   )
);

// throttled variant for stuff that would otherwise repeat every tick
_bf_debug_throttled(p, msg) -> (
   if (global_bf_debug,
      name = query(p, 'name');
      next = global_bf_debug_clock:name;
      now = tick_time();
      if (next == null || now >= next,
         global_bf_debug_clock:name = now + global_bf_debug_every;
         _bf_log(p, msg);
      );
   )
);

//------------------------------------------------------------------ helpers

_bf_key(dim, pos) -> dim + '|' + str('%d,%d,%d', floor(pos:0), floor(pos:1), floor(pos:2));

_bf_parse_key(key) -> (
   parts = split(key, '|');
   dim = parts:0;
   coords = map(split(parts:1, ','), number(_));
   [dim, coords]
);

// Reads the level of a given enchantment id from an item triple [name, count, tag]
// Supports both the modern (1.20.5+) data component format and the legacy NBT format.
_bf_enchant_level(item_tuple, id) -> (
   if (item_tuple == null, return(0));
   tag = item_tuple:2;
   if (tag == null, return(0));

   // modern data-component format, nested under 'components'
   // (the enchantments component is itself a plain id -> level map)
   levels = tag:'components':'minecraft:enchantments';
   if (levels != null,
      lvl = levels:('minecraft:' + id);
      if (lvl == null, lvl = levels:id);
      if (lvl != null, return(number(lvl)));
   );

   // modern data-component format, exposed at the top level (no 'components' wrapper)
   levels = tag:'minecraft:enchantments';
   if (levels != null,
      lvl = levels:('minecraft:' + id);
      if (lvl == null, lvl = levels:id);
      if (lvl != null, return(number(lvl)));
   );

   // legacy NBT format (pre 1.20.5)
   list = tag:'Enchantments';
   if (list == null, list = tag:'ench');
   if (list == null, return(0));
   result = 0;
   for (list,
      eid = str(_:'id');
      if (eid == id || eid == 'minecraft:' + id, result = number(_:'lvl'));
   );
   result
);

_bf_frost_walker_level(p) -> _bf_enchant_level(query(p, 'holds', 'feet'), global_bf_enchant_id);

// Dumps the raw NBT of the boots so we can see how this MC version actually
// structures enchantment data if level detection keeps returning 0.
_bf_debug_dump_boots(p) -> (
   item_tuple = query(p, 'holds', 'feet');
   if (item_tuple == null,
      _bf_log(p, 'no boots equipped');
   ,
      tag = item_tuple:2;
      _bf_log(p, 'boots = ' + item_tuple:0 + ', tag = ' + str(tag));
   )
);

_bf_ends_with(s, suffix) -> (
   n = length(suffix);
   length(s) >= n && slice(s, -n) == suffix
);

_bf_is_boat(e) -> (
   if (e == null, return(false));
   t = str(query(e, 'type'));
   // matches 'boat', 'chest_boat', wood-specific variants like 'oak_boat',
   // and bamboo's differently-named 'raft' / 'chest_raft' entities.
   t == 'boat' || t == 'chest_boat' || t == 'raft' || t == 'chest_raft'
      || _bf_ends_with(t, '_boat') || _bf_ends_with(t, '_raft')
);

// Looks for a water block near the boat's waterline: checks the block at the
// boat's y position and a couple of blocks below (boats float slightly above
// the surface, so the actual water block is usually 1 below the boat's feet).
_bf_water_surface_at(x, y, z) -> (
   ty = round(y);
   result = null;
   loop(3,
      if (result == null,
         py = ty - _;
         wp = [x, py, z];
         if (block(wp) == 'water', result = wp);
      );
   );
   result
);

// Freezes the footprint around a boat, exactly like Frost Walker would for a
// walking player, and registers the frozen tiles so their decay can be
// suppressed later while the boat stays put.
_bf_freeze_ring(boat, p, dim) -> (
   lvl = _bf_frost_walker_level(p);
   if (lvl <= 0, return());
   radius = lvl + global_bf_base_radius;
   side = 2 * radius + 1;
   [cx, cy, cz] = pos(boat);
   icx = round(cx); icz = round(cz);
   checked = 0; water_found = 0; frozen = 0;
   loop(side * side,
      i = _;
      dx = i % side - radius;
      dz = floor(i / side) - radius;
      if (dx * dx + dz * dz <= radius * radius,
         checked += 1;
         wp = _bf_water_surface_at(icx + dx, cy, icz + dz);
         if (wp != null,
            water_found += 1;
            result = set(wp, 'frosted_ice', 'age', 0);
            if (result != false, frozen += 1);
            global_bf_tiles:(_bf_key(dim, wp)) = global_bf_grace_ticks;
         )
      )
   );
   _bf_debug_throttled(p, str('freeze_ring: lvl=%d radius=%d boatpos=[%d,%d,%d] checked=%d water_found=%d frozen=%d',
      lvl, radius, cx, cy, cz, checked, water_found, frozen));
);

//------------------------------------------------------------------ startup

__on_start() -> (
   logger('info', '[BF] boat_froster app started/loaded, debug=' + global_bf_debug);
   for (player('all'), print(_, format('db [BF] ', 'w boat_froster loaded (debug mode: ' + global_bf_debug + ')')));
);

//------------------------------------------------------------------ main tick

global_bf_heartbeat = 0;

__on_tick() -> (
   // console-visible heartbeat every 100 ticks (~5s) so we can confirm the
   // tick event is actually bound and firing, independent of any player
   // being in a boat.
   global_bf_heartbeat += 1;
   if (global_bf_debug && global_bf_heartbeat >= 100,
      global_bf_heartbeat = 0;
      logger('[BF] heartbeat - tick alive, players online: ' + length(player('all')));
   );

   // 1) let every boating player with Frost Walker boots freeze/refresh their footprint
   for (player('all'),
      p = _;
      if (query(p, 'is_riding'),
         boat = query(p, 'mount');
         is_boat = _bf_is_boat(boat);
         lvl = _bf_frost_walker_level(p);
         _bf_debug_throttled(p, str('riding entity type=%s is_boat=%s frost_walker_lvl=%d', query(boat, 'type'), is_boat, lvl));
         if (lvl <= 0, _bf_debug_dump_boots(p));
         if (is_boat && lvl > 0,
            dim = query(p, 'dimension');
            in_dimension(dim, _bf_freeze_ring(boat, p, dim));

            motion = query(boat, 'motion');
            speed_sq = motion:0 * motion:0 + motion:2 * motion:2;
            if (speed_sq < 0.0004, // boat is essentially stationary
               // refresh every tracked tile in this dimension so its age
               // never advances far enough for vanilla to melt it
               in_dimension(dim,
                  for (keys(global_bf_tiles),
                     key = _;
                     [tdim, coords] = _bf_parse_key(key);
                     if (tdim == dim && block(coords) == 'frosted_ice',
                        set(coords, 'frosted_ice', 'age', 0);
                        global_bf_tiles:key = global_bf_grace_ticks;
                     )
                  )
               )
            )
         )
      )
   );

   // 2) age out bookkeeping - once a tile stops being refreshed for long
   //    enough (boat moved away / sank / was removed), forget about it and
   //    let vanilla decay take over normally again.
   for (keys(global_bf_tiles),
      key = _;
      remaining = global_bf_tiles:key - 1;
      if (remaining <= 0,
         delete(global_bf_tiles, key);
      ,
         global_bf_tiles:key = remaining;
      )
   );
);