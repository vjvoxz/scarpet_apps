// ================================================================================
// Assembled Slabs v1.2.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  Breaking a double slab while crouching drops a full block rather than 2 slabs.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Assembled Slabs" data pack/mod 
//  by Voodoobeard.
//      https://mc.voodoobeard.com/#assembled_slabs
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

// Define subcommands for /assembled_slabs
__config() -> {
   'scope' -> 'global',
   'commands' -> {
      ''     -> 'info',
      'info' -> 'info'
   }
};

//----------------------------------------------------------------------
// Command interface: /assembled_slabs [info]
//----------------------------------------------------------------------
// Registers the /assembled_slabs info command
info() -> (
    print(format('db Assembled Slabs'));
    print(format('gi Breaking a double slab while crouching drops a full block rather than 2 slabs.'));
    print(format('gi Created by: vjvoxz (Ported from Voodoobeard Data Pack)'));
    print(format('p License: MIT'));
);

global_slab_to_block = {
   'acacia_slab' -> 'acacia_planks',
   'andesite_slab' -> 'andesite',
   'bamboo_mosaic_slab' -> 'bamboo_mosaic',
   'bamboo_slab' -> 'bamboo_planks',
   'birch_slab' -> 'birch_planks',
   'blackstone_slab' -> 'blackstone',
   'brick_slab' -> 'bricks',
   'cherry_slab' -> 'cherry_planks',
   'cobbled_deepslate_slab' -> 'cobbled_deepslate',
   'cobblestone_slab' -> 'cobblestone',
   'crimson_slab' -> 'crimson_planks',
   'cut_copper_slab' -> 'cut_copper',
   'cut_red_sandstone_slab' -> 'cut_red_sandstone',
   'cut_sandstone_slab' -> 'cut_sandstone',
   'dark_oak_slab' -> 'dark_oak_planks',
   'dark_prismarine_slab' -> 'dark_prismarine',
   'deepslate_brick_slab' -> 'deepslate_bricks',
   'deepslate_tile_slab' -> 'deepslate_tiles',
   'diorite_slab' -> 'diorite',
   'end_stone_brick_slab' -> 'end_stone_bricks',
   'exposed_cut_copper_slab' -> 'exposed_cut_copper',
   'granite_slab' -> 'granite',
   'jungle_slab' -> 'jungle_planks',
   'mangrove_slab' -> 'mangrove_planks',
   'mossy_cobblestone_slab' -> 'mossy_cobblestone',
   'mossy_stone_brick_slab' -> 'mossy_stone_bricks',
   'mud_brick_slab' -> 'mud_bricks',
   'nether_brick_slab' -> 'nether_bricks',
   'oak_slab' -> 'oak_planks',
   'oxidized_cut_copper_slab' -> 'oxidized_cut_copper',
   'pale_oak_slab' -> 'pale_oak_planks',
   'polished_andesite_slab' -> 'polished_andesite',
   'polished_blackstone_brick_slab' -> 'polished_blackstone_bricks',
   'polished_blackstone_slab' -> 'polished_blackstone',
   'polished_deepslate_slab' -> 'polished_deepslate',
   'polished_diorite_slab' -> 'polished_diorite',
   'polished_granite_slab' -> 'polished_granite',
   'polished_tuff_slab' -> 'polished_tuff',
   'prismarine_brick_slab' -> 'prismarine_bricks',
   'prismarine_slab' -> 'prismarine',
   'purpur_slab' -> 'purpur_block',
   'quartz_slab' -> 'quartz_block',
   'red_nether_brick_slab' -> 'red_nether_bricks',
   'red_sandstone_slab' -> 'red_sandstone',
   'resin_brick_slab' -> 'resin_bricks',
   'sandstone_slab' -> 'sandstone',
   'smooth_quartz_slab' -> 'smooth_quartz',
   'smooth_red_sandstone_slab' -> 'smooth_red_sandstone',
   'smooth_sandstone_slab' -> 'smooth_sandstone',
   'smooth_stone_slab' -> 'smooth_stone',
   'spruce_slab' -> 'spruce_planks',
   'stone_brick_slab' -> 'stone_bricks',
   'stone_slab' -> 'stone',
   'tuff_brick_slab' -> 'tuff_bricks',
   'tuff_slab' -> 'tuff',
   'warped_slab' -> 'warped_planks',
   'waxed_cut_copper_slab' -> 'waxed_cut_copper',
   'waxed_exposed_cut_copper_slab' -> 'waxed_exposed_cut_copper',
   'waxed_oxidized_cut_copper_slab' -> 'waxed_oxidized_cut_copper',
   'waxed_weathered_cut_copper_slab' -> 'waxed_weathered_cut_copper',
   'weathered_cut_copper_slab' -> 'weathered_cut_copper',
   // Additional slabs from the "Cinch's Missing Blocks" mod
   'cinchsmissingblocks:calcite_slab' -> 'calcite',
   'cinchsmissingblocks:dripstone_slab' -> 'dripstone_block',
   'cinchsmissingblocks:quartz_brick_slab' -> 'quartz_block',
   'cinchsmissingblocks:end_stone_slab' -> 'end_stone',
   'cinchsmissingblocks:mossy_mud_brick_slab' -> 'cinchsmissingblocks:mossy_mud_bricks',
   'cinchsmissingblocks:polished_calcite_slab' -> 'calcite',
   'cinchsmissingblocks:polished_dripstone_slab' -> 'dripstone_block',
   'cinchsmissingblocks:cracked_stone_brick_slab' -> 'cracked_stone_bricks',
   'cinchsmissingblocks:cracked_deepslate_brick_slab' -> 'cracked_deepslate_bricks',
   'cinchsmissingblocks:cracked_deepslate_tile_slab' -> 'cracked_deepslate_tiles',
   'cinchsmissingblocks:cracked_polished_blackstone_brick_slab' -> 'cracked_polished_blackstone_bricks',
   'cinchsmissingblocks:cracked_nether_brick_slab' -> 'cracked_nether_bricks',
   'cinchsmissingblocks:cracked_red_nether_brick_slab' -> 'cinchsmissingblocks:cracked_red_nether_bricks',
   'cinchsmissingblocks:cracked_mud_brick_slab' -> 'cinchsmissingblocks:cracked_mud_bricks',
   'cinchsmissingblocks:cracked_quartz_brick_slab' -> 'cinchsmissingblocks:cracked_quartz_bricks',
   'cinchsmissingblocks:smooth_basalt_slab' -> 'smooth_basalt',
   'cinchsmissingblocks:cracked_brick_slab' -> 'cinchsmissingblocks:cracked_bricks',
   'cinchsmissingblocks:cracked_prismarine_brick_slab' -> 'cinchsmissingblocks:cracked_prismarine_bricks',
   'cinchsmissingblocks:cracked_end_stone_brick_slab' -> 'cinchsmissingblocks:cracked_end_stone_bricks',
   'cinchsmissingblocks:mossy_brick_slab' -> 'cinchsmissingblocks:mossy_bricks',
   'cinchsmissingblocks:mossy_quartz_brick_slab' -> 'cinchsmissingblocks:mossy_quartz_bricks',
   'cinchsmissingblocks:cracked_tuff_brick_slab' -> 'cinchsmissingblocks:cracked_tuff_bricks',
   'cinchsmissingblocks:mossy_tuff_brick_slab' -> 'cinchsmissingblocks:mossy_tuff_bricks',
   'cinchsmissingblocks:calcite_brick_slab' -> 'cinchsmissingblocks:calcite_bricks',
   'cinchsmissingblocks:cracked_calcite_brick_slab' -> 'cinchsmissingblocks:cracked_calcite_bricks',
   'cinchsmissingblocks:mossy_calcite_brick_slab' -> 'cinchsmissingblocks:mossy_calcite_bricks',
   'cinchsmissingblocks:dripstone_brick_slab' -> 'dripstone_block',
   'cinchsmissingblocks:cracked_dripstone_brick_slab' -> 'cinchsmissingblocks:cracked_dripstone_bricks',
   'cinchsmissingblocks:mossy_dripstone_brick_slab' -> 'cinchsmissingblocks:mossy_dripstone_bricks',
   'cinchsmissingblocks:andesite_brick_slab' -> 'cinchsmissingblocks:andesite_bricks',
   'cinchsmissingblocks:cracked_andesite_brick_slab' -> 'cinchsmissingblocks:cracked_andesite_bricks',
   'cinchsmissingblocks:mossy_andesite_brick_slab' -> 'cinchsmissingblocks:mossy_andesite_bricks',
   'cinchsmissingblocks:granite_brick_slab' -> 'cinchsmissingblocks:granite_bricks',
   'cinchsmissingblocks:cracked_granite_brick_slab' -> 'cinchsmissingblocks:cracked_granite_bricks',
   'cinchsmissingblocks:mossy_granite_brick_slab' -> 'cinchsmissingblocks:mossy_granite_bricks',
   'cinchsmissingblocks:diorite_brick_slab' -> 'cinchsmissingblocks:diorite_bricks',
   'cinchsmissingblocks:cracked_diorite_brick_slab' -> 'cinchsmissingblocks:cracked_diorite_bricks',
   'cinchsmissingblocks:mossy_diorite_brick_slab' -> 'cinchsmissingblocks:mossy_diorite_bricks',
   'cinchsmissingblocks:netherrack_slab' -> 'netherrack',
   'cinchsmissingblocks:packed_mud_slab' -> 'packed_mud',
   'cinchsmissingblocks:mossy_prismarine_brick_slab' -> 'cinchsmissingblocks:mossy_prismarine_bricks',
   'cinchsmissingblocks:snow_brick_slab' -> 'snow_block',
   'cinchsmissingblocks:polished_end_stone_slab' -> 'end_stone',
   'cinchsmissingblocks:mossy_cobbled_deepslate_slab' -> 'cinchsmissingblocks:mossy_cobbled_deepslate',
   'cinchsmissingblocks:mossy_deepslate_brick_slab' -> 'cinchsmissingblocks:mossy_deepslate_bricks',
   'cinchsmissingblocks:mossy_deepslate_tile_slab' -> 'cinchsmissingblocks:mossy_deepslate_tiles',
   'cinchsmissingblocks:deepslate_slab' -> 'deepslate',
   'cinchsmissingblocks:cracked_resin_brick_slab' -> 'cinchsmissingblocks:cracked_resin_bricks',
   'cinchsmissingblocks:mossy_resin_brick_slab' -> 'cinchsmissingblocks:mossy_resin_bricks'

};

