package com.easyattribute.client;

import com.easyattribute.SwapConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class WeaponSwapKeyHandler {
    public static KeyMapping swapKey;

    // pending restore: -1 means nothing to restore
    private static int restoreSlot = -1;

    public static void register() {
        swapKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.weaponswap.swap",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "category.weaponswap"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // restore slot from previous tick first
            if (restoreSlot != -1 && client.player != null) {
                client.player.getInventory().selected = restoreSlot;
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

        int original = player.getInventory().selected;
        int target = SwapConfig.get().targetSlot();

        // swap to weapon slot
        if (target != original) {
            player.getInventory().selected = target;
        }

        // attack with weapon slot active
        client.gameMode.attack(player, player);
        player.swing(InteractionHand.MAIN_HAND);

        // queue restore back to original next tick
        if (target != original) {
            restoreSlot = original;
        }
    }
}
