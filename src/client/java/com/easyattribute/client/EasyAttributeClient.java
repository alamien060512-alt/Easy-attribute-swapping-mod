package com.easyattribute.client;

import net.fabricmc.api.ClientModInitializer;

public class EasyAttributeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WeaponSwapKeyHandler.register();
    }
}
