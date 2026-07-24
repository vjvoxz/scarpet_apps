// ================================================================================
// Anti Creeper Grief v1.1.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  The "Anti Creeper Grief" app is designed to prevent creepers from causing 
//  destruction in your Minecraft world. It accomplishes this by modifying the 
//  creeper's explosion mechanics so that they no longer blow up holes in the 
//  terrain.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Anti Creeper Grief" data pack/mod 
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

// Define subcommands for /anti_creeper_grief
__config() -> {
   'scope' -> 'global',
   'commands' -> {
      ''     -> 'info',
      'info' -> 'info',
      'enable <state>' -> 'set_grief'
    },
    'arguments' -> {
        'state' -> { 'type' -> 'bool' }
    }
};

//----------------------------------------------------------------------
// Command interface: /anti_creeper_grief [info|enable <state>]
//----------------------------------------------------------------------
// Registers the /anti_creeper_grief info command
info() -> (
    print(format('db Anti Creeper Grief'));
    print(format('gi Prevents creepers from blowing up holes'));
    print(format('gi Created by: vjvoxz (Ported from Vanilla Tweaks)'));
    print(format('wi Version: 1.1.0'));
    print(format('p License: MIT'));
    
    // Display current running status
    enable_style = if(global_enabled, 'l  Enabled', 'r  Disabled');
    print(format('nu Status:', enable_style));
);

// Toggles the grief prevention mechanic on the fly
set_grief(state) -> (
   global_enabled = state;
   print(format('db [ACG] ', 'w Grief prevention is now ', if(global_enabled, 'lb ON', 'rb OFF')));
);

// Triggers exactly once when a creeper is spawned or loaded into the world
entity_load_handler('creeper', _(e, is_new) -> (
    if(global_enabled,
        modify(e, 'nbt_merge', '{ExplosionRadius:0}');
    );
));
