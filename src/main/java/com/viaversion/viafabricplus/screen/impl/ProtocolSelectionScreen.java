/*
 * This file is part of ViaFabricPlus - https://github.com/ViaVersion/ViaFabricPlus
 * Copyright (C) 2021-2025 the original authors
 *                         - FlorianMichael/EnZaXD <florian.michael07@gmail.com>
 *                         - RK_01/RaphiMC
 * Copyright (C) 2023-2025 ViaVersion and contributors
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
import com.viaversion.vialoader.util.ProtocolVersionList;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;

import java.awt.Color;
import com.viaversion.viafabricplus.save.SaveManager;

public final class ProtocolSelectionScreen extends VFPScreen {

    public static final ProtocolSelectionScreen INSTANCE = new ProtocolSelectionScreen();

    private SlotList slotList;
    private boolean showFavoritesOnly = false;
    private ButtonWidget allTabButton;
    private ButtonWidget favoritesTabButton;

    private ProtocolSelectionScreen() {
        super("ViaFabricPlusLTS", true);
    }

    @Override
    protected void init() {
        this.setupDefaultSubtitle();

        final ButtonWidget serverList = this.addDrawableChild(ButtonWidget.builder(ServerListScreen.INSTANCE.getTitle(), button -> ServerListScreen.INSTANCE.open(this))
                .position(5, height - 25).size(98, 20).build());
        serverList.active = MinecraftClient.getInstance().getNetworkHandler() == null;

        // Tabs: "All" and "★ Favorites" (offset by 25px to the right to place immediately after the 20px back button)
        final int tabY = 5;
        this.addDrawableChild(this.allTabButton = ButtonWidget.builder(Text.translatable("protocol_selection.viafabricplus.all"), button -> {
            this.showFavoritesOnly = false;
            updateTabButtons();
            if (this.slotList != null) {
                this.slotList.populate(false);
            }
        }).position(30, tabY).size(50, 20).build());

        this.addDrawableChild(this.favoritesTabButton = ButtonWidget.builder(Text.translatable("protocol_selection.viafabricplus.favorites"), button -> {
            this.showFavoritesOnly = true;
            updateTabButtons();
            if (this.slotList != null) {
                this.slotList.populate(true);
            }
        }).position(85, tabY).size(95, 20).build());

        updateTabButtons();

        // List and Settings
        this.addDrawableChild(this.slotList = new SlotList(this, this.client, width, height, 3 + 3 /* start offset */ + (textRenderer.fontHeight + 2) * 3 /* title is 2 */, 30, textRenderer.fontHeight + 4, this.showFavoritesOnly));
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("base.viafabricplus.settings"), button -> SettingsScreen.INSTANCE.open(this)).position(width - 98 - 5, 5).size(98, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("report.viafabricplus.button"), button -> ReportIssuesScreen.INSTANCE.open(this))
                .position(width - 98 - 5, height - 25).size(98, 20).build());

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
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (this.showFavoritesOnly && this.slotList != null && this.slotList.children().isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("protocol_selection.viafabricplus.no_favorites"), width / 2, height / 2, 0xFFAAAAAA);
        }
    }

    public static class SlotList extends VFPList {
        private static double scrollAmount;
        private final ProtocolSelectionScreen screen;

        public SlotList(ProtocolSelectionScreen screen, MinecraftClient minecraftClient, int width, int height, int top, int bottom, int entryHeight, boolean favoritesOnly) {
            super(minecraftClient, width, height, top, bottom, entryHeight);
            this.screen = screen;

            populate(favoritesOnly);
            initScrollY(scrollAmount);
        }

        public void populate(final boolean favoritesOnly) {
            this.clearEntries();
            final var settingsSave = SaveManager.INSTANCE.getSettingsSave();
            ProtocolVersionList.getProtocolsNewToOld().stream()
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
        public Text getNarration() {
            return Text.of(this.protocolVersion.getName());
        }

        @Override
        public void mappedMouseClicked(double mouseX, double mouseY, int button) {
            final var settingsSave = SaveManager.INSTANCE.getSettingsSave();
            if (settingsSave != null && mouseX >= this.x && mouseX <= this.x + 24) {
                settingsSave.toggleFavorite(this.protocolVersion);
                this.screen.onFavoriteToggled(this.protocolVersion);
                return;
            }

            if (MinecraftClient.getInstance().getNetworkHandler() != null) {
                // Setting the target version while connected to a server is not allowed as this will
                // literally break our code away.
                return;
            }

            ProtocolTranslator.setTargetVersion(this.protocolVersion);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            final boolean isSelected = ProtocolTranslator.getTargetVersion().equals(protocolVersion);
            final var settingsSave = SaveManager.INSTANCE.getSettingsSave();
            final boolean isFavorite = settingsSave != null && settingsSave.isFavorite(this.protocolVersion);

            final Matrix3x2fStack matrices = context.getMatrices();

            matrices.pushMatrix();
            matrices.translate(x, y - 1);

            Color color = isSelected ? Color.GREEN : Color.RED;
            if (MinecraftClient.getInstance().getNetworkHandler() != null) {
                color = color.darker();
            }

            final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            context.drawCenteredTextWithShadow(textRenderer, this.protocolVersion.getName(), entryWidth / 2, entryHeight / 2 - textRenderer.fontHeight / 2, color.getRGB());
            final boolean hoveredStar = mouseX >= x && mouseX <= x + 24 && mouseY >= y && mouseY <= y + entryHeight;
            final int starColor = isFavorite ? FAVORITE_COLOR : (hoveredStar ? HOVER_STAR_COLOR : NO_FAVORITE_COLOR);
            context.drawTextWithShadow(textRenderer, STAR, 12, entryHeight / 2 - textRenderer.fontHeight / 2, starColor);

            matrices.popMatrix();
        }
    }

}
