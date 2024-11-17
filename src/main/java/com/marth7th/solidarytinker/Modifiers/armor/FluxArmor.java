package com.marth7th.solidarytinker.Modifiers.armor;


import com.marth7th.solidarytinker.extend.energy.FluxStorage;
import com.marth7th.solidarytinker.extend.superclass.FluxArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class FluxArmor extends FluxArmorModifier {

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ModifierLevel.EachaArmorHasModifierlevel(player, this.getId())) {
                IToolStackView helmet = ToolStack.from(player.getItemBySlot(EquipmentSlot.HEAD));
                IToolStackView chest = ToolStack.from(player.getItemBySlot(EquipmentSlot.CHEST));
                IToolStackView legs = ToolStack.from(player.getItemBySlot(EquipmentSlot.LEGS));
                IToolStackView feet = ToolStack.from(player.getItemBySlot(EquipmentSlot.FEET));
                int amount = (int) event.getAmount();
                boolean canprotect = FluxStorage.getEnergyStored(helmet) >= 200 * amount && FluxStorage.getEnergyStored(chest) >= 200 * amount && FluxStorage.getEnergyStored(legs) >= 200 * amount && FluxStorage.getEnergyStored(feet) >= 200 * amount;
                if (canprotect) {
                    event.getEntity().invulnerableTime = 30;
                    FluxStorage.removeEnergy(helmet, 200 * amount, false, false);
                    FluxStorage.removeEnergy(chest, 200 * amount, false, false);
                    FluxStorage.removeEnergy(legs, 200 * amount, false, false);
                    FluxStorage.removeEnergy(feet, 200 * amount, false, false);
                    event.setAmount(amount * 0.075f);
                } else {
                    event.setAmount(event.getAmount() * 0.5f);
                    FluxStorage.removeEnergy(helmet, 200, false, true);
                    FluxStorage.removeEnergy(chest, 200, false, true);
                    FluxStorage.removeEnergy(legs, 200, false, true);
                    FluxStorage.removeEnergy(feet, 200, false, true);
                }
            }
        }
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifierEntry, int amount, @Nullable LivingEntity livingEntity) {
        if (FluxStorage.getEnergyStored(tool) > 400 * amount) {
            FluxStorage.removeEnergy(tool, 400 * amount, false, false);
            return 0;
        } else FluxStorage.removeEnergy(tool, FluxStorage.getEnergyStored(tool), false, true);
        return amount;
    }
}
