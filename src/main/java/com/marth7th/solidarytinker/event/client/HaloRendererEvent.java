package com.marth7th.solidarytinker.event.client;

import com.marth7th.solidarytinker.client.Renderer.Halo.HaloClientCache;
import com.marth7th.solidarytinker.client.Renderer.Halo.HaloRegistry;
import com.marth7th.solidarytinker.client.Renderer.Halo.HaloRendererUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID,value = Dist.CLIENT)
public class HaloRendererEvent {
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        PoseStack poseStack = event.getPoseStack();
        float partialTick = event.getPartialTick();
        for (HaloRendererUtil halo : HaloRegistry.getAllHalos()) {
            if (HaloClientCache.getHaloState(player.getUUID(), halo.getModifierId())) {
                halo.render(poseStack, player, partialTick);
            }
        }
    }
}
