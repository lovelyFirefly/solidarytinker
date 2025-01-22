package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SeaBless extends BattleModifier {
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

    @Override
    public void LivingAttackEvent(LivingAttackEvent event) {
        if (ModifierLevel.getEachHandsTotalModifierlevel(event.getEntity(), solidarytinkerModifiers.CRCS_STATIC_MODIFIER.getId()) > 0) {
            if (event.getSource() == DamageSource.FALL) {
                event.getEntity().invulnerableTime = 80;
                event.setCanceled(true);
            }
        }
    }
}
