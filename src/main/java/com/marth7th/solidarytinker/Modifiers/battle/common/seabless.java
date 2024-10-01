package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class seabless extends BattleModifier {
    @Override
    public boolean havenolevel() {
        return true;
    }

    @Override
    public boolean hidden() {
        return true;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        if (entity != null && (entity.isInWater() || entity.level.isRaining())) {
            return (int) (amount * 0.5f);
        }
        return amount;
    }
}
