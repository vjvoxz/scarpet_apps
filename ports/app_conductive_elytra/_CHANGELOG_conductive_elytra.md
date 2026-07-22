# Changelog

All notable changes to the Conductive Elytra Scarpet app are documented here.

## [v1.0]
- Initial Scarpet port of the Conductive Elytra data pack mechanic.
- Global-scope app, self-rescheduling every 200 ticks (10 seconds).
- Uses `query(p, 'pose') == 'fall_flying'` in place of the data pack's `FallFlying` NBT + `is_swimming:false` pair (pose is a single exclusive value, so this covers both checks at once).
- Uses `query(p, 'holds', 'chest')` to confirm an elytra is actually equipped in the chest slot.
- `weather() == 'thunder'` matches the vanilla thundering check (per Carpet docs, `weather()` always reports `'thunder'` when thundering, regardless of rain state).
- `rand(1) < global_strike_chance` for the strike roll.