package de.florianmichael.viafabricplus.test;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.viafabricplus.protocoltranslator.ProtocolTranslator;
import de.florianmichael.viafabricplus.settings.impl.BedrockSettings;
import de.florianmichael.viafabricplus.settings.impl.GeneralSettings;
import de.florianmichael.viafabricplus.settings.impl.Mr712Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViaFabricPlusLogicTest {

    @Test
    public void testProtocolTargetSwitching() {
        ProtocolVersion initial = ProtocolTranslator.getTargetVersion();
        assertNotNull(initial, "Initial target version should not be null");

        // Switch to 1.12.2
        ProtocolTranslator.setTargetVersion(ProtocolVersion.v1_12_2);
        assertEquals(ProtocolVersion.v1_12_2, ProtocolTranslator.getTargetVersion());

        // Switch to 1.8
        ProtocolTranslator.setTargetVersion(ProtocolVersion.v1_8);
        assertEquals(ProtocolVersion.v1_8, ProtocolTranslator.getTargetVersion());

        // Switch to 1.20.2
        ProtocolTranslator.setTargetVersion(ProtocolVersion.v1_20_2);
        assertEquals(ProtocolVersion.v1_20_2, ProtocolTranslator.getTargetVersion());

        // Test revertOnDisconnect parameter logic
        ProtocolTranslator.setTargetVersion(ProtocolVersion.v1_7_6, true);
        assertEquals(ProtocolVersion.v1_7_6, ProtocolTranslator.getTargetVersion());

        // Restore initial
        ProtocolTranslator.setTargetVersion(initial);
        assertEquals(initial, ProtocolTranslator.getTargetVersion());
    }

    @Test
    public void testSettingsStateAndDefaults() {
        Mr712Settings mr712 = Mr712Settings.global();
        assertNotNull(mr712, "Mr712Settings global instance should not be null");
        assertTrue(mr712.fixDoubleTranslation.getValue(), "fixDoubleTranslation should be true by default");
        assertEquals(0, mr712.serverWarmupMode.getIndex(), "Default warmup mode should be 0");

        GeneralSettings general = GeneralSettings.global();
        assertNotNull(general, "GeneralSettings global instance should not be null");

        BedrockSettings bedrock = BedrockSettings.global();
        assertNotNull(bedrock, "BedrockSettings global instance should not be null");
    }
}
