# Conductive Elytra Scarpet App

Conductive Elytra is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that punishes flying through a thunderstorm on an elytra by occasionally striking you with lightning.

## How it works
- Every 10 seconds, the app checks the weather and every online player.
- If it is currently thundering, and a player is actively gliding (`fall_flying` pose) with an elytra equipped in their chest slot, they are eligible for a strike.
- Each eligible player then rolls a random chance; on success, a lightning bolt is summoned directly on top of them.

## Features
- Global-scope app — one shared check loop covers all players, no per-player state needed.
- Self-rescheduling 10-second check loop (`schedule()`), matching the timing of the original data pack.
- Configurable strike chance via the `global_strike_chance` variable at the top of the script.
- No dependency on scoreboards, tags, or persistent storage — purely reactive to live player/weather state each cycle.

## Commands
1. Place `conductive_elytra.sc` in your world's `scripts` folder (or `.minecraft/config/carpet/scripts` in singleplayer to make it available in any world).
2. Load the app in-game with `/script load conductive_elytra`.
3. Fly with an elytra during a thunderstorm and see what happens. Adjust `global_strike_chance` in the script (default `0.07`, i.e. 7%) before loading if you want a different risk level.

## Metadata & Credits
* **App Name:** Conductive Elytra
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** VoodooBeard (Matt Parkes)

## Attribution
This app recreates the behaviour of the Conductive Elytra data pack by [VoodooBeard](https://mc.voodoobeard.com/).

This project is an independent Scarpet implementation and does **not** contain or copy any files from the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance <Cluade>
This script was generated with assistance from **Claude**, an AI model created by [Anthropic](https://www.anthropic.com/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.