# Assembled Slabs Scarpet App

Assembled Slabs is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that when the player breaks a double slab while crouching drops a full block rather than 2 slabs.

Originally shipped as a pure vanilla datapack (per-slab loot_table/blocks/*.json overrides). This app replaces all of those files with a single event handler + lookup table.

## How it works
- **Basic mechanic** – Detect when a player breaks a double slab. 
- **Trigger condition** – Check if the player is crouching while breaking the slab.
- **Outcome** – Instead of dropping two separate slabs, the app drops a full block.

## Features
- **Crouch-breaking mechanic** – Breaking a double slab while crouching drops a full block.
- **Improved block drops** – Simplifies collection by reducing item clutter.
- **Scarpet port** – Originally a vanilla datapack by voodoobeard, now ported to scarpet for Carpet Mod.

## Usage
- Install the app in your Carpet Mod `scripts` folder.
- Run `/script load assambled_slabs` to enable it.
- Use `/assambled_slabs info` to view app details in-game.

## Metadata & Credits
* **App Name:** Assembled Slabs
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Voodoobeard

## Attribution
This app recreates the behaviour of the Assembled Slabs data pack/mod by [Voodoobeard](https://mc.voodoobeard.com/#assembled_slabs).

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance <Cluade>
This script was generated with assistance from **Claude**, an AI model created by [Anthropic](https://www.anthropic.com/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.