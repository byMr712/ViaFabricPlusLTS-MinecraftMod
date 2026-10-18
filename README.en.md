> **Language:** [Русский](README.md) · English

# [MR] ViaFabricPlusLTS

![Java 21](https://img.shields.io/badge/Java-21-blue.svg)
![Minecraft](https://img.shields.io/badge/Minecraft-1.21.x+-blue.svg)
![Fabric](https://img.shields.io/badge/Loader-Fabric-blue.svg)
![ModMenu](https://img.shields.io/badge/ModMenu-Supported-blue.svg)
![License](https://img.shields.io/badge/License-GPL_3.0-blue.svg)

An extended fork of **ViaFabricPlus** for **Minecraft (Fabric)** that restores connectivity from older client versions to servers up to the latest releases. The **LTS** (*Long-Term Support*) designation reflects the core purpose of this fork: extending the active lifespan of the original ViaFabricPlus mod across multiple older Minecraft versions, allowing players to keep their favorite modpacks while playing seamlessly on any server.

---

## What Does This Mod Do? (Player Friendly)

The original [ViaFabricPlus](https://github.com/ViaVersion/ViaFabricPlus) project by FlorianMichael and the ViaVersion team primarily focuses on supporting the latest Minecraft versions: when new updates arrive, active development naturally transitions to the newest client releases.

This puts players in a frustrating situation:
- Either remain on a favorite stable version (e.g. 1.21.4, 1.21.1, 1.21, etc.) with a custom-tuned modpack, but lose access to modern servers;
- Or update the game and break compatibility with existing mods.

### Fork Philosophy:
This project changes that philosophy: **it brings modern server connectivity back to older Minecraft versions**. You can stay on your client version with all your favorite mods and seamlessly play on any server!

---

## Key Features & Improvements

- **Backported Server Compatibility up to 26.3 / 1.21.11:** Bundles modern ViaVersion and ViaBackwards libraries, allowing older client versions to join servers of any version — from the first multiplayer Classic c0.0.15 release to modern snapshots and releases.
- **Async Protocol Warmup:** Eliminates the notorious 30-second login timeout ("ReadTimeoutException") when first connecting to newer versions. Mapping tables and pipelines are pre-warmed in the background upon UI selection or server pinging.
- **"Fix Double Translation" Option:** Enabled by default in the mod's "Mr712 Debug" settings group. When connecting to servers with ViaVersion / ViaBackwards plugins, dynamically detects if the server supports the client's native protocol and bypasses redundant client-side packet conversion. This significantly reduces CPU overhead, latency, and anticheat desyncs, while keeping the user's manual version settings intact.
- **Safe Netty Packet Decoder:** Gracefully recovers from malformed Brigadier command tree packets (`CommandTreeS2CPacket`) and trailing buffer bytes by substituting safe fallbacks, preventing unnecessary disconnects.
- **Mr712 Debug Settings Section:** Dedicated settings section in the mod settings screen containing advanced compatibility options, pipeline warmup mode toggles, and the double translation fix.

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
- **Fork Maintainer:** [byMr712](https://github.com/byMr712)
- **Fork Contributions:**
  - Backporting and adapting modern ViaVersion / ViaBackwards protocol translation libraries across multiple older Minecraft client versions (1.21, 1.21.1, 1.21.4 and other branches).
  - Enabling connections to modern server releases (including 1.21.11 and 26.3).
  - Dynamic double translation optimization (`fixDoubleTranslation`).
  - Asynchronous background protocol pre-warming (`ProtocolWarmupManager`).
  - Safe Netty packet decoding defense against malformed Brigadier command trees (`Safe Netty Decoder`).
  - Localization and dedicated Mr712 Debug settings group.
- **License:** In full accordance with GPL-3.0, this fork remains free, open-source software distributed under the terms of the **GPL-3.0 License**. All original copyrights and author attributions are preserved.
