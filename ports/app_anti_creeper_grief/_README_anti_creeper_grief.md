# Anti Creeper Grief Scarpet App

Anti Creeper Grief is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that prevents creepers from destroying terrain in your Minecraft world. Instead of leaving behind craters, creepers will still explode but cause no block damage; keeping yourself and your builds safe and intact.

## How it works
- **Explosion Override**: When a creeper is spawned or loaded, its `ExplosionRadius` is set to `0`.
- **Toggle Control**: Players can enable or disable the mechanic on the fly using app commands.
- **Safe Explosions**: Creepers still explode visually and damage entities, but blocks remain untouched.

### Features
- **Grief Prevention**: Stops creepers from blowing holes in the terrain.
- **Dynamic Control**: Toggle the app’s functionality with `/anti_creeper_grief status`.
- **Lightweight Integration**: Simple scarpet script, no external dependencies.

## Commands
- **App Info:** `/anti_creeper_grief` → Displays app info and current status.
- **Swicht On/Off:** `/anti_creeper_grief enable` → Toggles creeper grief prevention on/off.

## Compatibility
- Minecraft version: 1.21+
- Carpet Mod version: v1.4.112+

## Metadata & Credits
* **App Name:** Anti Enderman Grief
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Vanilla Tweaks

## Attribution
This app recreates the behaviour of the **Anti Creeper Grief** data pack popularized by the [Vanilla Tweaks](https://vanillatweaks.net/) community.

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance
This script was generated with assistance from **Gemini**, an AI model created by [Google DeepMind](https://deepmind.google/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See _CHANGELOG_anti_creeper_grief.md for full detailed version history.
