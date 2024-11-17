package com.marth7th.solidarytinker.Modifiers.armor;


import com.marth7th.solidarytinker.extend.energy.FluxStorage;
import com.marth7th.solidarytinker.extend.superclass.FluxArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

public class FluxArmor extends FluxArmorModifier {

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ModifierLevel.EachaArmorHasModifierlevel(player, this.getId())) {
                List<ItemStack> armors = player.getInventory().armor;
                for (ItemStack armor : armors) {
                    IToolStackView toolstack = ToolStack.from(armor);
                    int amount = (int) event.getAmount();
                    if (FluxStorage.getEnergyStored(toolstack) >= 200) {
                        event.getEntity().invulnerableTime = 80;
                        FluxStorage.removeEnergy(toolstack, 200 * amount, false, false);
                        event.setAmount(event.getAmount() * 0.95f);
                    } else if (FluxStorage.getEnergyStored(toolstack) < 200) {
                        FluxStorage.removeEnergy(toolstack, FluxStorage.getEnergyStored(toolstack), false, true);
                    }
                }
            }
        }
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifierEntry, int amount, @Nullable LivingEntity livingEntity) {
        if (FluxStorage.getEnergyStored(tool) > 1000 * amount) {
            FluxStorage.removeEnergy(tool, 1000 * amount, false, false);
            return 0;
        } else FluxStorage.removeEnergy(tool, FluxStorage.getEnergyStored(tool), false, true);
        return amount;
    }
}