//--------------------------------------------------------------------
// Legacy / special-case slabs that are intentionally left out of
// global_slab_to_block, so they should never be flagged by
// audit_slab_map() as "unmapped" either. Kept as its own set rather
// than as null entries in the main map, since that would depend on
// has() treating a null-valued key as still "present" - safer to be
// explicit here instead.
//--------------------------------------------------------------------
global_slab_audit_ignore = {
   // Legacy block from old worlds, predates current slab crafting
   // recipes. Has no full-block equivalent to assemble into.
   'petrified_oak_slab' -> true
};

//--------------------------------------------------------------------
// Best-effort guess at the "full block" a slab was cut/crafted from,
// used only by audit_slab_map() below to suggest new table entries.
// Tries the stonecutting recipe first (always a single 1:1
// ingredient for slabs), then falls back to the shaped crafting
// recipe (III / III / III pattern - any filled slot is the block).
//--------------------------------------------------------------------
guess_full_block(slab) -> (
   guess = null;
   for (['stonecutting', 'crafting'],
      type = _;
      if (guess == null,
         recipes = recipe_data(slab, type);
         if (recipes,
            for (recipes,
               recipe = _;
               ingredients = recipe:1;
               if (ingredients,
                  for (ingredients,
                     slot = _;
                     if (slot && slot:0 && guess == null,
                        id = str(slot:0);
                        guess = if (slice(id, 0, 10) == 'minecraft:', slice(id, 10), id);
                     )
                  )
               )
            )
         )
      )
   );
   guess
);

