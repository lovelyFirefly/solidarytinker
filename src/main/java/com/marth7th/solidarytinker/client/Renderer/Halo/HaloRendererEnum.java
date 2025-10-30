package com.marth7th.solidarytinker.client.Renderer.Halo;




import com.marth7th.solidarytinker.client.Renderer.HaloRenderLogic;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;

import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import java.util.function.Supplier;

public enum HaloRendererEnum implements HaloRendererUtil {
    HOSHINO(solidarytinkerModifiers.ABSOLUTEJUSTICE_STATIC_MODIFIER, "hoshino"),
    AZUSA(solidarytinkerModifiers.azusaHaloStaticModifier, "azusa"),
    REISA(solidarytinkerModifiers.reisaHaloStaticModifier, "reisa"),
    NATSU(solidarytinkerModifiers.natsuHaloStaticModifier, "natsu"),
    AL1S(solidarytinkerModifiers.al1sHaloStaticModifier, "al1s"),
    MARI(solidarytinkerModifiers.mariHaloStaticModifier, "mari");
    private final Supplier<Modifier> modifierSupplier;
    private final ResourceLocation texture;
    private Modifier cachedModifier;

    HaloRendererEnum(Supplier<Modifier> modifierSupplier, String textureName) {
        this.modifierSupplier = modifierSupplier;
        this.texture = solidarytinker.getResource("textures/halo/" + textureName + ".png");
    }

    public Modifier getModifier() {
        if (cachedModifier == null) {
            cachedModifier = modifierSupplier.get();
        }
        return cachedModifier;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public @Nullable ResourceLocation getModelLocation() {
        return null;
    }

    @Override
    public boolean checkClientRenderCondition(Player player) {
        return ModifierLevel.getAllSlotModifierlevel(player, this.getModifierId()) > 0;
    }

    @Override
    public void render(PoseStack poseStack, Player player, float partialTick) {
        HaloRenderLogic.renderCompleteDynamicHaloHorizontal(poseStack, player, partialTick, this.texture);
    }

    @Override
    public ModifierId getModifierId() {
        return getModifier().getId();
    }
}
