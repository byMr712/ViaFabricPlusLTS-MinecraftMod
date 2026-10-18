package com.viaversion.viafabricplus.injection.mixin.fixes.authlib;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = YggdrasilServicesKeyInfo.class, remap = false)
public interface MixinYggdrasilServicesKeyInfo {

    @Inject(method = "validateProperty", at = @At("HEAD"), cancellable = true)
    default void onValidateProperty(Property property, CallbackInfoReturnable<Boolean> cir) {
        if (property.signature() == null || property.signature().isEmpty()) {
            cir.setReturnValue(false);
        }
    }
}
