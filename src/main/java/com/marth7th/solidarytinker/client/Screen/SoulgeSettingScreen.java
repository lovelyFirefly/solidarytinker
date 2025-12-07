package com.marth7th.solidarytinker.client.Screen;

import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.shelf.Network.Packet.SoulgeConfigPacket;
import com.marth7th.solidarytinker.shelf.Network.STChannel;
import com.marth7th.solidarytinker.util.method.SoulgeHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

@OnlyIn(Dist.CLIENT)
public class SoulgeSettingScreen extends Screen {
    private boolean isSingleMode;
    private int extraBurnScale;
    private IToolStackView tool;
    public SoulgeSettingScreen(IToolStackView tool) {
        super(Component.literal("魂戈配置界面"));
        this.tool=tool;
        this.isSingleMode = SoulgeHelper.isSingleMode(tool);
        this.extraBurnScale = SoulgeHelper.getExtraBurnScale(tool);
    }

    private Component getButtonText() {
        MutableComponent component = Component.literal("当前模式: ");
        if (this.isSingleMode) {
            return component.append(Component.literal(" 单头 ").withStyle(style -> style.withColor(0xff8c40)));
        } else {
            return component.append(Component.literal(" 多头 ").withStyle(style -> style.withColor(0x7398ff)));
        }
    }
    private Component getSliderText() {
        return Component.literal("当前燃烧倍率: " + Math.max(this.extraBurnScale,1)).withStyle(style -> style.withColor(0xffaaff));
    }

    @Override
    protected void init() {
        super.init();
        if(tool==null)return;
        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(new Button(
                centerX - (buttonWidth / 2),
                centerY - 45,
                buttonWidth,
                buttonHeight,
                this.getButtonText(),
                (button) -> {
                    this.isSingleMode = !this.isSingleMode;
                    button.setMessage(this.getButtonText());
                    sendConfigToServer();
                }
        ));

        if(tool.getModifierLevel(solidarytinkerModifiers.OVERLOAD_BURN_STATIC_MODIFIER.getId())>0){
            this.addRenderableWidget(new AbstractSliderButton(
                    centerX - (buttonWidth / 2),
                    centerY,
                    buttonWidth,
                    buttonHeight,
                    getSliderText(),
                    (double) (this.extraBurnScale - 1) / (25 - 1)
            ){
                @Override
                protected void applyValue() {
                    SoulgeSettingScreen.this.extraBurnScale = 1 + (int) Math.round(this.value * (25 - 1));
                    sendConfigToServer();
                }
                @Override
                protected void updateMessage() {
                    this.setMessage(getSliderText());
                }
            });
        }
        this.addRenderableWidget(new Button(
                centerX - (buttonWidth / 2),
                centerY + 45,
                buttonWidth,
                buttonHeight,
                Component.translatable("tooltip.soulge.configscreen.close"),
                (button) -> this.onClose()
        ));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }
    private void sendConfigToServer() {
        int clampedBurnScale = Mth.clamp(this.extraBurnScale, 1, 25);
        STChannel.SendToServer(new SoulgeConfigPacket(isSingleMode,clampedBurnScale));
    }
}
