// ================================================================================
// Cauldron Converter v1.4.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  A Scarpet recreation of the "convert concrete powder in a water cauldron" 
//  and "convert dirt in a water cauldron" datapacks. Vanilla Minecraft already 
//  turns concrete powder into solid concrete when it touches a water *source block* 
//  - this app extends that same conversion to concrete powder items that are 
//  sitting in (or dropped into) a water cauldron.
//
//  Original datapack mechanics being reproduced here:
//   - schedule_1s.mcfunction   → _cauldron_scan(), re-scheduled every
//                                  20 ticks (1s) via schedule()
//   - tags/item/convertible    → global_convert_map (powder → block, dirt → mud)
//   - item_modifier/convert    → modify(item_entity, 'item', ...)
//   - tags/function/load       → __on_start()
//   - uninstall.mcfunction     → __on_close() / global_running flag
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of "Cauldron Concrete Powder" 
//  and "Cauldron Mud" data packs by Vanilla Tweaks.
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

// Define subcommands for /cauldron_converter
__config() -> {
   'scope' -> 'global',
   'commands' -> {
      ''     -> 'info',
      'info' -> 'info',
      'debug <state>' -> 'set_debug'
    },
    'arguments' -> {
        'state' -> { 'type' -> 'bool' }
    }
};

//----------------------------------------------------------------------
// Command interface: /cauldron_converter [info|debug <state>]
//----------------------------------------------------------------------
// Registers the /cauldron_converter info command
info() -> (
    print(format('db Cauldron Converter'));
    print(format('gi Dropping concrete powder into a water cauldron hardens it instantly.'));
    print(format('gi Dropping dirt into a water cauldron turns it into mud.'));
    print(format('gi Created by: vjvoxz (Ported from Vanilla Tweaks)'));
    print(format('p License: MIT'));
);

// Registers the /cauldron_converter debug command
set_debug(state) -> (
   global_debug = state;
   print(format('db [CC]', 'w  Debug tracing is now ', if(global_debug, 'lb ON', 'rb OFF')));
);

// All 16 concrete powder colors and their hardened concrete counterpart.
global_colors = [
   'white', 'orange', 'magenta', 'light_blue', 'yellow', 'lime', 'pink',
   'gray', 'light_gray', 'cyan', 'purple', 'blue', 'brown', 'green',
   'red', 'black'
];

// Equivalent of tags/item/convertible.json + item_modifier/convert.json
// combined into a single lookup: source item name -> resulting item name.
global_convert_map = reduce(global_colors, put(_a, _ + '_concrete_powder', _ + '_concrete'); _a, {});

// Extra conversion: dirt dropped into a water cauldron hardens into mud,
// mirroring vanilla's dirt + water bottle -> mud mechanic.
put(global_convert_map, 'dirt', 'mud');

// Controls whether the recurring scan keeps rescheduling itself.
global_running = false;

// Toggle for verbose step-by-step chat/log tracing while debugging.
global_debug = false;

_debug(msg) -> if(global_debug, print(format('g [debug] ', 'w ' + msg)));

//----------------------------------------------------------------------
// Checks if the block at, or directly below, a floored position is a
// water-filled cauldron. Returns the cauldron block value, or null.
//----------------------------------------------------------------------
_find_water_cauldron(pos) -> (
   [px, py, pz] = [floor(pos:0), floor(pos:1), floor(pos:2)];
   candidates = [block(px, py, pz), block(px, py - 1, pz)];
   first(candidates, str(_) == 'water_cauldron' && number(block_state(_, 'level')) > 0)
);

//----------------------------------------------------------------------
// Scans all item entities in the current dimension, converting any
// convertible concrete powder sitting in a water cauldron.
//----------------------------------------------------------------------
_cauldron_scan() -> (
   // The whole body is wrapped in try() so that any unexpected error
   // (bad particle/sound name, invalid block, etc) gets logged instead
   // of silently killing the schedule() chain at the bottom - a script
   // that throws before it reaches its own reschedule call would just
   // stop running forever with no visible symptom other than "nothing
   // happens anymore".
   try(
      items = entity_list('item');
      _debug('scan tick - found ' + length(items) + ' item entities');

      for(items,
         item_entity = _;
         item_data = query(item_entity, 'item');
         if(item_data != null,
            [name, count, nbt] = item_data;
            new_block = global_convert_map:name;
            _debug('item entity ' + query(item_entity,'id') + ': ' + name + ' x' + count +
               if(new_block != null, ' -> convertible to ' + new_block, ' -> not convertible'));

            if(new_block != null,
               ipos = pos(item_entity);
               cauldron = _find_water_cauldron(ipos);
               if(cauldron != null,
                  _debug('water_cauldron found near ' + ipos + ', level=' + block_state(cauldron, 'level') + ' -> converting now');

                  try(
                     // IMPORTANT (2nd finding, via debug): modify(e, 'item',
                     // [name, count, nbt]) actually rebuilds the item stack
                     // straight from `nbt` whenever nbt isn't null - the name
                     // argument is only authoritative when nbt is null. So we
                     // can't just delete the stale 'id' (that leaves the tag
                     // without one at all, which throws "No key id..."); we
                     // have to overwrite it with the new block's id. put() on
                     // an nbt tag parses the value as SNBT, so an explicit
                     // string tag needs literal double quotes around it.
                     clean_nbt = nbt;
                     if(clean_nbt != null,
                        clean_nbt = copy(clean_nbt);
                        put(clean_nbt, 'id', '"minecraft:' + new_block + '"');
                     );

                     modify(item_entity, 'item', [new_block, count, clean_nbt]);
                     _debug('post-convert check: ' + query(item_entity, 'item'));
                     particle('splash', ipos, 8, 0.2, 0.05);
                     sound('entity.generic.splash', ipos, 0.5, 1.0);
                  ,
                     'exception',
                     logger('error', 'cauldron_converter: failed to convert ' + name + ' -> ' + new_block + ': ' + _);
                     _debug('ERROR converting item: ' + _);
                  );
               ,
                  _debug('item at ' + ipos + ' is convertible but no water_cauldron with level > 0 was found at/below it - blocks there: ' +
                     block(floor(ipos:0), floor(ipos:1), floor(ipos:2)) + ' / ' +
                     block(floor(ipos:0), floor(ipos:1) - 1, floor(ipos:2)))
               )
            )
         )
      );
   ,
      'exception',
      logger('error', 'cauldron_converter: scan failed - ' + _);
      _debug('ERROR during scan: ' + _);
   );

   if(global_running, schedule(20, '_cauldron_scan'));
);

//----------------------------------------------------------------------
// Meta-events: mirrors tags/function/load.json (start the loop when
// the app starts) and uninstall.mcfunction (stop it cleanly on close).
//----------------------------------------------------------------------
__on_start() -> (
   global_running = true;
   schedule(20, '_cauldron_scan');
);

__on_close() -> (
   global_running = false;
);