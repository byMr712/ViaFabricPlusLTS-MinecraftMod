> **Language:** [Русский](README.md) · English

# [MR] ViaFabricPlus BackPort

![Java 21](https://img.shields.io/badge/Java-21-blue.svg)
![Minecraft](https://img.shields.io/badge/Minecraft-1.21.4-blue.svg)
![Fabric](https://img.shields.io/badge/Loader-Fabric-blue.svg)
![ModMenu](https://img.shields.io/badge/ModMenu-Supported-blue.svg)
![License](https://img.shields.io/badge/License-GPL_3.0-blue.svg)

An extended fork of **ViaFabricPlus** for **Minecraft 1.21.4 (Fabric)** that restores the ability for older client versions to connect to ALL servers up to the latest releases (**1.21.11** and **26.3**), something upstream does not provide.

---

## What Does This Mod Do? (Player Friendly)

The original [ViaFabricPlus](https://github.com/ViaVersion/ViaFabricPlus) by FlorianMichael and the ViaVersion team follows a strict policy: development targets **only the very latest Minecraft release**, while support for older client versions is dropped immediately upon a new Minecraft release.

This puts players in a frustrating situation:
- Either remain on a favorite stable version (e.g. 1.21.4) with a custom-tuned modpack, but lose access to modern servers;
- Or update the game and break compatibility with existing mods.

### Fork Philosophy:
This project changes that philosophy: **it brings modern server connectivity back to older Minecraft versions**. You can stay on your client version with all your favorite mods and seamlessly play on any server!

---

## Key Features & Improvements

- **Backported Server Compatibility up to 26.3 / 1.21.11:** Bundles modern ViaVersion and ViaBackwards libraries, allowing a 1.21.4 client to join servers of any version — from the first multiplayer Classic c0.0.15 release to modern snapshots and releases.
- **Async Protocol Warmup:** Eliminates the notorious 30-second login timeout ("ReadTimeoutException") when first connecting to newer versions. Mapping tables and pipelines are pre-warmed in the background upon UI selection or server pinging.
- **Hybrid Server Support (ViaBackwards Native Priority):** Automatically detects servers (e.g. Paper 1.21.11 with ViaBackwards) advertising native 1.21.4 (769) protocol support and connects natively without dual client-side translation. This resolves carpet hitbox desyncs and false-positive anticheat kicks.
- **Safe Netty Packet Decoder:** Gracefully recovers from malformed Brigadier command tree packets (`CommandTreeS2CPacket`) and trailing buffer bytes by substituting safe fallbacks, preventing unnecessary disconnects.
- **Mr712 Debug Settings Toggle:** Advanced warmup and compatibility options are neatly tucked away behind an optional developer toggle in the mod settings.

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

1. Download the latest release `ViaFabricPlusBackPort-1.21.4-byMr712.jar` from the [Releases](https://github.com/byMr712/ViaFabricPlusBackPort-1.21.4-MinecraftMod/releases) page.
2. Place the `.jar` into your Minecraft instance's `mods` folder.
3. Ensure you have **Fabric Loader** (>=0.16.10) and **Fabric API** installed for your Minecraft version.
4. Launch the game — the version selector button will appear on the title and multiplayer screens.

---

## Building from Source

To build the mod from source:

```bash
git clone https://github.com/byMr712/ViaFabricPlusBackPort-1.21.4-MinecraftMod.git
cd ViaFabricPlusBackPort-1.21.4-MinecraftMod
./gradlew build
```

The resulting `.jar` file will be generated in `build/libs/`.

---

## License

All original work belongs to [ViaVersion](https://github.com/ViaVersion) / [FlorianMichael](https://github.com/FlorianMichael) / [RaphiMC](https://github.com/RaphiMC) and ViaFabricPlus contributors.  
This project is licensed under the **GPL-3.0 License**.
