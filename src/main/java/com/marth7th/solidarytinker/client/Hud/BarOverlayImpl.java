package com.marth7th.solidarytinker.client.Hud;

import com.marth7th.solidarytinker.register.solidarytinkerModifierMekEtsh;
import com.marth7th.solidarytinker.shelf.energy.Cache.ClientEnergyData;
import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class BarOverlayImpl {
    public static final ResourceLocation EnergyBar = new ResourceLocation(solidarytinker.MOD_ID, "textures/hud/energybar.png");
    public static final ResourceLocation EnergyBar_Empty = new ResourceLocation(solidarytinker.MOD_ID, "textures/hud/energybar_empty.png");
    public static final IGuiOverlay EnergyHud = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2 - 91;
        int y = screenHeight - gui.leftHeight + 2;
        if (Minecraft.getInstance().player != null && !Minecraft.getInstance().player.isCreative()) {
            if (ModifierLevel.EquipHasModifierlevel(Minecraft.getInstance().player, solidarytinkerModifierMekEtsh.FLUX_ARMOR_STATIC_MODIFIER.getId())) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
                RenderSystem.setShaderTexture(0, EnergyBar_Empty);
                GuiComponent.blit(poseStack, x + 1, y - 3, 0, 0, 78, 8, 78, 8);
                RenderSystem.setShaderTexture(0, EnergyBar);
                for (int i = 0; i < 19; i++) {
                    int k = ClientEnergyData.getPlayerEnergyLevel();
                    if (k > i) {
                        GuiComponent.blit(poseStack, x + 4 + (i * 4), y - 3, 0, 0, 4, 8, 4, 8);
                    } else {
                        break;
                    }
                }
            }
        }
    });
}
