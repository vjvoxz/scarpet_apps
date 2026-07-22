// ================================================================================
// Assembled Slabs v1.2.3 Scarpet App
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
// See CHANGELOG.md for full version history and README.md for full app info.
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
   'minecraft:acacia_slab' -> 'minecraft:acacia_planks',
   'minecraft:andesite_slab' -> 'minecraft:andesite',
   'minecraft:bamboo_mosaic_slab' -> 'minecraft:bamboo_mosaic',
   'minecraft:bamboo_slab' -> 'minecraft:bamboo_planks',
   'minecraft:birch_slab' -> 'minecraft:birch_planks',
   'minecraft:blackstone_slab' -> 'minecraft:blackstone',
   'minecraft:brick_slab' -> 'minecraft:bricks',
   'minecraft:cherry_slab' -> 'minecraft:cherry_planks',
   'minecraft:cobbled_deepslate_slab' -> 'minecraft:cobbled_deepslate',
   'minecraft:cobblestone_slab' -> 'minecraft:cobblestone',
   'minecraft:crimson_slab' -> 'minecraft:crimson_planks',
   'minecraft:cut_copper_slab' -> 'minecraft:cut_copper',
   'minecraft:cut_red_sandstone_slab' -> 'minecraft:cut_red_sandstone',
   'minecraft:cut_sandstone_slab' -> 'minecraft:cut_sandstone',
   'minecraft:dark_oak_slab' -> 'minecraft:dark_oak_planks',
   'minecraft:dark_prismarine_slab' -> 'minecraft:dark_prismarine',
   'minecraft:deepslate_brick_slab' -> 'minecraft:deepslate_bricks',
   'minecraft:deepslate_tile_slab' -> 'minecraft:deepslate_tiles',
   'minecraft:diorite_slab' -> 'minecraft:diorite',
   'minecraft:end_stone_brick_slab' -> 'minecraft:end_stone_bricks',
   'minecraft:exposed_cut_copper_slab' -> 'minecraft:exposed_cut_copper',
   'minecraft:granite_slab' -> 'minecraft:granite',
   'minecraft:jungle_slab' -> 'minecraft:jungle_planks',
   'minecraft:mangrove_slab' -> 'minecraft:mangrove_planks',
   'minecraft:mossy_cobblestone_slab' -> 'minecraft:mossy_cobblestone',
   'minecraft:mossy_stone_brick_slab' -> 'minecraft:mossy_stone_bricks',
   'minecraft:mud_brick_slab' -> 'minecraft:mud_bricks',
   'minecraft:nether_brick_slab' -> 'minecraft:nether_bricks',
   'minecraft:oak_slab' -> 'minecraft:oak_planks',
   'minecraft:oxidized_cut_copper_slab' -> 'minecraft:oxidized_cut_copper',
   'minecraft:pale_oak_slab' -> 'minecraft:pale_oak_planks',
   'minecraft:polished_andesite_slab' -> 'minecraft:polished_andesite',
   'minecraft:polished_blackstone_brick_slab' -> 'minecraft:polished_blackstone_bricks',
   'minecraft:polished_blackstone_slab' -> 'minecraft:polished_blackstone',
   'minecraft:polished_deepslate_slab' -> 'minecraft:polished_deepslate',
   'minecraft:polished_diorite_slab' -> 'minecraft:polished_diorite',
   'minecraft:polished_granite_slab' -> 'minecraft:polished_granite',
   'minecraft:polished_tuff_slab' -> 'minecraft:polished_tuff',
   'minecraft:prismarine_brick_slab' -> 'minecraft:prismarine_bricks',
   'minecraft:prismarine_slab' -> 'minecraft:prismarine',
   'minecraft:purpur_slab' -> 'minecraft:purpur_block',
   'minecraft:quartz_slab' -> 'minecraft:quartz_block',
   'minecraft:red_nether_brick_slab' -> 'minecraft:red_nether_bricks',
   'minecraft:red_sandstone_slab' -> 'minecraft:red_sandstone',
   'minecraft:resin_brick_slab' -> 'minecraft:resin_bricks',
   'minecraft:sandstone_slab' -> 'minecraft:sandstone',
   'minecraft:smooth_quartz_slab' -> 'minecraft:smooth_quartz',
   'minecraft:smooth_red_sandstone_slab' -> 'minecraft:smooth_red_sandstone',
   'minecraft:smooth_sandstone_slab' -> 'minecraft:smooth_sandstone',
   'minecraft:smooth_stone_slab' -> 'minecraft:smooth_stone',
   'minecraft:spruce_slab' -> 'minecraft:spruce_planks',
   'minecraft:stone_brick_slab' -> 'minecraft:stone_bricks',
   'minecraft:stone_slab' -> 'minecraft:stone',
   'minecraft:tuff_brick_slab' -> 'minecraft:tuff_bricks',
   'minecraft:tuff_slab' -> 'minecraft:tuff',
   'minecraft:warped_slab' -> 'minecraft:warped_planks',
   'minecraft:waxed_cut_copper_slab' -> 'minecraft:waxed_cut_copper',
   'minecraft:waxed_exposed_cut_copper_slab' -> 'minecraft:waxed_exposed_cut_copper',
   'minecraft:waxed_oxidized_cut_copper_slab' -> 'minecraft:waxed_oxidized_cut_copper',
   'minecraft:waxed_weathered_cut_copper_slab' -> 'minecraft:waxed_weathered_cut_copper',
   'minecraft:weathered_cut_copper_slab' -> 'minecraft:weathered_cut_copper',
   // Additional slabs from the "Cinch's Missing Blocks" mod
   'cinchsmissingblocks:calcite_slab' -> 'minecraft:calcite',
   'cinchsmissingblocks:dripstone_slab' -> 'minecraft:dripstone_block',
   'cinchsmissingblocks:quartz_brick_slab' -> 'minecraft:quartz_bricks',
   'cinchsmissingblocks:end_stone_slab' -> 'minecraft:end_stone',
   'cinchsmissingblocks:mossy_mud_brick_slab' -> 'cinchsmissingblocks:mossy_mud_bricks',
   'cinchsmissingblocks:polished_calcite_slab' -> 'cinchsmissingblocks:polished_calcite',
   'cinchsmissingblocks:polished_dripstone_slab' -> 'cinchsmissingblocks:polished_dripstone',
   'cinchsmissingblocks:cracked_stone_brick_slab' -> 'minecraft:cracked_stone_bricks',
   'cinchsmissingblocks:cracked_deepslate_brick_slab' -> 'minecraft:cracked_deepslate_bricks',
   'cinchsmissingblocks:cracked_deepslate_tile_slab' -> 'minecraft:cracked_deepslate_tiles',
   'cinchsmissingblocks:cracked_polished_blackstone_brick_slab' -> 'minecraft:cracked_polished_blackstone_bricks',
   'cinchsmissingblocks:cracked_nether_brick_slab' -> 'minecraft:cracked_nether_bricks',
   'cinchsmissingblocks:cracked_red_nether_brick_slab' -> 'cinchsmissingblocks:cracked_red_nether_bricks',
   'cinchsmissingblocks:cracked_mud_brick_slab' -> 'cinchsmissingblocks:cracked_mud_bricks',
   'cinchsmissingblocks:cracked_quartz_brick_slab' -> 'cinchsmissingblocks:cracked_quartz_bricks',
   'cinchsmissingblocks:smooth_basalt_slab' -> 'minecraft:smooth_basalt',
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
   'cinchsmissingblocks:dripstone_brick_slab' -> 'cinchsmissingblocks:dripstone_bricks',
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
   'cinchsmissingblocks:netherrack_slab' -> 'minecraft:netherrack',
   'cinchsmissingblocks:packed_mud_slab' -> 'minecraft:packed_mud',
   'cinchsmissingblocks:mossy_prismarine_brick_slab' -> 'cinchsmissingblocks:mossy_prismarine_bricks',
   'cinchsmissingblocks:snow_brick_slab' -> 'cinchsmissingblocks:snow_bricks',
   'cinchsmissingblocks:polished_end_stone_slab' -> 'minecraft:end_stone',
   'cinchsmissingblocks:mossy_cobbled_deepslate_slab' -> 'cinchsmissingblocks:mossy_cobbled_deepslate',
   'cinchsmissingblocks:mossy_deepslate_brick_slab' -> 'cinchsmissingblocks:mossy_deepslate_bricks',
   'cinchsmissingblocks:mossy_deepslate_tile_slab' -> 'cinchsmissingblocks:mossy_deepslate_tiles',
   'cinchsmissingblocks:deepslate_slab' -> 'minecraft:deepslate',
   'cinchsmissingblocks:cracked_resin_brick_slab' -> 'cinchsmissingblocks:cracked_resin_bricks',
   'cinchsmissingblocks:mossy_resin_brick_slab' -> 'cinchsmissingblocks:mossy_resin_bricks',
   'cinchsmissingblocks:mossy_end_stone_brick_slab' -> 'cinchsmissingblocks:mossy_end_stone_bricks',
   'cinchsmissingblocks:sandstone_brick_slab' -> 'cinchsmissingblocks:sandstone_bricks',
   'cinchsmissingblocks:cracked_sandstone_brick_slab' -> 'cinchsmissingblocks:cracked_sandstone_bricks',
   'cinchsmissingblocks:mossy_sandstone_brick_slab' -> 'cinchsmissingblocks:mossy_sandstone_bricks',
   'cinchsmissingblocks:red_sandstone_brick_slab' -> 'cinchsmissingblocks:red_sandstone_bricks',
   'cinchsmissingblocks:cracked_red_sandstone_brick_slab' -> 'cinchsmissingblocks:cracked_red_sandstone_bricks',
   'cinchsmissingblocks:mossy_red_sandstone_brick_slab' -> 'cinchsmissingblocks:mossy_red_sandstone_bricks',
   'cinchsmissingblocks:blue_nether_brick_slab' -> 'cinchsmissingblocks:blue_nether_bricks',
   'cinchsmissingblocks:cracked_blue_nether_brick_slab' -> 'cinchsmissingblocks:cracked_blue_nether_bricks',
   'cinchsmissingblocks:stone_tile_slab' -> 'cinchsmissingblocks:stone_tiles',
   'cinchsmissingblocks:cracked_stone_tile_slab' -> 'cinchsmissingblocks:cracked_stone_tiles',
   'cinchsmissingblocks:mossy_stone_tile_slab' -> 'cinchsmissingblocks:mossy_stone_tiles',
   'cinchsmissingblocks:blackstone_tile_slab' -> 'minecraft:blackstone',
   'cinchsmissingblocks:cracked_blackstone_tile_slab' -> 'cinchsmissingblocks:cracked_blackstone_tiles',
   'cinchsmissingblocks:terracotta_slab' -> 'minecraft:terracotta',
   'cinchsmissingblocks:white_terracotta_slab' -> 'minecraft:white_terracotta',
   'cinchsmissingblocks:light_gray_terracotta_slab' -> 'minecraft:light_gray_terracotta',
   'cinchsmissingblocks:gray_terracotta_slab' -> 'minecraft:gray_terracotta',
   'cinchsmissingblocks:black_terracotta_slab' -> 'minecraft:black_terracotta',
   'cinchsmissingblocks:brown_terracotta_slab' -> 'minecraft:brown_terracotta',
   'cinchsmissingblocks:red_terracotta_slab' -> 'minecraft:red_terracotta',
   'cinchsmissingblocks:orange_terracotta_slab' -> 'minecraft:orange_terracotta',
   'cinchsmissingblocks:yellow_terracotta_slab' -> 'minecraft:yellow_terracotta',
   'cinchsmissingblocks:lime_terracotta_slab' -> 'minecraft:lime_terracotta',
   'cinchsmissingblocks:green_terracotta_slab' -> 'minecraft:green_terracotta',
   'cinchsmissingblocks:cyan_terracotta_slab' -> 'minecraft:cyan_terracotta',
   'cinchsmissingblocks:light_blue_terracotta_slab' -> 'minecraft:light_blue_terracotta',
   'cinchsmissingblocks:blue_terracotta_slab' -> 'minecraft:blue_terracotta',
   'cinchsmissingblocks:purple_terracotta_slab' -> 'minecraft:purple_terracotta',
   'cinchsmissingblocks:magenta_terracotta_slab' -> 'minecraft:magenta_terracotta',
   'cinchsmissingblocks:pink_terracotta_slab' -> 'minecraft:pink_terracotta',
   'cinchsmissingblocks:white_concrete_slab' -> 'minecraft:white_concrete',
   'cinchsmissingblocks:light_gray_concrete_slab' -> 'minecraft:light_gray_concrete',
   'cinchsmissingblocks:gray_concrete_slab' -> 'minecraft:gray_concrete',
   'cinchsmissingblocks:black_concrete_slab' -> 'minecraft:black_concrete',
   'cinchsmissingblocks:brown_concrete_slab' -> 'minecraft:brown_concrete',
   'cinchsmissingblocks:red_concrete_slab' -> 'minecraft:red_concrete',
   'cinchsmissingblocks:orange_concrete_slab' -> 'minecraft:orange_concrete',
   'cinchsmissingblocks:yellow_concrete_slab' -> 'minecraft:yellow_concrete',
   'cinchsmissingblocks:lime_concrete_slab' -> 'minecraft:lime_concrete',
   'cinchsmissingblocks:green_concrete_slab' -> 'minecraft:green_concrete',
   'cinchsmissingblocks:cyan_concrete_slab' -> 'minecraft:cyan_concrete',
   'cinchsmissingblocks:light_blue_concrete_slab' -> 'minecraft:light_blue_concrete',
   'cinchsmissingblocks:blue_concrete_slab' -> 'minecraft:blue_concrete',
   'cinchsmissingblocks:purple_concrete_slab' -> 'minecraft:purple_concrete',
   'cinchsmissingblocks:magenta_concrete_slab' -> 'minecraft:magenta_concrete',
   'cinchsmissingblocks:pink_concrete_slab' -> 'minecraft:pink_concrete'
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
                        guess = str(slot:0);
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
// block_list() and str(block) report blocks in the default
// 'minecraft' namespace with the prefix stripped (e.g. 'oak_slab'),
// but keep the namespace for modded blocks (e.g.
// 'cinchsmissingblocks:calcite_slab'). global_slab_to_block always
// uses the fully namespaced id, for readability and consistency
// between vanilla and modded entries. Any id coming from the game
// needs to go through this before being used as a lookup key into
// that table, otherwise every vanilla entry silently fails to match.
//--------------------------------------------------------------------
full_id(id) -> if (id ~ ':', id, 'minecraft:' + id);

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
      // audit_ignore is keyed the same way block_list() reports names
      // (namespace-stripped for vanilla), so check it against the raw
      // name; global_slab_to_block always uses the full id, so that
      // check needs the normalized one.
      if (!has(global_slab_to_block, full_id(_)) && !has(global_slab_audit_ignore, _), missing += full_id(_))
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
__on_player_breaks_block(player, block) -> (
   full_block = global_slab_to_block:full_id(str(block));
   if (full_block != null && block_state(block, 'type') == 'double' && query(player, 'sneaking'),
      pos = pos(block);
      set(pos, 'air');
      // Vanilla scatters item drops within roughly the middle 70% of the
      // block on X/Z (offset 0.15 to 0.85) rather than dead center, and
      // gives them a small random horizontal drift plus a gentle upward
      // pop instead of spawning them with zero motion.
      // ----------------------------------------------------------------
      // If it still feels a touch too energetic or too tame once you test 
      // it in-game, the two easy knobs are the 0.2 spread in the motion 
      // (bigger = more scatter) and the rand(0.15) on Y (bigger = more 
      // height variance before it lands) — just say which direction you 
      // want and I'll retune it.
      drop_pos = pos + [0.15 + rand(0.7), 0.1 + rand(0.15), 0.15 + rand(0.7)];
      item = spawn('item', drop_pos, nbt('{Item:{id:"' + full_block + '",Count:1b}}'));
      if (item, modify(item, 'motion', rand(0.2) - 0.1, rand(0.2) + 0.1, rand(0.2) - 0.1));
      'cancel'
   )
);
