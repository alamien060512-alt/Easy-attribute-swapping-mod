package com.easyattribute;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyAttribute implements ModInitializer {
    public static final String MOD_ID = "easy-attribute";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        SwapConfig.load();
        LOGGER.info("Easy Attribute loaded — slots: {}", SwapConfig.get().swapSlots);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
