# Anti Enderman Grief (Scarpet App)

**Anti Enderman Grief** is a lightweight [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that prevents endermen from picking up blocks by changing the "enderman_holdable" block tag to be empty, effectively making all blocks unpickable, keeping your terrain and builds safe from unwanted landscape remodeling.

Instead of running a heavy tick-loop to check for entity griefing, this app utilizes Scarpet's `create_datapack()` capability to dynamically inject an empty `enderman_holdable` block tag into the game's memory upon loading.

## Commands
- **Start the App:** Run `/script load anti_enderman_grief` to activate the protection.
- **App Info:** Run `/anti_enderman_grief info` in the game chat to view the app's metadata, credits, and licensing details.
- **Swicht On/Off:** `/anti_enderman_grief status` → Toggles creeper grief prevention on/off.

## Metadata & Credits
* **App Name:** Anti Enderman Grief
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Vanilla Tweaks

## Attribution
This app recreates the behaviour of the "Anti Enderman Grief" data pack popularized by the [Vanilla Tweaks](https://vanillatweaks.net/) community.

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance
This script was generated with assistance from **Gemini**, an AI model created by [Google DeepMind](https://deepmind.google/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.