package com.marth7th.solidarytinker.client.Renderer.Halo;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import javax.annotation.Nullable;

public interface HaloRendererUtil {

    ModifierId getModifierId();

    ResourceLocation getTexture();
    @Nullable
    ResourceLocation getModelLocation();

    boolean checkClientRenderCondition(Player player);

    void render(PoseStack poseStack, Player player, float partialTick);
}
