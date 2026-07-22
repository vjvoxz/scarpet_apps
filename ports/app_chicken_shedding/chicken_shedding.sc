// ==============================================================================
// Chicken Shedding v1.0.0 Scarpet App
// ------------------------------------------------------------------------------
// ABOUT THE APP:
//  This app modifies the chicken laying behavior so that chickens have a 20%
//  chance of shedding a feather instead of laying an egg. If they lay an egg,
//  the type of egg depends on the chicken's variant.
// ------------------------------------------------------------------------------
// ATTRIBUTION:
//  This app recreates the behaviour of the "Chicken Shedding" data pack/mod 
//  by Voodoobeard.
//      https://mc.voodoobeard.com/#chicken_shedding
//      https://mc.voodoobeard.com/
//  This script doesn't copy any of the pack/mod files but provides credit to the 
//  creator, their rights and license terms still apply to the underlying idea.
// ------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet port created by vjvoxz
//  - License: MIT
//  - This script was generated with assistance from Gemini, an AI model
//    created by Google DeepMind.
// ==============================================================================

// Configuration for dynamic command routing
__config() -> {
    'scope' -> 'global',
    'commands' -> {
        '' -> 'info',
        'info' -> 'info'
    }
};

// Registers the /chicken_shedding info command
info() -> (
    print(format('db Chicken Shedding'));
    print(format('gi Chickens have a 20% chance of shedding feathers instead of laying an egg'));
    print(format('gi Egg type depends on chicken variant:'));
    print(format('gi Created by: vjvoxz (Ported from Voodoobeard Datapack)'));
    print(format('p License: MIT'));
);

// Triggers exactly once when the app is loaded
__on_start() -> (
    // Inject the custom loot table into the game memory
    create_datapack('chicken_shedding', {
        'data/minecraft/loot_table/gameplay/chicken_lay.json' -> {
            'type' -> 'minecraft:gift',
            'pools' -> [
                {
                    'bonus_rolls' -> 0,
                    'entries' -> [
                        {
                            'type' -> 'minecraft:alternatives',
                            'children' -> [
                                {
                                    'type' -> 'minecraft:item',
                                    'name' -> 'minecraft:feather',
                                    'functions' -> [],
                                    'conditions' -> [
                                        {
                                            'condition' -> 'minecraft:random_chance',
                                            'chance' -> 0.2
                                        }
                                    ]
                                },
                                {
                                    'type' -> 'minecraft:item',
                                    'conditions' -> [
                                        {
                                            'condition' -> 'minecraft:entity_properties',
                                            'entity' -> 'this',
                                            'predicate' -> {
                                                'components' -> {
                                                    'minecraft:chicken/variant' -> 'minecraft:temperate'
                                                }
                                            }
                                        }
                                    ],
                                    'name' -> 'minecraft:egg'
                                },
                                {
                                    'type' -> 'minecraft:item',
                                    'conditions' -> [
                                        {
                                            'condition' -> 'minecraft:entity_properties',
                                            'entity' -> 'this',
                                            'predicate' -> {
                                                'components' -> {
                                                    'minecraft:chicken/variant' -> 'minecraft:warm'
                                                }
                                            }
                                        }
                                    ],
                                    'name' -> 'minecraft:brown_egg'
                                },
                                {
                                    'type' -> 'minecraft:item',
                                    'conditions' -> [
                                        {
                                            'condition' -> 'minecraft:entity_properties',
                                            'entity' -> 'this',
                                            'predicate' -> {
                                                'components' -> {
                                                    'minecraft:chicken/variant' -> 'minecraft:cold'
                                                }
                                            }
                                        }
                                    ],
                                    'name' -> 'minecraft:blue_egg'
                                }
                            ]
                        }
                    ],
                    'rolls' -> 1
                }
            ],
            'random_sequence' -> 'minecraft:gameplay/chicken_lay'
        }
    });
    
    logger('info', 'Chicken Shedding virtual datapack loaded successfully.');
);