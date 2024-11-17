package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;

public class Clean extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if(event.getEntity()!=null&&event.getEntity()instanceof LivingEntity ){
            if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), solidarytinkerModifiers.CLEAN_STATIC_MODIFIER.getId())) {
                if (!event.getEffectInstance().getEffect().isBeneficial())
                    event.setResult(Event.Result.DENY);
            }
        }
    }
}
