package com.viaversion.viafabricplus.injection.mixin.fixes.authlib;

import com.mojang.authlib.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Property.class, remap = false)
public abstract class MixinProperty {

    @Shadow
    public abstract String signature();

    /**
     * @author byMr712
     * @reason Prevent Bad signature length exception on empty signature string
     */
    @Overwrite
    public boolean hasSignature() {
        return this.signature() != null && !this.signature().isEmpty();
    }
}
