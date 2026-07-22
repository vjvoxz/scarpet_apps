# Custom Nether Portals Scarpet App

Custom Nether Portals is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that replaces vanilla's rectangle-only nether portal validation with a flexible flood-fill frame search, allowing non-rectangular obsidian frames, crying obsidian frames, and configurable min/max frame sizes.

## How it works
- **Detect ignition:** When a player lights a portal with flint & steel or a fire charge, the app intercepts the event and schedules a validation check on the resulting fire block.
- **Flood-fill search:** Starting from the fire block, the app performs a BFS (breadth-first search) outward along the X-axis plane; if that fails, it retries along the Z-axis plane. The search expands through open air, collects all interior blocks, and stops when it hits obsidian (or crying obsidian, if enabled) frame blocks.
- **Validates & builds:** If the collected interior meets the configured size and shape rules, every interior block is filled with nether portal blocks along the correct axis.

## Features
- **Non-rectangular frames** - Ignite portals with L-shaped, T-shaped, or any freeform obsidian outline (toggleable).
- **Crying obsidian support** - Use crying obsidian as part of (or all of) the portal frame (toggleable).
- **Configurable size limits** - Set minimum and maximum frame interior sizes (default: 10–84 blocks).
- **Persistent settings** - All configuration is saved to disk and survives server restarts.
- **No leftover entities** - Uses a native map-based visited set instead of AreaEffectCloud markers, so no cleanup entities are left behind.

## Commands
All commands require operator permission and are accessed under `/custom_nether_portals`:

| Command | Description |
|---|---|
| (no arguments) | Show current settings and info |
|`non_rectangular <true\|false>`| Enable or disable non-rectangular portal frames |
|`crying_obsidian <true\|false>` |  Enable or disable crying obsidian in frames |
|`min_size <1-512>` | Set the minimum interior block count |
| `max_size <1-512>` | Set the maximum interior block count |
| `uninstall` | Delete saved settings (then run `/script unload custom_nether_portals` to fully remove) |


## Metadata & Credits
* **App Name:** Custom Nether Portals
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Vanilla Tweaks ([vanillatweaks.net](https://vanillatweaks.net))

## Attribution
This app recreates the behaviour of the **Custom Nether Portals** data pack popularized by the [Vanilla Tweaks](https://vanillatweaks.net/) community.

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance
This script was generated with assistance from **Claude**, an AI model created by [Anthropic](https://www.anthropic.com/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.
