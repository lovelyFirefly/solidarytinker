package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Collapse extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            if (ModifierLevel.getTotalArmorModifierlevel(entity, solidarytinkerModifiers.COLLAPSE_STATIC_MODIFIER.getId()) > 0) {
                Entity enemy = event.getSource().getEntity();
                if (entity != null) {
                    event.setAmount(event.getAmount() * 0.5f);
                }
                if (enemy != null) {
                    enemy.setRemainingFireTicks(2147483647);
                    if (enemy.isOnFire()) {
                        event.setAmount(event.getAmount() * 0.2f);
                    }
                }
            }
        }
    }
}
