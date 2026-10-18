/*
 * This file is part of ViaFabricPlus - https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod
 * Copyright (C) 2021-2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and RK_01/RaphiMC
 * Copyright (C) 2023-2024 contributors
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

package de.florianmichael.viafabricplus.settings.impl;

import de.florianmichael.viafabricplus.settings.base.BooleanSetting;
import de.florianmichael.viafabricplus.settings.base.ModeSetting;
import de.florianmichael.viafabricplus.settings.base.SettingGroup;
import net.minecraft.text.Text;

public class Mr712Settings extends SettingGroup {

    private static final Mr712Settings INSTANCE = new Mr712Settings();

    public final BooleanSetting fixDoubleTranslation = new BooleanSetting(this, Text.translatable("mr712_settings.viafabricplus.fix_double_translation"), true);
    public final ModeSetting serverWarmupMode = new ModeSetting(this, Text.translatable("mr712_settings.viafabricplus.server_warmup_mode"), 0,
            Text.translatable("mr712_settings.viafabricplus.server_warmup_mode.on_demand_ui"),
            Text.translatable("mr712_settings.viafabricplus.server_warmup_mode.startup_preload"),
            Text.translatable("mr712_settings.viafabricplus.server_warmup_mode.native_priority"),
            Text.translatable("mr712_settings.viafabricplus.server_warmup_mode.disabled")
    );

    public Mr712Settings() {
        super(Text.translatable("setting_group_name.viafabricplus.mr712"));
        fixDoubleTranslation.setTooltip(Text.translatable("mr712_settings.viafabricplus.fix_double_translation.tooltip"));
        serverWarmupMode.setTooltip(() -> Text.translatable("mr712_settings.viafabricplus.server_warmup_mode.tooltip." + serverWarmupMode.getIndex()));
    }

    public static Mr712Settings global() {
        return INSTANCE;
    }

}
