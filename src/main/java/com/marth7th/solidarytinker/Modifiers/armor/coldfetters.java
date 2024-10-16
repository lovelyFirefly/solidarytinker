package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class coldfetters extends ArmorModifier {

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if(event.getSource().getEntity()!=null&& modifierlevel.getTotalArmorModifierlevel(event.getEntity(),this.getId())>0){
            int level=modifierlevel.getTotalArmorModifierlevel(event.getEntity(),this.getId());
            if(event.getSource().getEntity()instanceof LivingEntity enemy){
                if(enemy.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                    event.setAmount(event.getAmount() * (1-0.1f*level));
                }
            }
        }
        else if(modifierlevel.getTotalArmorModifierlevel(event.getEntity(),this.getId())>0){
            event.setAmount(event.getAmount() * 1.1f);
        }
    }
}
