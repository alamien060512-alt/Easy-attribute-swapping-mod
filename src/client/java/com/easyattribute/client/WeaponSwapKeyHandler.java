package com.easyattribute.client;

import com.easyattribute.SwapConfig;
import com.easyattribute.client.mixin.InventoryAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class WeaponSwapKeyHandler {
    public static KeyMapping swapKey;

    private static int restoreSlot = -1;

    public static void register() {
        swapKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.weaponswap.swap",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (restoreSlot != -1 && client.player != null) {
                ((InventoryAccessor) client.player.getInventory()).setSelected(restoreSlot);
                restoreSlot = -1;
            }

            while (swapKey.consumeClick()) {
                handle(client);
            }
        });
    }

    private static void handle(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null || client.level == null) return;

        int original = ((InventoryAccessor) player.getInventory()).getSelected();
        int target = SwapConfig.get().targetSlot();

        if (target != original) {
            ((InventoryAccessor) player.getInventory()).setSelected(target);
        }

        client.gameMode.attack(player, player);
        player.swing(InteractionHand.MAIN_HAND);

        if (target != original) {
            restoreSlot = original;
        }
    }
}
