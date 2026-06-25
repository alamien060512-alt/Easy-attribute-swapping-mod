package com.easyattribute.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class EasyAttributeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WeaponSwapKeyHandler.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (WeaponSwapKeyHandler.configKey.consumeClick()) {
                client.gui.setScreen(new ConfigScreen(client.gui.screen()));
            }
        });
    }
}
