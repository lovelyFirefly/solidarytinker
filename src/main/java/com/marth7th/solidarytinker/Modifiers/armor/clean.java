package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;

public class clean extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    public clean() {
        MinecraftForge.EVENT_BUS.addListener(this::RemoveEffectEvent);
    }

    private void RemoveEffectEvent(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        if (modifierlevel.getsinglearmorlevel(entity, solidarytinkerModifiers.CLEAN_STATIC_MODIFIER.getId()) > 0) {
            if (!event.getEffectInstance().getEffect().isBeneficial())
                event.setResult(Event.Result.DENY);
        }
    }
}
