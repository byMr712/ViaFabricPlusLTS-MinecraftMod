package com.viaversion.viafabricplus.test;

import com.viaversion.viafabricplus.protocoltranslator.ProtocolTranslator;
import com.viaversion.viafabricplus.screen.impl.ProtocolSelectionScreen;
import com.viaversion.viafabricplus.screen.impl.SettingsScreen;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.TestInput;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;

/**
 * Client GameTest for ViaFabricPlus exercising screen interaction and target protocol selection
 * both on the title screen and inside a live singleplayer world.
 */
public class ViaFabricPlusClientGameTest implements FabricClientGameTest {

    @Override
    public void runTest(ClientGameTestContext ctx) {
        TestInput input = ctx.getInput();
        input.resizeWindow(1600, 900);

        ctx.runOnClient(mc -> {
            mc.options.guiScale().set(3);
            mc.resizeGui();
        });
        ctx.waitTicks(20);

        // -------------------------------------------------------------
        // Phase 1: Title Screen / Protocol Selection Screen Check
        // -------------------------------------------------------------
        System.out.println("[ViaFabricPlusClientGameTest] Testing Title Screen integration...");

        final ProtocolVersion initialVersion = ProtocolTranslator.getTargetVersion();
        if (initialVersion == null) {
            throw new AssertionError("ViaFabricPlus initial target version is null!");
        }

        // Open ViaFabricPlus Protocol Screen
        ctx.runOnClient(mc -> {
            ProtocolSelectionScreen.INSTANCE.open(mc.screen);
        });
        ctx.waitTicks(15);
        ctx.takeScreenshot("01_title_screen_vfp");

        ctx.runOnClient(mc -> {
            if (!(mc.screen instanceof ProtocolSelectionScreen)) {
                throw new AssertionError("Expected ProtocolSelectionScreen on Title Screen, got: " + mc.screen);
            }
            // Test switching target version
            ProtocolTranslator.setTargetVersion(ProtocolVersion.v1_12_2);
            if (ProtocolTranslator.getTargetVersion() != ProtocolVersion.v1_12_2) {
                throw new AssertionError("Failed to switch target version to 1.12.2!");
            }
            // Restore initial target version
            ProtocolTranslator.setTargetVersion(initialVersion);

            // Close screen
            mc.screen.onClose();
        });
        ctx.waitTicks(10);

        // -------------------------------------------------------------
        // Phase 2: In-Game Singleplayer Live Workflow
        // -------------------------------------------------------------
        System.out.println("[ViaFabricPlusClientGameTest] Testing In-Game Singleplayer integration...");
        try (TestSingleplayerContext sp = ctx.worldBuilder()
                .setUseConsistentSettings(false)
                .adjustSettings(s -> s.setAllowCommands(true))
                .create()) {

            sp.getServer().runCommand("time set day");
            sp.getServer().runCommand("difficulty peaceful");
            ctx.waitTicks(30);

            // Open ViaFabricPlus Settings Screen in-game
            ctx.runOnClient(mc -> {
                SettingsScreen.INSTANCE.open(mc.screen);
            });
            ctx.waitTicks(15);
            ctx.takeScreenshot("02_ingame_vfp_settings");

            ctx.runOnClient(mc -> {
                if (mc.screen == null) {
                    throw new AssertionError("Expected Settings Screen to be open in-game!");
                }
                mc.screen.onClose();
            });
            ctx.waitTicks(10);
            ctx.takeScreenshot("03_ingame_world");
        }

        System.out.println("[ViaFabricPlusClientGameTest] Complete client gametest workflow PASSED!");
    }
}
