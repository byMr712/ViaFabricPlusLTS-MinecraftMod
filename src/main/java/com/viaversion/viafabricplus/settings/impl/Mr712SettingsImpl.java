/*
 * This file is part of ViaFabricPlus - https://github.com/byMr712/ViaFabricPlusLTS-MinecraftMod
 * Copyright (C) 2021-2026 the original authors
 *                         - Florian Reuth <git@florianreuth.de>
 *                         - RK_01/RaphiMC
 * Copyright (C) 2023-2026 ViaVersion and contributors
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

import com.viaversion.viafabricplus.api.settings.base.BooleanSetting;
import com.viaversion.viafabricplus.api.settings.base.EnumSetting;
import com.viaversion.viafabricplus.settings.base.SettingGroupImpl;
import net.minecraft.network.chat.Component;

public final class Mr712SettingsImpl extends SettingGroupImpl {

    public enum ServerWarmupMode implements EnumSetting.EnumValue {
        ON_DEMAND_UI,
        STARTUP_PRELOAD,
        NATIVE_PRIORITY,
        DISABLED;

        private final Component component = Component.translatable("mr712_settings.viafabricplus.server_warmup_mode." + name().toLowerCase());

        @Override
        public Component component() {
            return this.component;
        }
    }

    private final BooleanSetting fixDoubleTranslation = registerBoolean("fix_double_translation", true);
    private final EnumSetting<ServerWarmupMode> serverWarmupMode = registerEnum("server_warmup_mode", ServerWarmupMode.ON_DEMAND_UI);

    public Mr712SettingsImpl() {
        super("mr712");
    }

    public BooleanSetting fixDoubleTranslation() {
        return this.fixDoubleTranslation;
    }

    public EnumSetting<ServerWarmupMode> serverWarmupMode() {
        return this.serverWarmupMode;
    }

}
