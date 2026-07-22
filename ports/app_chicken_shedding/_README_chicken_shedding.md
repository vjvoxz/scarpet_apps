# Chicken Shedding Scarpet App

Chicken Shedding is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that modifies the chicken laying behavior so that chickens have a 20% chance of shedding a feather instead of laying an egg. If they lay an egg, the type of egg depends on the chicken's variant.

## How it works
- The app overrides the default chicken loot table (`gameplay/chicken_lay.json`) by injecting a custom one directly into memory.

- Each time a chicken lays, the loot table rolls once:
    - 20% chance → chicken drops a feather instead of an egg.
    - 80% chance → chicken lays an egg, but the egg type depends on its variant:
        Temperate chickens → regular egg (`minecraft:egg`)
        Warm chickens → brown egg (`minecraft:brown_egg`)
        Cold chickens → blue egg (`minecraft:blue_egg`)

## Features
* **Feather shedding:** Chickens have a 20% chance to drop a feather instead of laying an egg.
* **Variant-based eggs:** Egg type depends on the chicken’s biome variant.
* **Dynamic datapack injection:** The loot table is injected directly into memory at startup, avoiding server overhead.
* **Info command:** `/chicken_shedding info` shows details about the app, its purpose, and credits.
* **Lightweight design:** No tick-based checks; behavior is handled entirely through loot table logic.
* **Logging:** Confirms successful loading of the virtual datapack in the server logs.

## Commands
1. `/chicken_shedding info` — show the about panel.

## Metadata & Credits
* **App Name:** Chicken Shedding
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Voodoobeard's Datapacks

## Attribution
This app recreates the behaviour of *"Chicken Shedding"* data pack/mod by [Voodoobeard](https://mc.voodoobeard.com/#chicken_shedding).

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance <Gemini>
This script was generated with assistance from **Gemini**, an AI model created by [Google DeepMind](https://deepmind.google/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.