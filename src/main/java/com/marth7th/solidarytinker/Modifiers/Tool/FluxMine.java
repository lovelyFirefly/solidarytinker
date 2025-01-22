package com.marth7th.solidarytinker.Modifiers.Tool;

import com.marth7th.solidarytinker.extend.superclass.FluxBattleModifier;
import com.marth7th.solidarytinker.shelf.energy.FluxStorage;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class FluxMine extends FluxBattleModifier {
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        if (FluxStorage.getEnergyStored(tool) > 100 * amount) {
            FluxStorage.removeEnergy(tool, 100 * amount, false, false);
            return 0;
        }
        return amount;
    }
}
