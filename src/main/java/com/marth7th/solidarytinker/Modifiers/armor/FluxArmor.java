package com.marth7th.solidarytinker.Modifiers.armor;


import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.extend.superclass.FluxArmorModifier;
import com.marth7th.solidarytinker.shelf.Network.Packet.EnergyChangePacket;
import com.marth7th.solidarytinker.shelf.Network.STChannel;
import com.marth7th.solidarytinker.shelf.energy.FluxStorage;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class FluxArmor extends FluxArmorModifier {

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            float protect = SolidarytinkerConfig.FluxArmorBlock.get().floatValue();
            int cost = SolidarytinkerConfig.FluxArmorCost.get();
            if (ModifierLevel.EachaArmorHasModifierlevel(player, this.getId())) {
                IToolStackView helmet = ToolStack.from(player.getItemBySlot(EquipmentSlot.HEAD));
                IToolStackView chest = ToolStack.from(player.getItemBySlot(EquipmentSlot.CHEST));
                IToolStackView legs = ToolStack.from(player.getItemBySlot(EquipmentSlot.LEGS));
                IToolStackView feet = ToolStack.from(player.getItemBySlot(EquipmentSlot.FEET));
                int amount = (int) event.getAmount();
                int shouldremove=amount * cost /4;
                boolean canprotect = FluxStorage.getEnergyStored(helmet) >= shouldremove && FluxStorage.getEnergyStored(chest) >= shouldremove && FluxStorage.getEnergyStored(legs) >= shouldremove&& FluxStorage.getEnergyStored(feet) >= shouldremove;
                if (canprotect) {
                    FluxStorage.removeEnergy(helmet, shouldremove, false, false);
                    FluxStorage.removeEnergy(chest, shouldremove, false, false);
                    FluxStorage.removeEnergy(legs, shouldremove, false, false);
                    FluxStorage.removeEnergy(feet, shouldremove, false, false);
                    event.setAmount(amount * (1 - protect));
                } else {
                    event.setAmount(event.getAmount() * 0.5f);
                    FluxStorage.removeEnergy(helmet, cost, false, true);
                    FluxStorage.removeEnergy(chest, cost, false, true);
                    FluxStorage.removeEnergy(legs, cost, false, true);
                    FluxStorage.removeEnergy(feet, cost, false, true);
                }
            }
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.tickCount % 2 == 0) {
                IToolStackView helmet = ToolStack.from(serverPlayer.getItemBySlot(EquipmentSlot.HEAD));
                IToolStackView chest = ToolStack.from(serverPlayer.getItemBySlot(EquipmentSlot.CHEST));
                IToolStackView legs = ToolStack.from(serverPlayer.getItemBySlot(EquipmentSlot.LEGS));
                IToolStackView feet = ToolStack.from(serverPlayer.getItemBySlot(EquipmentSlot.FEET));
                float nowEnergy = FluxStorage.getEnergyStored(helmet) + FluxStorage.getEnergyStored(chest) + FluxStorage.getEnergyStored(legs) + FluxStorage.getEnergyStored(feet);
                float TotalEnergy = FluxStorage.getMaxEnergyStored(helmet) + FluxStorage.getMaxEnergyStored(chest) + FluxStorage.getMaxEnergyStored(legs) + FluxStorage.getMaxEnergyStored(feet);
                int EnergyLevel = Math.round(nowEnergy / TotalEnergy * 18F);
                STChannel.SendToPlayer(new EnergyChangePacket(EnergyLevel), serverPlayer);
            }
        }
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifierEntry, int amount, @Nullable LivingEntity livingEntity) {
        if (FluxStorage.getEnergyStored(tool) > 200 * amount) {
            return 0;
        }
        return amount;
    }
}
