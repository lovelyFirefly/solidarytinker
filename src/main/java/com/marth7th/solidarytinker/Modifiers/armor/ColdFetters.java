package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ColdFetters extends ArmorModifier {

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getSource().getEntity() != null && ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), this.getId()) > 0) {
            int level = ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), this.getId());
            if(event.getSource().getEntity()instanceof LivingEntity enemy){
                if(enemy.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                    event.setAmount(event.getAmount() * (1-0.1f*level));
                }
            }
        } else if (ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), this.getId()) > 0) {
            event.setAmount(event.getAmount() * 1.1f);
        }
    }
}
