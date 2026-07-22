// ================================================================================
// Apiarist Suit v1.1.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  The Apiarist Suit app provides protection against aggressive bees by requiring 
//  the player to wear a full set of chainmail armor.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Apiarist Suit" data pack/mod 
//  by Voodoobeard.
//      https://mc.voodoobeard.com/#apiarist_suit
//      https://mc.voodoobeard.com/
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

// How far (in blocks) around the player to look for angry bees
global_apiarist_range = [2, 2, 2];

// Define subcommands for /apiarist_suit
__config() -> {
   'scope' -> 'global',
   'commands' -> {
      ''     -> 'info',
      'info' -> 'info'
   }
};

//----------------------------------------------------------------------
// Command interface: /apiarist_suit [info]
//----------------------------------------------------------------------
// Registers the /apiarist_suit info command
info() -> (
    print(format('db Apiarist Suit'));
    print(format('gi Wearing full Chainmail Armor protects you from their vicious stings from bees and calms them down.'));
    print(format('gi Created by: vjvoxz (Ported from Voodoobeard Data Pack)'));
    print(format('p License: MIT'));
);

// Runs on every tick to check player equipment and nearby bees 
__on_tick() -> ( 
    for(player('all'), 
        p = _;
        
        // Check if the player is wearing a full set of chainmail armor
        if ( 
            inventory_get(p, 36):0 == 'chainmail_boots' && 
            inventory_get(p, 37):0 == 'chainmail_leggings' && 
            inventory_get(p, 38):0 == 'chainmail_chestplate' && 
            inventory_get(p, 39):0 == 'chainmail_helmet',
            
            // Scan for bees within a 2-block radius of the player 
            for(entity_area('bee', p, global_apiarist_range), 
                bee = _; 
                
                // Only merge NBT data if the bee is actually angry (has a target) 
                if (query(bee, 'target') != null, 
                    // Broad NBT wipe to account for potential schema changes in 26.2
                    modify(bee, 'nbt_merge', '{AngerTime:0, anger_time:0, AngryAt:[I;0,0,0,0], angry_at:[I;0,0,0,0]}'); 
                ); 
            );
        ); 
    ); 
);