# Boat Froster (Scarpet Port)

The **Frost Walker** enchantment (on a rider's boots) also functions while the player is riding in a boat, turning nearby water into **Frosted Ice** as the boat travels across it.

## How it works
When a player wearing **Frost Walker** boots rides in a boat, the app continuously checks for nearby water beneath the boat and freezes it using the same radius that vanilla Frost Walker would normally use.

Unlike vanilla Frost Walker, ice created while boating is treated differently:

- While the boat is moving, nearby water continues to freeze as the boat travels.
- While the boat remains stationary, the app continuously refreshes the frosted ice, preventing it from melting.
- Once the boat moves away or the player leaves the boat, the protection expires after a short grace period, allowing the frosted ice to melt naturally.
- Frost Walker used while walking behaves exactly like vanilla Minecraft and is completely unaffected.

The app automatically detects the Frost Walker enchantment level and adjusts the freezing radius accordingly.

## Features
- ❄️ Enables **Frost Walker** while riding boats.
- 🚣 Creates frozen paths as boats travel across water.
- 🧊 Prevents boat-created frosted ice from melting while the boat is stationary.
- ⏳ Automatically restores normal vanilla melting behavior after the boat leaves.
- 📏 Uses the vanilla Frost Walker freezing radius based on enchantment level.
- 👢 Does not modify or replace normal Frost Walker behavior on foot.
- 🌍 Works across all Minecraft dimensions where water can be frozen.
- 🛠️ Includes an optional debug mode for troubleshooting.
- 💾 Lightweight implementation with automatic cleanup of temporary frozen tiles.

## Metadata & Credits
* **App Name:** Boat Froster
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Enchanted-Games "Boat Froster" data pack

## Attribution
This app recreates the behavior of the [Boat Froster](https://modrinth.com/datapack/boat-froster) data pack, licensed under **CC BY-NC 4.0**, by [enchanted-games](https://modrinth.com/user/enchanted-games).

This project is an independent Scarpet implementation and does **not** contain or copy any files from the original pack's files, but provides credit to the creators, their rights and license terms still apply to the underlying idea.

## AI Assistance Disclaimer
This script was generated with the assistance of **Claude**, an AI model created by [Anthropic](https://www.anthropic.com).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.