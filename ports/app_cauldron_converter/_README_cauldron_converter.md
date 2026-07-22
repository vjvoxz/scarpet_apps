# Cauldron Converter Scarpet App

Cauldron Converter is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that lets a water-filled cauldron do the job of a full water source block: drop in certain items and they instantly harden/convert into their solid counterpart, right inside the cauldron.

## How it works
- A background loop scans all item entities in the world once every second (20 ticks), without checking every single tick.
- For each item found, it checks two things: is this a *convertible* item, and is it sitting in (or resting on top of) a `water_cauldron` block that actually has water in it (level > 0)?
- If both are true, the item stack is converted in place into its target item — no new entity is spawned, the stack just becomes the hardened result, with a splash particle and drip sound for feedback.

## Features
- Converts all 16 colors of concrete powder into their solid concrete block counterpart.
- Converts dropped dirt into mud, mirroring vanilla's dirt + water bottle mechanic.
- Runs entirely on a lightweight, self-rescheduling 1-second loop instead of a per-tick check.
- Starts and stops automatically with the app's lifecycle (`/script load` / `/script unload`), no manual setup required.
- Built-in `info` command with a short usage/about panel.
- Toggleable debug tracing for troubleshooting, with error handling so one bad item can never break the loop for everyone else.

## Commands
1. `/cauldron_converter` or `/cauldron_converter info` — show the about panel.
2. `/cauldron_converter debug true` — turn on verbose step-by-step tracing in chat/log (useful if something doesn't seem to convert).
3. `/cauldron_converter debug false` — turn tracing back off.

## Metadata & Credits
* **App Name:** Cauldron Converter
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Vanilla Tweaks-style "Cauldron Concrete Powder" and "Cauldron Mud" data pack

## Attribution
This app recreates the behaviour of the Cauldron Concrete Powder and Cauldron Mud data packs popularized by the [Vanilla Tweaks](https://vanillatweaks.net/) community.

This project is an independent Scarpet implementation and does **not** contain or copy any files from the original pack's files, but provides credit to the creators, their rights and license terms still apply to the underlying idea.

## AI Assistance
This script was generated with assistance from **Claude**, an AI model created by [Anthropic](https://www.anthropic.com/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.