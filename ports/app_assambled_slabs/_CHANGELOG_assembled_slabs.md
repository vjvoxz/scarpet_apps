# Changelog

All notable changes to the Assembled Slabs scarpet app will be documented in this file.

Changes follow the format: **MAJOR.MINOR.PATCH**  
- **MAJOR**: Breaking changes or major new features.  
- **MINOR**: Backwards-compatible feature additions.  
- **PATCH**: Bug fixes or small improvements.  

The latest changes appear at the top.

---

## v1.3.0  (2026-07-22)
- Added support for unmapped slabs in Minecraft Java Edition 26.2: "Chaos Cubed" and 26.3: "".

## v1.2.5  (2026-07-22)
- Fixed the tool-break sound not playing when a tool broke while crouch-breaking a double slab (confirmed via testing: standing up played it, crouching was silent). destroy() removes the tool from the inventory correctly but doesn't play vanilla's usual 'item breaks' sound on its own, since that's normally fired by the break pipeline this code cancels. Now played manually at the player's position when tag_back == null.

## v1.2.4  (2026-07-22)
- Fixed the held tool taking no durability damage when breaking a double slab. Returning 'cancel' from __on_player_breaks_block skips vanilla's entire break pipeline, including the durability hit that's normally part of it, so the tool was never touched.
- Rather than hand-rolling Unbreaking odds / Unbreakable checks / per-material max durability ourselves (fragile, and easy to drift from real vanilla behaviour), the block is now removed via destroy(pos, tool, nbt) using the player's actual held item, which already applies the game's own correct durability logic and hands back the tool's new state. The resulting inventory slot is updated accordingly (tool consumed if it broke, or its nbt updated if it survived).
- destroy() drops the block's normal loot (2 single slabs) as part of doing the removal, so those are now cleared (matched tightly by item id, position and age to avoid touching unrelated nearby item entities) immediately before spawning our own single full-block item, same as before.
- Mining speed is unaffected either way: this hook only fires after vanilla has already decided the break should happen, i.e. after its normal mining time has elapsed.

## v1.2.3  (2026-07-21)
- The full-block drop in __on_player_breaks_block spawned at a fixed offset (0.5, 0.2, 0.5) with no motion, so it looked stiff/unnatural compared to vanilla drops. It now spawns at a randomized position within the block (X/Z offset 0.15-0.85, matching vanilla's own spread) and gets a small random horizontal drift plus a gentle upward pop, using rand() and modify(item, 'motion', ...).

## v1.2.2  (2026-07-21)
- Root-caused the flood of "unmapped slab" warnings on startup: block_list('slabs') (confirmed via in-game test) reports vanilla block ids with the 'minecraft:' namespace stripped (e.g.'oak_slab'), but keeps it for modded ids (e.g.'cinchsmissingblocks:calcite_slab'). global_slab_to_block always uses the fully namespaced id for every entry, so has() was being asked for keys like 'oak_slab' that don't exist in the table (the real key is 'minecraft:oak_slab'), silently false-flagging every vanilla slab as unmapped.
- The same mismatch affects __on_player_breaks_block's lookup (global_slab_to_block:str(block)), which could have prevented the full-block drop from ever triggering for vanilla double slabs, since only modded ids happened to already match the table's format.
- Added full_id(), which adds back the 'minecraft:' prefix to any id that doesn't already carry a namespace, and applied it at both lookup sites (audit_slab_map and __on_player_breaks_block) instead of rewriting the table.
## v1.2.1  (2026-07-21) 
- Fixed guess_full_block(): it had a namespace-stripping branch, `if (slice(id, 0, 10) == 'minecraft', slice(id, 10), id)`, meant to trim a leading "minecraft:" off suggested ids. slice(id, 0, 10) keeps 10 characters, which includes the trailing colon ("minecraft:"), but it was compared against the 9-character literal 'minecraft' (no colon), so the branch could never be true and was dead code for every slab, vanilla or modded.
- That stripping was also solving the wrong problem: every existing entry in global_slab_to_block, vanilla and modded alike, keeps its full namespaced id (e.g. 'minecraft:acacia_planks', 'cinchsmissingblocks:mossy_mud_bricks'). Fixing the slice math instead of removing it would have made guess_full_block() start stripping "minecraft:" off vanilla suggestions, producing entries inconsistent with the rest of the table.
- guess_full_block() now returns the raw ingredient id exactly as reported by recipe_data(), with no reformatting.

## v1.2.0  (2026-07-21)
- Added slab mappings for the "Cinch's Missing Blocks v4.0.0" mod.
- Added audit_slab_map() / guess_full_block() to detect slabs missing from global_slab_to_block and suggest a mapping for them on startup.

## v1.1.1  (2026-07-17)
- Added global_slab_audit_ignore, a dedicated exclusion set for legacy/special-case slabs with no full-block equivalent (currently: 'petrified_oak_slab'). audit_slab_map() now skips anything in this set instead of flagging it as unmapped on every startup. 

- Kept as its own map rather than adding null entries to global_slab_to_block, to avoid depending on unconfirmed has()-with-null-value semantics; the break handler already ignores unmapped slabs correctly regardless (an absent map key returns null on lookup either way).

## v1.1.0  (2026-07-17)
- Added audit_slab_map(), run automatically on __on_start(). It diffs the live 'slabs' block tag (whatever the running server actually has, including datapack/mod-added slabs) against global_slab_to_block and logs anything missing. 

- Added guess_full_block(), a best-effort lookup via the slab's own stonecutting/crafting recipe data, used only to suggest a ready-to-paste table entry in the log - it never writes to the live map automatically, since scarpet has no way to reach the wiki or minecraft.net changelog (no HTTP client in the API), and a wrong auto-guess would silently change drop behaviour.

## v1.0.0  (2026-07-16)
- Ported "Assembled Slabs" from a 55-file loot table datapack to a single scarpet app.
- Behaviour preserved exactly:
    * single slab broken           → 1 slab   (vanilla default)
    * double slab, not sneaking    → 2 slabs   (vanilla default)
    * double slab, sneaking        → 1 full block (new)
- Added global_slab_to_block lookup covering all 55 vanilla slab types present in the original datapack (1.21.x block set, including pale_oak_slab and resin_brick_slab). 
- Implemented as __on_player_breaks_block(): only double slabs broken while sneaking are intercepted and cancelled; every other break is left to run through vanilla, so tool damage,    mining stats, fortune/silk touch on non-slab blocks, etc. are unaffected.
