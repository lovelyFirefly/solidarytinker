package com.marth7th.solidarytinker.event.client;

import com.marth7th.solidarytinker.client.KeyBinding;
import com.marth7th.solidarytinker.register.solidarytinkerModifierMekEtsh;
import com.marth7th.solidarytinker.shelf.Network.Packet.EnergyChangePacket;
import com.marth7th.solidarytinker.shelf.Network.Packet.MekaKeyBoardPacket;
import com.marth7th.solidarytinker.shelf.Network.STChannel;
import com.marth7th.solidarytinker.shelf.energy.FluxStorage;
import com.marth7th.solidarytinker.tools.tinkeritem.MekaTool;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;


@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    static void clientSetupEvent(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.getMainHandItem().getItem() instanceof MekaTool mekaTool) {
                if (KeyBinding.DIGGING_SPEED_KEY.consumeClick()) {
                    if (mekaTool.getToolLevel() == 0) {
                        STChannel.SendToServer(new MekaKeyBoardPacket(0));
                        player.sendSystemMessage(Component.literal("切换到中速模式"));
                    } else if (mekaTool.getToolLevel() == 1) {
                        STChannel.SendToServer(new MekaKeyBoardPacket(1));
                        player.sendSystemMessage(Component.literal("切换到高速模式"));
                    } else if (mekaTool.getToolLevel() == 2) {
                        STChannel.SendToServer(new MekaKeyBoardPacket(2));
                        player.sendSystemMessage(Component.literal("切换到极速模式"));
                        player.sendSystemMessage(Component.literal("此模式可能产生幽灵方块"));
                    } else if (mekaTool.getToolLevel() == 3) {
                        STChannel.SendToServer(new MekaKeyBoardPacket(3));
                        player.sendSystemMessage(Component.literal("切换到低速模式"));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinIn(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (ModifierLevel.EachaArmorHasModifierlevel(player, solidarytinkerModifierMekEtsh.FLUX_ARMOR_STATIC_MODIFIER.getId())) {
                    IToolStackView helmet = ToolStack.from(player.getItemBySlot(EquipmentSlot.HEAD));
                    IToolStackView chest = ToolStack.from(player.getItemBySlot(EquipmentSlot.CHEST));
                    IToolStackView legs = ToolStack.from(player.getItemBySlot(EquipmentSlot.LEGS));
                    IToolStackView feet = ToolStack.from(player.getItemBySlot(EquipmentSlot.FEET));
                    float nowEnergy = FluxStorage.getEnergyStored(helmet) + FluxStorage.getEnergyStored(chest) + FluxStorage.getEnergyStored(legs) + FluxStorage.getEnergyStored(feet);
                    float TotalEnergy = FluxStorage.getMaxEnergyStored(helmet) + FluxStorage.getMaxEnergyStored(chest) + FluxStorage.getMaxEnergyStored(legs) + FluxStorage.getMaxEnergyStored(feet);
                    int EnergyLevel = Math.round(nowEnergy / TotalEnergy * 18F);
                    STChannel.SendToPlayer(new EnergyChangePacket(EnergyLevel), player);
                }
            }
        }
    }
}
