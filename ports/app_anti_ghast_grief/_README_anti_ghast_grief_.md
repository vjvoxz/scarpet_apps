# Anti Ghast Grief (Scarpet App)

Anti Ghast Grief is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that prevent ghasts from causing destruction in your Minecraft world. It accomplishes this by modifying the ghast's explosion mechanics so that they no longer blow up blocks.

## How it works
- **Detection:** The app listens for ghasts being spawned or loaded into the world.
- **Condition:** If the prevention toggle is enabled, the app modifies the ghast’s NBT data. 
- **Effect:** Ghasts’ ExplosionPower is set to 0, meaning their fireballs no longer break blocks.

## Features
- **Toggleable control:** Use /anti_ghast_grief status to enable or disable the mechanic instantly. 
- **Info command:** /anti_ghast_grief shows app details and current status. 
- **Lightweight:** Runs only when ghasts are loaded, minimizing performance impact.

## Commands
- **Start the App:** Run `/script load anti_ghast_grief` → activate the protection.
- **App Info:** Run `/anti_ghast_grief info` in the game chat to view the app's metadata, credits, and licensing details.
- **Swicht On/Off:** `/anti_ghast_grief status` → Toggles ghast grief prevention on/off.

## Metadata & Credits
* **App Name:** Anti Ghast Grief
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Vanilla Tweaks

## Attribution
This app recreates the behaviour of the **Anti Ghast Grief** data pack popularized by the [Vanilla Tweaks](https://vanillatweaks.net/) community.

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance
This script was generated with assistance from **Gemini**, an AI model created by [Google DeepMind](https://deepmind.google/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.