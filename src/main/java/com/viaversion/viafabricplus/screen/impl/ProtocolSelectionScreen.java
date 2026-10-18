/*
 * This file is part of ViaFabricPlus - https://github.com/ViaVersion/ViaFabricPlus
 * Copyright (C) 2021-2026 the original authors
 *                         - Florian Reuth <git@florianreuth.de>
 *                         - RK_01/RaphiMC
 * Copyright (C) 2023-2026 ViaVersion and contributors
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

package com.viaversion.viafabricplus.screen.impl;

import com.viaversion.viafabricplus.protocoltranslator.ProtocolTranslator;
import com.viaversion.viafabricplus.screen.VFPList;
import com.viaversion.viafabricplus.screen.VFPListEntry;
import com.viaversion.viafabricplus.screen.VFPScreen;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.awt.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import com.viaversion.viafabricplus.save.SaveManager;
public final class ProtocolSelectionScreen extends VFPScreen {

    public static final ProtocolSelectionScreen INSTANCE = new ProtocolSelectionScreen();

    private SlotList slotList;
    private boolean showFavoritesOnly = false;
    private Button allTabButton;
    private Button favoritesTabButton;

    private ProtocolSelectionScreen() {
        super("ViaFabricPlusLTS", true);
    }

    @Override
    protected void init() {
        this.setupDefaultSubtitle();

        // Tabs: "All" and "★ Favorites" (offset by 25px to the right to place immediately after the 20px back button)
        final int tabY = 5;
        this.allTabButton = this.addRenderableWidget(Button.builder(Component.translatable("protocol_selection.viafabricplus.all"), button -> {
            this.showFavoritesOnly = false;
            updateTabButtons();
            if (this.slotList != null) {
                this.slotList.populate(false);
            }
        }).pos(30, tabY).size(50, 20).build());

        this.favoritesTabButton = this.addRenderableWidget(Button.builder(Component.translatable("protocol_selection.viafabricplus.favorites"), button -> {
            this.showFavoritesOnly = true;
            updateTabButtons();
            if (this.slotList != null) {
                this.slotList.populate(true);
            }
        }).pos(85, tabY).size(95, 20).build());

        updateTabButtons();

        this.addRenderableWidget(Button.builder(Component.translatable("report.viafabricplus.button"), button -> ReportIssuesScreen.INSTANCE.open(this))
            .pos(width - 98 - 5, height - 25).size(98, 20).build());

        // List and Settings
        this.addRenderableWidget(this.slotList = new SlotList(this, this.minecraft, width, height, 3 + 3 /* start offset */ + (font.lineHeight + 2) * 3 /* title is 2 */, 30, font.lineHeight + 4, this.showFavoritesOnly));
        this.addRenderableWidget(Button.builder(Component.translatable("base.viafabricplus.settings"), button -> SettingsScreen.INSTANCE.open(this)).pos(width - 98 - 5, 5).size(98, 20).build());

        super.init();
    }

    private void updateTabButtons() {
        if (this.allTabButton != null && this.favoritesTabButton != null) {
            this.allTabButton.active = this.showFavoritesOnly;
            this.favoritesTabButton.active = !this.showFavoritesOnly;
        }
    }

    public void onFavoriteToggled(final ProtocolVersion version) {
        if (this.showFavoritesOnly && this.slotList != null) {
            this.slotList.populate(true);
        }
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (this.showFavoritesOnly && this.slotList != null && this.slotList.children().isEmpty()) {
            context.drawCenteredString(this.font, Component.translatable("protocol_selection.viafabricplus.no_favorites"), width / 2, height / 2, 0xFFAAAAAA);
        }
    }

    public static class SlotList extends VFPList {
        private static double scrollAmount;
        private final ProtocolSelectionScreen screen;

        public SlotList(ProtocolSelectionScreen screen, Minecraft minecraftClient, int width, int height, int top, int bottom, int entryHeight, boolean favoritesOnly) {
            super(minecraftClient, width, height, top, bottom, entryHeight);
            this.screen = screen;

            populate(favoritesOnly);
            initScrollY(scrollAmount);
        }

        public void populate(final boolean favoritesOnly) {
            this.clearEntries();
            final var settingsSave = SaveManager.INSTANCE.getSettingsSave();
            ProtocolVersion.getReversedProtocols().stream()
                    .filter(it -> !favoritesOnly || (settingsSave != null && settingsSave.isFavorite(it)))
                    .map(it -> new ProtocolSlot(this.screen, it))
                    .forEach(this::addEntry);
        }

        @Override
        protected void updateSlotAmount(double amount) {
            scrollAmount = amount;
        }
    }

    public static class ProtocolSlot extends VFPListEntry {
        private static final String STAR = "★";
        private static final int FAVORITE_COLOR = 0xFFFFAA00;
        private static final int NO_FAVORITE_COLOR = 0xFF555555;
        private static final int HOVER_STAR_COLOR = 0xFFAAAAAA;

        private final ProtocolSelectionScreen screen;
        private final ProtocolVersion protocolVersion;

        public ProtocolSlot(final ProtocolSelectionScreen screen, final ProtocolVersion protocolVersion) {
            this.screen = screen;
            this.protocolVersion = protocolVersion;
        }

        @Override
        public Component getNarration() {
            return Component.nullToEmpty(this.protocolVersion.getName());
        }

        @Override
        public void mappedMouseClicked(double mouseX, double mouseY, int button) {
            final var settingsSave = SaveManager.INSTANCE.getSettingsSave();
            if (settingsSave != null && mouseX >= getContentX() && mouseX <= getContentX() + 24) {
                settingsSave.toggleFavorite(this.protocolVersion);
                this.screen.onFavoriteToggled(this.protocolVersion);
                return;
            }

            if (Minecraft.getInstance().getConnection() != null) {
                // Setting the target version while connected to a server is not allowed as this will
                // literally break our code away.
                return;
            }

            ProtocolTranslator.setTargetVersion(this.protocolVersion);
        }

        @Override
        public void renderContent(final GuiGraphics context, final int mouseX, final int mouseY, final boolean hovered, final float deltaTicks) {
            final boolean isSelected = ProtocolTranslator.getTargetVersion().equals(protocolVersion);
            final var settingsSave = SaveManager.INSTANCE.getSettingsSave();
            final boolean isFavorite = settingsSave != null && settingsSave.isFavorite(this.protocolVersion);

            Color color = isSelected ? Color.GREEN : Color.RED;
            if (Minecraft.getInstance().getConnection() != null) {
                color = color.darker();
            }

            final Font textRenderer = Minecraft.getInstance().font;
            context.drawCenteredString(textRenderer, this.protocolVersion.getName(), getContentXMiddle(), getContentYMiddle() - textRenderer.lineHeight / 2, color.getRGB());

            final boolean hoveredStar = mouseX >= getContentX() && mouseX <= getContentX() + 24 && mouseY >= getContentY() && mouseY <= getContentY() + getContentHeight();
            final int starColor = isFavorite ? FAVORITE_COLOR : (hoveredStar ? HOVER_STAR_COLOR : NO_FAVORITE_COLOR);
            context.drawString(textRenderer, STAR, getContentX() + 12, getContentYMiddle() - textRenderer.lineHeight / 2, starColor);
        }
    }

}
