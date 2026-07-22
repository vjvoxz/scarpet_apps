// ================================================================================
// Anti Ghast Grief v1.0.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  The "Anti Ghast Grief" app is designed to prevent ghasts from causing 
//  destruction in your Minecraft world. It accomplishes this by modifying the 
//  ghast's explosion mechanics so that they no longer blow up blocks.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Anti Ghast Grief" data pack/mod 
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

// Global state for the toggle
global_enabled = true;

// Define subcommands for /anti_ghast_grief
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
// Command interface: /anti_ghast_grief [info|status <state>]
//----------------------------------------------------------------------
// Registers the /anti_ghast_grief info command
info() -> (
    print(format('db Anti Ghast Grief'));
    print(format('gi Prevents ghasts from blowing up blocks'));
    print(format('gi Created by: vjvoxz (Ported from Vanilla Tweaks)'));
    print(format('p License: MIT'));

    // Display current running status
    status_style = if(global_enabled, 'e  Enabled', 'r  Disabled');
    print(format('nu Status:', status_style));
);

// Toggles the grief prevention mechanic on the fly
set_grief(state) -> (
   global_enabled = state;
   print(format('db [AGG] ', 'w Grief prevention is now ', if(global_enabled, 'lb ON', 'rb OFF')));
);

// Triggers exactly once when a ghast is spawned or loaded into the world
entity_load_handler('ghast', _(e, is_new) -> (
    // Quick check on the global variable before modifying
    if (global_enabled,
        modify(e, 'nbt_merge', '{ExplosionPower:0}');
    );
));