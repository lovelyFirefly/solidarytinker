package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class collapse extends ArmorModifier {
    public collapse() {
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtEvent);
    }

    private void livingHurtEvent(LivingHurtEvent event) {
        Entity enemy=  event.getSource().getEntity();
        LivingEntity entity=event.getEntity();
        if (modifierlevel.getsinglearmorlevel(entity, solidarytinkerModifiers.COLLAPSE_STATIC_MODIFIER.getId())>0){
          if(entity!=null){
            event.setAmount(event.getAmount() * 0.5f);
           }
          if(enemy!=null){
              enemy.setRemainingFireTicks(2147483647);
              if(enemy.isOnFire()){
                  event.setAmount(event.getAmount() * 0.2f);
              }
            }
        }
    }
}
