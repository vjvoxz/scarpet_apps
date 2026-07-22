# Changelog

All notable changes to the Anti Ghast Grief scarpet app will be documented in this file.

## [v1.0.0]
- Initial Scarpet port of Vanilla Tweaks Anti Ghast Grief.
- **Change Log Header:** A dedicated version history section is now mapped at the top of the file for tracking updates.
- **Dynamic Command Config (__config()):** Replaced basic routing with native sub-command routing.
- Added global toggle state to enable/disable the mechanic dynamically.
- **State-Checked Handler:** The entity_load_handler now performs a quick check on the global variable global_enabled before modifying any ghasts, ensuring toggle commands are respected immediately.
