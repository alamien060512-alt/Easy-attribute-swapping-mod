package com.easyattribute;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.*;

public class SwapConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance()
            .getConfigDir().resolve("easy-attribute.json");

    // hotbar slot (0-8) to swap to when F is pressed
    public int weaponSlot = 1;

    private static SwapConfig instance;

    public static SwapConfig get() {
        if (instance == null) load();
        return instance;
    }

    public static void load() {
        if (Files.exists(CONFIG_FILE)) {
            try (Reader r = Files.newBufferedReader(CONFIG_FILE)) {
                instance = GSON.fromJson(r, SwapConfig.class);
            } catch (IOException e) {
                instance = new SwapConfig();
            }
        } else {
            instance = new SwapConfig();
            save();
        }
    }

    public static void save() {
        try (Writer w = Files.newBufferedWriter(CONFIG_FILE)) {
            GSON.toJson(instance, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int targetSlot() {
        return Math.max(0, Math.min(8, weaponSlot));
    }
}
