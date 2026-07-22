# Changelog

All notable changes to the Boat Froster scarpet app will be documented in this file.

### [v1.1.0]
- Fixed boat/vehicle detection.
  - Minecraft 1.21.x reports wood-specific entity types (for example `oak_boat` and `cherry_chest_boat`) instead of generic `boat`/`chest_boat`.
  - Bamboo boats are separate `raft` and `chest_raft` entities.
  - All variants are now recognized.

- Fixed Frost Walker level detection.
  - In Minecraft 1.21.8, the `minecraft:enchantments` data component is itself the `id → level` map.
  - The previous nested `levels` lookup is no longer used.
  - Legacy NBT fallback support remains for older Minecraft versions.

- Added toggleable debug logging.
  - Global variable: `global_bf_debug`
  - Used to diagnose the boat detection and enchantment lookup issues.
  - Disabled by default for normal gameplay.

### [v1.0.0]
- Initial Scarpet port.