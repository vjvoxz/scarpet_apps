# Apiarist Suit Scarpet App

Apiarist Suit is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that protects players from bee stings when wearing full Chainmail Armor.

## How it works
- **Step 1: Equipment Check**  
  On every tick, the app checks if the player is wearing a complete set of chainmail armor (helmet, chestplate, leggings, boots).
- **Step 2: Bee Detection**  
  If equipped, the app scans for bees within a 2-block radius around the player.
- **Step 3: Calm Effect**  
  Any nearby bees that are angry (have a target) are calmed: their `AngerTime` is reset to `0`, and their target is cleared.

## Features
- **Bee Protection**: Prevents bees from stinging when the player wears full chainmail.
- **Calming Aura**: Bees within 2 blocks are pacified automatically.
- **Lightweight Implementation**: Efficient scarpet script without heavy vanilla NBT checks.

## Usage
- Install the app in your Carpet Mod `scripts` folder.
- Run `/script load apiarist_suit` to enable it.
- Use `/apiarist_suit info` to view app details in-game.

## Metadata & Credits
* **App Name:** Apiarist Suit
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** VoodooBeard

## Attribution
This app recreates the behaviour of the **Apiarist Suit** data pack/mod by [VoodooBeard](https://mc.voodoobeard.com/#apiarist_suit).

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance
This script was generated with assistance from **Gemini**, an AI model created by [Google DeepMind](https://deepmind.google/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.
