> **Language:** [Русский](README.md) · English

# [MR] ViaFabricPlusLTS

![Java 21](https://img.shields.io/badge/Java-21-blue.svg)
![Minecraft](https://img.shields.io/badge/Minecraft-1.21.x+-blue.svg)
![Fabric](https://img.shields.io/badge/Loader-Fabric-blue.svg)
![ModMenu](https://img.shields.io/badge/ModMenu-Supported-blue.svg)
![License](https://img.shields.io/badge/License-GPL_3.0-blue.svg)

An extended fork of [**ViaFabricPlus**](https://github.com/ViaVersion/ViaFabricPlus) for **Minecraft (Fabric)** that restores connectivity from older client versions to servers up to the latest releases. The **LTS** (*Long-Term Support*) designation reflects the core purpose of this fork: extending the active lifespan of the original ViaFabricPlus mod across multiple older Minecraft versions, allowing players to keep their favorite modpacks while playing seamlessly on any server.

---

## What Does This Mod Do?

The original [ViaFabricPlus](https://github.com/ViaVersion/ViaFabricPlus) project by FlorianMichael and the ViaVersion team primarily focuses on supporting the latest Minecraft versions: when new updates arrive, active development naturally transitions to the newest client releases.

This puts players in a frustrating situation:
- Either remain on a favorite stable version (e.g. 1.21.4, 1.21.1, 1.21, etc.) with a custom-tuned modpack, but lose access to modern servers;
- Or update the game and break compatibility with existing mods.

### Fork Philosophy:
This project changes that philosophy: **it brings modern server connectivity back to older Minecraft versions**. You can stay on your client version with all your favorite mods and seamlessly play on any server!

---

## Supported Server Versions

| Type | Version Range |
| :--- | :--- |
| **Release** | 1.0.0 through **26.3** (including 1.21.4, 1.21.11, 26.1, 26.2, 26.3) |
| **Beta** | b1.0 — b1.8.1 |
| **Alpha** | a1.0.15 — a1.2.6 |
| **Classic** | c0.0.15 — c0.30 (including [CPE](https://wiki.vg/Classic_Protocol_Extension)) |
| **Snapshots** | 3D Shareware, 20w14infinite, Combat Snapshots 8c |

---

## Installation

1. Download the release `.jar` for your Minecraft version (e.g. `MrViaFabricPlusLTS-1.21.4-byMr712.jar`) from the [Releases](https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod/releases) page.
2. Place the `.jar` into your Minecraft instance's `mods` folder.
3. Ensure you have **Fabric Loader** (>=0.16.10) and **Fabric API** installed for your Minecraft version.
4. Launch the game — the version selector button will appear on the title and multiplayer screens.

---

## Building from Source

To build the mod from source:

```bash
git clone https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod.git
cd ViaFabricPlusLTS-MinecraftMod
./gradlew build
```

The resulting `.jar` file will be generated in `build/libs/`.

---

## License & Attribution

- **Original Project:** [ViaFabricPlus](https://github.com/ViaVersion/ViaFabricPlus)  
  Authors: [FlorianMichael/EnZaXD](https://github.com/FlorianMichael), [RK_01/RaphiMC](https://github.com/RaphiMC), and ViaVersion contributors.  
  Licensed under the **GNU General Public License v3.0 (GPL-3.0)**.
- **Fork Maintainer (LTS Releases):** [byMr712](https://github.com/byMr712)
- **Technical Contributions (Engineering & Architecture):**
  - **Seamless Upstream Override via SemVer Priority Resolution:** Retaining the original mod ID (`id: "viafabricplus"`) while bumping the patch version (e.g. `4.0.6-LTS` over upstream `4.0.5`). This leverages Fabric Loader's native Semantic Versioning resolver to prioritize the LTS fork over upstream builds without SAT-solver dependency conflicts (`breaks`/`provides`), preserves complete ecosystem compatibility with third-party addons depending on `viafabricplus`, and maintains 100% cross-platform parity without OS-specific dialog hacks (Win32/Swing).
  - **Multi-version Protocol Stack Backporting:** Adapting and integrating modern `ViaVersion` and `ViaBackwards` libraries into older client environments (1.21, 1.21.1, 1.21.4, and other branches), enabling seamless translation up to server versions 1.21.11 and 26.3.
  - **Dynamic Double Translation Optimization (`fixDoubleTranslation`):** When connecting to hybrid servers (e.g. Paper with ViaVersion/ViaBackwards) or proxies (Velocity/BungeeCord), the client conducts a fast pre-handshake probe (`ProtocolVersionDetector`). If the server advertises native protocol support for the client, translation is bypassed for the session. This session override automatically reverts upon disconnect via `injectPreviousVersionReset` channel listeners without altering the user's persistent version configuration.
  - **Asynchronous Pipeline Pre-warming (`ProtocolWarmupManager`):** Offloads protocol mapping table initialization to a dedicated background daemon thread (`ViaFabricPlus-Warmup`) with reduced priority. Pre-warming occurs upon UI selection or server pinging, eliminating 30-second handshake timeouts (`ReadTimeoutException`) and frame drops.
  - **Netty Pipeline Hardening (`Safe Netty Decoder`):** Catches and gracefully recovers from malformed Brigadier command tree packets (`CommandTreeS2CPacket`) and flushes lingering buffer bytes, preventing client disconnects caused by `DecoderException` on modded/hybrid servers.
  - **Favorites Protocol System & Header Layout Optimization:** Interactive star toggle `★` for every protocol and `all` / `favorites` filter tabs with configuration persistence, empty state hints, and geometric layout offsets preventing back-button overlap.
  - **Authentication Skin Signature Hardening (Authlib Empty Signature Guard):** Intercepts `hasSignature()` and `validateProperty()` in `Property` and key verifiers (`YggdrasilServicesKeyInfo` / `MinecraftServicesKeyInfo`), preventing connection drops caused by `SignatureException` on offline-mode servers, proxies, and unsigned skin providers.
  - **Modular Settings Architecture (`Mr712Settings`):** Standalone settings group with automatic JSON serialization via `SettingsSave`, complete bilingual localization (`ru_ru`, `en_us`), and hover tooltip fixes (removing raw LF characters in favor of adaptive multi-line wrapping via `wrapLines` + `drawOrderedTooltip`).
- **License:** In full accordance with GPL-3.0, this fork remains free, open-source software distributed under the terms of the **GPL-3.0 License**. All original copyrights and author attributions are preserved.
