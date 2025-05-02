package com.marth7th.solidarytinker.event.client;

import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.client.Handler.ClientAzusaHaloHandler;
import com.marth7th.solidarytinker.client.Handler.ClientHoshinoHaloHandler;
import com.marth7th.solidarytinker.client.Handler.ClientReisaHaloHandler;
import com.marth7th.solidarytinker.client.Renderer.HaloRenderLogic;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID,value = Dist.CLIENT)
public class HaloRendererEvent {
    private static final ResourceLocation HoshinoHalo= solidarytinker.getResource("textures/halo/hoshino.png");
    private static final ResourceLocation ReisaHalo= solidarytinker.getResource("textures/halo/reisa.png");
    private static final ResourceLocation AzusaHalo= solidarytinker.getResource("textures/halo/azusa.png");
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.CLIENT) {
            Player player=event.player;
            ClientHoshinoHaloHandler.setHasHalo(ModifierUtil.getModifierLevel(player.getItemBySlot(EquipmentSlot.HEAD), solidarytinkerModifiers.hoshinoHaloStaticModifier.getId()) > 0);
            ClientAzusaHaloHandler.setHasHalo(ModifierUtil.getModifierLevel(player.getItemBySlot(EquipmentSlot.HEAD), solidarytinkerModifiers.azusaHaloStaticModifier.getId()) > 0);
            ClientReisaHaloHandler.setHasHalo(ModifierUtil.getModifierLevel(player.getItemBySlot(EquipmentSlot.HEAD), solidarytinkerModifiers.reisaHaloStaticModifier.getId()) > 0);
        }
    }
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (ClientHoshinoHaloHandler.shouldRenderHalo(player)) {
            HaloRenderLogic.renderCompleteDynamicHaloHorizontal(event.getPoseStack(), player, event.getPartialTick(),HoshinoHalo);
        }
        if (ClientAzusaHaloHandler.shouldRenderHalo(player)) {
            HaloRenderLogic.renderCompleteDynamicHaloHorizontal(event.getPoseStack(), player, event.getPartialTick(),AzusaHalo);
        }
        if (ClientReisaHaloHandler.shouldRenderHalo(player)) {
            HaloRenderLogic.renderCompleteDynamicHaloHorizontal(event.getPoseStack(), player, event.getPartialTick(),ReisaHalo);
        }
    }
}
