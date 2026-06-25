package com.easyattribute.client.mixin;

import com.easyattribute.client.WeaponSwapKeyHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class ExampleClientMixin {
    @Inject(method = "swapItemWithOffhand", at = @At("HEAD"), cancellable = true)
    private void cancelOffhandSwap(CallbackInfo ci) {
        if (WeaponSwapKeyHandler.swapKey != null && WeaponSwapKeyHandler.swapKey.isDown()) {
            ci.cancel();
        }
    }
}
