# Changelog

All notable changes to the Custom Nether Portals scarpet app will be documented in this file.

## [v1.1.0]  
- Bugfix: replaced `list += list_value` appends (which trigger element-wise/vector addition on lists, not append) with put(list, null, value) 
- Bugfix: replaced slice(stack, 0, -1) "pop" (unreliable negative end-index on a plain list, unlike get()'s negative indexing) with an append-only queue + head-pointer BFS, marking nodes visited at enqueue time.

## [v1.0.0]
- Initial port from the Vanilla Tweaks mcfunction implementation:
  - fire-placement detection via __on_player_right_clicks_block
  - flood-fill frame/interior search (map-based visited set, replacing the datapack's AreaEffectCloud markers)
  - X-axis then Z-axis orientation attempts, matching original
  - min_size / max_size / non_rectangular / crying_obsidian config
  - rectangle-only validation path when non_rectangular is disabled
  - /custom_nether_portals command tree (info, toggles, uninstall)
  - settings persisted via load_app_data()/store_app_data()