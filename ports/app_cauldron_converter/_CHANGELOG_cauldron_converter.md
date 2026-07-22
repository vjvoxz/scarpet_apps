# Changelog
All notable changes to the Cauldron Converter scarpet app will be documented in this file.

## [v1.4.0]  (2026-07-18)
- Added a second conversion: dirt dropped into a water cauldron now hardens into mud, mirroring vanilla's dirt + water bottle mechanic. Implemented as a single extra entry ('dirt' -> 'mud') in global_convert_map, so it goes through the exact same detection/conversion/error-handling path as the concrete powder colors - no other code changes needed.
- Updated the info() panel to mention the new mechanic.

## [v1.3.1] (2026-07-18)
- Replaced the status-style info() output with a proper about panel: app title, one-line description of the mechanic, and attribution/license. Command tree entries updated to call the public info() function directly (no leading underscore).

## [v1.3.0] (2026-07-18)
- Reworked the command interface to use a proper typed argument instead of a bare toggle: '/cauldron_concrete_converter debug <state>' now takes an explicit true/false via the built-in 'bool' argument type, calling set_debug(state) directly rather than flipping a hidden toggle.
- Renamed the status command/handler to 'info' / _info() and folded the current debug state into its output.
- Dropped the manual 'stop'/'start' commands to keep the command tree small; global_running + __on_start()/__on_close() still handle start/stop automatically on app load/unload.

## [v1.1.2] (2026-07-18)
- Corrected v1.1.1's fix: modify(e, 'item', [name, count, nbt]) rebuilds the item stack entirely from `nbt` whenever nbt is non-null (the `name` argument is only used when nbt is null). Deleting 'id' therefore threw "No key id in MapLike[...]" once nbt no longer had one at all. The correct fix is to overwrite 'id' with the new block's id rather than delete it, written as an explicit quoted SNBT string tag since put() on an nbt value parses its argument as SNBT.
- Wrapped the per-item conversion attempt in its own try()/catch (nested inside the outer scan try()) so one item failing to convert can't abort the rest of that tick's scan.

## [v1.1.1] (2026-07-18)
- Fixed the actual conversion bug found via debug tracing: the nbt from query(item_entity, 'item') embeds the ORIGINAL item's 'id' field. Passing that nbt straight back into modify(e,'item', [new_block, count, nbt]) let the stale embedded id silently win over the new block name, so items looked converted in the logs but never actually changed in-world. Now strips 'id' from the nbt (if present) before reassigning the item, and logs the post-conversion item state to confirm the fix.

## [v1.1.0] (2026-07-18)
- Wrapped _cauldron_scan() in try()/catch so an unexpected runtime error (bad particle/sound id, invalid block, etc.) gets logged //     instead of silently breaking the schedule() recursion (which would otherwise stop the whole loop forever with no visible symptom).
- Added global_debug flag + _debug() helper, and instrumented the scan with step-by-step chat/log tracing: how many item entities were found each tick, whether each item is in the convertible map, whether a water_cauldron was found at its position, and what its level is. On failure to find a cauldron, also logs the actual block(s) found there for troubleshooting.
- Added '/cauldron_concrete_converter debug' command to toggle tracing on/off without reloading the app.

## [v1.0.0]
- Initial release.
- Ported the datapack's 1-second scheduled scan loop to Scarpet's schedule() function.
- Rebuilt the "convertible items" tag as a lookup map covering all 16 concrete powder colors -> their hardened concrete block. 
- Rebuilt the item_modifier conversion step using modify(entity, 'item', ...) on matching item entities.
- Cauldron detection checks both the block at the item's position and the block directly beneath it (items rest on top of the cauldron block), requiring a 'water_cauldron' with level > 0.
- Added particle + sound feedback on conversion, mirroring the vanilla powder+water "fizz" feel.
- Added graceful start/stop handling: __on_start() kicks off the loop, __on_close() flips global_running to false so the recursive schedule() chain naturally stops (equivalent of uninstall.mcfunction clearing the repeating schedule).
- Added a small '/cauldron_concrete_converter' command (status / stop / start) so the app can be controlled in-game without needing to unload it.