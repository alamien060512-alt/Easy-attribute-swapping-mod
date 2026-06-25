package com.easyattribute.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class EasyAttributeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WeaponSwapKeyHandler.register();

        // open config screen with config key (default: none, user assigns in controls)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (WeaponSwapKeyHandler.configKey.consumeClick()) {
                client.setScreen(new ConfigScreen(client.screen));
            }
        });
    }
}
