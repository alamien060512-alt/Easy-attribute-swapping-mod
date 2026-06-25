package com.easyattribute.client;

import com.easyattribute.SwapConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private EditBox slotInput;

    public ConfigScreen(Screen parent) {
        super(Component.literal("Easy Attribute Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        slotInput = new EditBox(font, width / 2 - 50, height / 2 - 10, 100, 20,
                Component.literal("Weapon Slot"));
        slotInput.setValue(String.valueOf(SwapConfig.get().weaponSlot));
        addRenderableWidget(slotInput);

        addRenderableWidget(Button.builder(Component.literal("Save"), btn -> {
            try {
                int val = Integer.parseInt(slotInput.getValue().trim());
                if (val >= 0 && val <= 8) {
                    SwapConfig.get().weaponSlot = val;
                    SwapConfig.save();
                }
            } catch (NumberFormatException ignored) {}
            minecraft.gui.setScreen(parent);
        }).bounds(width / 2 - 50, height / 2 + 20, 100, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Cancel"), btn ->
                minecraft.gui.setScreen(parent)
        ).bounds(width / 2 - 50, height / 2 + 45, 100, 20).build());
    }

    @Override
    protected void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(font, "Easy Attribute Config", width / 2, height / 2 - 40, 0xFFFFFF);
        graphics.drawCenteredString(font, "Weapon Slot (0-8):", width / 2, height / 2 - 25, 0xAAAAAA);
    }

    @Override
    public void onClose() {
        minecraft.gui.setScreen(parent);
    }
}
