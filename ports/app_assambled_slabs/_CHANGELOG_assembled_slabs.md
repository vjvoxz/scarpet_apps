# Changelog

All notable changes to the Assembled Slabs scarpet app will be documented in this file.

## [v1.4.0]  (2026-##-##)
- Added support for unmapped slabs in Minecraft Java Edition 26.3 "?".
- Extended global_slab_to_block dictionary with new slab-to-block mappings.
- Fixed warnings for missing slab definitions.

## [v1.3.0]  (2026-##-##)
- Added support for unmapped slabs in Minecraft Java Edition 26.2 "Chaos Cubed".
- Extended global_slab_to_block dictionary with new slab-to-block mappings.
- Fixed warnings for missing slab definitions.

## [v1.2.0]  (2026-07-19)
- Added support for unmapped slabs in cinchsmissingblocks pack.
- Extended global_slab_to_block dictionary with new slab-to-block mappings.
- Fixed warnings for missing slab definitions.

## [v1.1.1]  (2026-07-17)
- Added global_slab_audit_ignore, a dedicated exclusion set for legacy/special-case slabs with no full-block equivalent (currently: 'petrified_oak_slab'). audit_slab_map() now skips anything in this set instead of flagging it as unmapped on every startup. 

- Kept as its own map rather than adding null entries to global_slab_to_block, to avoid depending on unconfirmed has()-with-null-value semantics; the break handler already ignores unmapped slabs correctly regardless (an absent map key returns null on lookup either way).

## [v1.1.0]  (2026-07-17)
- Added audit_slab_map(), run automatically on __on_start(). It diffs the live 'slabs' block tag (whatever the running server actually has, including datapack/mod-added slabs) against global_slab_to_block and logs anything missing. 

- Added guess_full_block(), a best-effort lookup via the slab's own stonecutting/crafting recipe data, used only to suggest a ready-to-paste table entry in the log - it never writes to the live map automatically, since scarpet has no way to reach the wiki or minecraft.net changelog (no HTTP client in the API), and a wrong auto-guess would silently change drop behaviour.

## [v1.0.0]  (2026-07-16)
- Ported "Assembled Slabs" from a 55-file loot table datapack to a single scarpet app.
- Behaviour preserved exactly:
    * single slab broken           → 1 slab   (vanilla default)
    * double slab, not sneaking    → 2 slabs   (vanilla default)
    * double slab, sneaking        → 1 full block (new)
- Added global_slab_to_block lookup covering all 55 vanilla slab types present in the original datapack (1.21.x block set, including pale_oak_slab and resin_brick_slab). 
- Implemented as __on_player_breaks_block(): only double slabs broken while sneaking are intercepted and cancelled; every other break is left to run through vanilla, so tool damage,    mining stats, fortune/silk touch on non-slab blocks, etc. are unaffected.