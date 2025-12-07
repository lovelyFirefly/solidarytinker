package com.marth7th.solidarytinker.client.Hud;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class HoshinoStagnationHUD {
    @Getter
    @Setter
    public static int remainingStagnationTime;
    @Getter
    @Setter
    public static int StagnationWaitTime1;
    @Getter
    @Setter
    public static int StagnationWaitTime2;
    @Getter
    @Setter
    public static int StagnationWaitTime3;
    @Getter
    @Setter
    public static int StagnationWaitTime4;
    public static final IGuiOverlay STAGNATION_WAIT_HUD = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        var localPlayer=minecraft.player;
        if (localPlayer == null || localPlayer.isCreative()){
            return;
        }
        poseStack.pushPose();
        int x = 10;
        int yBottom = screenHeight - 60;
        int remainingStagnationTime = getRemainingStagnationTime();
        if(remainingStagnationTime>0){
            int minutes = remainingStagnationTime / 60;
            int seconds = remainingStagnationTime % 60;
            String remainFormattedTime = String.format("%02d:%02d",minutes, seconds);
            String remainFormatText = "凝滞持续时长: " + remainFormattedTime;
            fontRenderer.drawShadow(poseStack, remainFormatText, x+20, yBottom, 0xf0b0ff);
        }

        if(StagnationWaitTime4>0){
            yBottom-=15;
            drawStagnationCD(poseStack,minecraft,localPlayer.getItemBySlot(EquipmentSlot.FEET),x,yBottom,StagnationWaitTime4);
        }
        if(StagnationWaitTime3>0){
            yBottom-=15;
            drawStagnationCD(poseStack,minecraft,localPlayer.getItemBySlot(EquipmentSlot.LEGS),x,yBottom,StagnationWaitTime3);
        }
        if(StagnationWaitTime2>0){
            yBottom-=15;
            drawStagnationCD(poseStack,minecraft,localPlayer.getItemBySlot(EquipmentSlot.CHEST),x,yBottom,StagnationWaitTime2);
        }
        if(StagnationWaitTime1>0){
            yBottom-=15;
            drawStagnationCD(poseStack,minecraft,localPlayer.getItemBySlot(EquipmentSlot.HEAD),x,yBottom,StagnationWaitTime1);
        }
        poseStack.popPose();
    });
    public static void drawStagnationCD(PoseStack poseStack, Minecraft minecraft, ItemStack stack, int x, int y, int cd){
        Font fontRenderer = minecraft.font;
        int minutes = cd / 60;
        int seconds = cd % 60;
        String WaitFormattedTime = String.format("%02d:%02d", minutes, seconds);
        String WaitFormatText = "凝滞冷却时间: " + WaitFormattedTime;
        fontRenderer.drawShadow(poseStack, WaitFormatText, x+20, y, 0xffa98c);
        minecraft.getItemRenderer().renderAndDecorateItem(stack, x, y-5, 0);
    }
}