//--------------------------------------------------------------------
// Diffs the live 'slabs' block tag (which reflects whatever version,
// datapacks or mods the server is actually running) against our
// static global_slab_to_block table, and logs any unmapped slabs
// together with an auto-detected guess, so an operator can just copy
// the suggested line into the table above rather than hunting for it
// by hand. Nothing is added to the live map automatically - guesses
// are logged only, since a wrong guess would silently change drop
// behaviour.
//--------------------------------------------------------------------
audit_slab_map() -> (
   missing = [];
   for (block_list('slabs'),
      if (!has(global_slab_to_block, _) && !has(global_slab_audit_ignore, _), missing += _)
   );
   if (missing,
      logger('warn', 'Assembled Slabs: unmapped slab(s) detected - add these to global_slab_to_block:');
      for (missing,
         slab = _;
         guess = guess_full_block(slab);
         guess_text = if (guess, guess, 'null - could not auto-detect, please fill in manually');
         logger('warn', '   ' + slab + ' -> ' + guess_text + ' (auto-detected values should be verified)');
      )
   )
);

__on_start() -> audit_slab_map();

//--------------------------------------------------------------------
// Intercepts a player breaking a double slab while sneaking, and
// replaces the default (2x slab) drop with a single full block.
// All other breaks (single slabs, or double slabs broken standing
// up) are left completely untouched and fall through to vanilla.
//--------------------------------------------------------------------
__on_player_breaks_block(player, block) ->
(
   full_block = global_slab_to_block:str(block);

   if (full_block != null && block_state(block, 'type') == 'double' && query(player, 'sneaking'),
      pos = pos(block);
      set(pos, 'air');
      spawn('item', pos + [0.5, 0.2, 0.5], nbt('{Item:{id:"minecraft:' + full_block + '",Count:1b}}'));
      'cancel'
   )
);