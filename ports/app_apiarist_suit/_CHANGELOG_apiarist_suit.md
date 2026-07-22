# Changelog

All notable changes to the Apiarist Suit scarpet app will be documented in this file.

## [v1.1.0]
- Updated bee NBT clearing to support Minecraft 1.21.11 and newer versions mechanics, fixing an issue where bees retained their anger state.

## [v1.0.2]
- Removed invalid `modify(bee, 'target', null)` call. NBT merge of {AngerTime:0} is sufficient to calm bees.

## [v1.0.1]
- Fixed `entity_area` crash by converting the scalar distance value (2) into a 3D vector ([2, 2, 2]).

## [v1.0.0]
- Initial Scarpet port to replace heavy vanilla NBT checks.
- Added built-in change log and metadata header.