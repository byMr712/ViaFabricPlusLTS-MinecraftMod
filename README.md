# ViaFabricPlusLTS (1.21.2 - 1.21.3)

<div align="center">

[![License: GPL-3.0](https://img.shields.io/badge/License-GPL--3.0-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.2%20--%201.21.3-brightgreen.svg)](https://minecraft.net/)
[![Fabric Loader](https://img.shields.io/badge/Fabric-Loader-blue.svg)](https://fabricmc.net/)

**Расширенная долгосрочная (LTS) редакция мода ViaFabricPlus для Minecraft 1.21.2 и 1.21.3**  
Позволяет подключаться к серверам практически любых версий — от Alpha/Beta/Classic до новейших **26.3** и Bedrock.

[English Description](README.en.md) | [Исходный код](https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod) | [Сообщить об ошибке](https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod/issues)

</div>

---

## Особенности сборки LTS

- **Поддержка серверов вплоть до 26.3:** Обновлённый стек трансляции протоколов (ViaVersion / ViaBackwards) позволяет клиенту на версии Minecraft 1.21.2/1.21.3 заходить на сервера актуальных версий протокола (1.21.4, 1.21.11, 26.1, 26.2, 26.3).
- **Повышенная стабильность соединения:** Исправлены проблемы синхронизации состояний инвентаря, движения и парсинга сетевых NBT-тегов без искусственных ограничений размера.
- **Защита декодера пакетов (Safe Netty Decoder):** Перехватывает повреждённые Brigadier-пакеты дерева команд (`CommandTreeS2CPacket`) и остаточные байты буфера, подставляя безопасный фоллбэк и предотвращая дисконнекты при несовместимости плагинов сервера.
- **Настройки для отладки Mr712:** Расширенные опции совместимости и переключатели режимов прогрева скрыты по умолчанию и включаются отдельной аккуратной опцией в меню мода.

---

## Поддерживаемые версии серверов

| Тип | Диапазон версий |
| :--- | :--- |
| **Release** | От 1.0.0 до **26.3** (включая 1.21, 1.21.4, 1.21.11, 26.1, 26.2, 26.3) |
| **Beta** | b1.0 — b1.8.1 |
| **Alpha** | a1.0.15 — a1.2.6 |
| **Classic** | c0.0.15 — c0.30 (включая [CPE](https://wiki.vg/Classic_Protocol_Extension)) |
| **Снапшоты** | 3D Shareware, 20w14infinite, Combat Snapshots 8c |

---

## Установка

1. Скачайте последнюю версию мода `ViaFabricPlusLTS-1.21.2-byMr712.jar` из раздела [Releases](https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod/releases).
2. Поместите `.jar` файл в папку `mods` вашего экземпляра Fabric.
3. Убедитесь, что установлен **Fabric Loader** (>=0.16.9) и **Fabric API** для вашей версии Minecraft (1.21.2 или 1.21.3).
4. Запустите игру — кнопка выбора версии появится на главном экране и в меню сетевой игры.

---

## Сборка из исходников

Для сборки мода:

```bash
git clone https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod.git
cd ViaFabricPlusLTS-MinecraftMod
./gradlew build
```

Собранный файл будет находиться в директории `build/libs/`.

---

## Лицензия

Все оригинальные наработки принадлежат [ViaVersion](https://github.com/ViaVersion) / [FlorianMichael](https://github.com/FlorianMichael) / [RaphiMC](https://github.com/RaphiMC) и авторам проекта ViaFabricPlus.  
Проект распространяется на условиях лицензии **GPL-3.0**.
