/*
 * This file is part of ViaFabricPlus - https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod
 * Copyright (C) 2021-2025 the original authors
 *                         - FlorianMichael/EnZaXD <florian.michael07@gmail.com>
 *                         - RK_01/RaphiMC
 * Copyright (C) 2023-2025 ViaVersion and contributors
 * Copyright (C) 2026 byMr712
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.viaversion.viafabricplus.settings.impl;

import com.viaversion.viafabricplus.api.settings.SettingGroup;
import com.viaversion.viafabricplus.api.settings.type.BooleanSetting;
import com.viaversion.viafabricplus.api.settings.type.ModeSetting;
import net.minecraft.network.chat.Component;

public final class Mr712Settings extends SettingGroup {

    public static final Mr712Settings INSTANCE = new Mr712Settings();

    public final BooleanSetting fixDoubleTranslation = new BooleanSetting(this, Component.translatable("mr712_settings.viafabricplus.fix_double_translation"), true);
    public final ModeSetting serverWarmupMode = new ModeSetting(this, Component.translatable("mr712_settings.viafabricplus.server_warmup_mode"), 0,
            Component.translatable("mr712_settings.viafabricplus.server_warmup_mode.on_demand_ui"),
            Component.translatable("mr712_settings.viafabricplus.server_warmup_mode.startup_preload"),
            Component.translatable("mr712_settings.viafabricplus.server_warmup_mode.native_priority"),
            Component.translatable("mr712_settings.viafabricplus.server_warmup_mode.disabled")
    );

    public Mr712Settings() {
        super(Component.translatable("setting_group_name.viafabricplus.mr712"));
        fixDoubleTranslation.setTooltip(Component.translatable("mr712_settings.viafabricplus.fix_double_translation.tooltip"));
        serverWarmupMode.setTooltip(() -> Component.translatable("mr712_settings.viafabricplus.server_warmup_mode.tooltip." + serverWarmupMode.getIndex()));
    }

}
