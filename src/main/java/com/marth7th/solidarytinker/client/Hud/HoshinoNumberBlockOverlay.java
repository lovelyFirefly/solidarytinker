package com.marth7th.solidarytinker.client.Hud;

import com.marth7th.solidarytinker.shelf.energy.Cache.BlockAmountData;
import com.marth7th.solidarytinker.shelf.energy.Cache.ClientEnergyData;
import com.marth7th.solidarytinker.solidarytinker;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class HoshinoNumberBlockOverlay {
    public static final ResourceLocation GemIcon_LeftHalf = new ResourceLocation(solidarytinker.MOD_ID, "textures/hud/gem_icon_left.png");
    public static final ResourceLocation GemIcon_RightHalf = new ResourceLocation(solidarytinker.MOD_ID, "textures/hud/gem_icon_right.png");

    private static final float[][] COLOR_LEVELS = new float[][] {
            {0.431F, 0.803F, 1.00F, 1.0F},
            {1.00F, 0.65F, 0.22F, 1.0F},
            {1.00F, 1.00F, 0.00F, 1.0F},
            {1.00F, 0.66F, 1.00F, 1.0F},
            {0.00F, 0.00F, 1.00F, 1.0F},
            {0.50F, 0.00F, 1.00F, 1.0F}
    };

    public static final IGuiOverlay GemHealthHud = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.player.isCreative()) {
            return;
        }
        Font font = minecraft.font;

        int currentTotalAmount = BlockAmountData.getPlayerBlockNumber();
        if(currentTotalAmount==0)return;
        int currentLevel = currentTotalAmount / 20;
        int levelsToDraw = currentLevel + 1;
        int x = screenWidth / 2 - 91;
        int y = screenHeight - gui.leftHeight + 2;
        if(ClientEnergyData.getPlayerEnergyLevel()>0){
            y=y-10;
        }
        String levelString = String.valueOf(currentTotalAmount);
        int textX = x - font.width(levelString) - 2;
        int textY = y + (8 / 2) - (font.lineHeight / 2);
        poseStack.pushPose();
        poseStack.translate(textX, textY, 0);
        font.drawShadow(poseStack, levelString, 0, 0, 0xDFAAFF);
        poseStack.popPose();

        RenderSystem.setShaderTexture(0, GemIcon_LeftHalf);
        for (int level = 0; level < levelsToDraw; level++) {
            int colorIndex = Math.min(level, COLOR_LEVELS.length - 1);
            float[] color = COLOR_LEVELS[colorIndex];
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
            int healthInThisLevel;
            int startPoint = level * 20;
            int endPoint = Math.min((level + 1) * 20, currentTotalAmount);
            healthInThisLevel = endPoint - startPoint;
            for (int i = 0; i < 10; i++) {
                int iconX = x + (i * 8);
                int requiredHealthLeft = (i * 2);
                int requiredHealthRight = (i * 2) + 1;
                if (healthInThisLevel > requiredHealthLeft) {
                    RenderSystem.setShaderTexture(0, GemIcon_LeftHalf);
                    GuiComponent.blit(poseStack, iconX, y, 0, 0, 8, 8, 8, 8);
                }
                if (healthInThisLevel > requiredHealthRight) {
                    RenderSystem.setShaderTexture(0, GemIcon_RightHalf);
                    GuiComponent.blit(poseStack, iconX, y, 0, 0, 8, 8, 8, 8);
                }
            }
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    });
}
