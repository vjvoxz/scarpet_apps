// ================================================================================
// Conductive Elytra Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  Flying in a storm doesn't seem too smart...
//
//  Every 10 seconds, during a thunderstorm, any player actively
//  gliding on an elytra has a 7% chance of being struck by
//  lightning right where they are.
//
//  Original mechanic: voodoobeard's "Conductive Elytra" datapack
//  (predicate-driven: random_chance 0.07 + FallFlying/elytra
//  entity_properties check + thundering weather_check, looped
//  via `schedule function ... 10s`).  
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the Conductive Elytra data pack/mod 
//  by voodoobeard.
//      https://mc.voodoobeard.com/#conductive_elytra
//      https://mc.voodoobeard.com/
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

// Global configuration state
global_enabled = true;

__config() -> {
    'scope' -> 'global',
    'commands' -> {
      ''     -> 'info',
      'info' -> 'info',
      'status <state>' -> 'set_enabled'
    },
    'arguments' -> {
        'state' -> { 'type' -> 'bool' }
    }
};

//----------------------------------------------------------------------
// Command interface: /conductive_elytra [info|status <state>]
//----------------------------------------------------------------------
// Registers the /conductive_elytra info command
info() -> (
    print(format('db Conductive Elytra'));
    print(format('gi Lightning bolts strike players on elytra during thunderstorms'));
    print(format('gi Created by: vjvoxz (Ported from voodoobeard "Conductive Elytra")'));
    print(format('p License: MIT'));
    
    // Display current running status
    status_style = if(global_enabled, 'e  Enabled', 'r  Disabled');
    print(format('nu Status:', status_style));
);

// Toggles the strike by lightning mechanic while flying
set_enabled(state) -> (
   global_enabled = state;
   print(format('db [CE] ', 'w Struck by lightning is now ', if(global_enabled, 'lb ON', 'rb OFF')));
);

global_strike_chance = 0.07;
global_check_interval = 200; // ticks (10 seconds)

__on_start() -> schedule(0, 'check_conductive_players');

check_conductive_players() -> (

    if (weather() == 'thunder',
        for (player('*'),
            p = _;
            if (query(p, 'pose') == 'fall_flying',
                chest_item = query(p, 'holds', 'chest');
                if (chest_item != null && chest_item:0 == 'elytra' && rand(1) < global_strike_chance,
                    spawn('lightning_bolt', pos(p))
                )
            )
        )
    );

    // reschedule ourselves to keep the loop running indefinitely
    schedule(global_check_interval, 'check_conductive_players')
);