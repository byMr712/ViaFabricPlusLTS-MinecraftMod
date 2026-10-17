# ViaFabricPlusLTS (1.21.5)

<div align="center">

[![License: GPL-3.0](https://img.shields.io/badge/License-GPL--3.0-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.5-brightgreen.svg)](https://minecraft.net/)
[![Fabric Loader](https://img.shields.io/badge/Fabric-Loader-blue.svg)](https://fabricmc.net/)

**Extended Long-Term Support (LTS) edition of ViaFabricPlus for Minecraft 1.21.5**  
Allows connecting to almost any server version — from Alpha/Beta/Classic up to the latest **26.3** and Bedrock.

[Russian Description](README.md) | [Source Code](https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod) | [Report Issue](https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod/issues)

</div>

---

## LTS Build Features

- **Support for servers up to 26.3:** Updated protocol translation stack (ViaVersion / ViaBackwards) enables clients running Minecraft 1.21.5 to connect to modern server protocol versions (1.21.11, 26.1, 26.2, 26.3).
- **Improved Connection Stability:** Fixed inventory and movement desyncs, and removed arbitrary NBT size limits.
- **Safe Netty Decoder:** Intercepts corrupted Brigadier command tree packets (`CommandTreeS2CPacket`) and residual buffer bytes, applying safe fallbacks to prevent disconnects caused by incompatible server plugins.
- **Mr712 Debug Settings:** Advanced compatibility options and warmup toggles are hidden by default and cleanly toggled via the in-game mod settings.

---

## Supported Server Versions

| Type | Version Range |
| :--- | :--- |
| **Release** | From 1.0.0 to **26.3** (including 1.21, 1.21.4, 1.21.11, 26.1, 26.2, 26.3) |
| **Beta** | b1.0 — b1.8.1 |
| **Alpha** | a1.0.15 — a1.2.6 |
| **Classic** | c0.0.15 — c0.30 (including [CPE](https://wiki.vg/Classic_Protocol_Extension)) |
| **Snapshots** | 3D Shareware, 20w14infinite, Combat Snapshots 8c |

---

## Installation

1. Download the latest `ViaFabricPlusLTS-1.21.5-byMr712.jar` from [Releases](https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod/releases).
2. Drop the `.jar` into the `mods` folder of your Fabric instance.
3. Make sure **Fabric Loader** (>=0.16.14) and **Fabric API** for your Minecraft version are installed.
4. Launch the game — the version selection button will appear on the main title screen and multiplayer menu.

---

## Building from Source

To build the mod:

```bash
git clone https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod.git
cd ViaFabricPlusLTS-MinecraftMod
./gradlew build
```

The compiled jar will be in `build/libs/`.

---

## License

All original work belongs to [ViaVersion](https://github.com/ViaVersion) / [FlorianMichael](https://github.com/FlorianMichael) / [RaphiMC](https://github.com/RaphiMC) and the contributors of ViaFabricPlus.  
Distributed under the **GPL-3.0** license.
