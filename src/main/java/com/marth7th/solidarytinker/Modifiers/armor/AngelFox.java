package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AngelFox extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    {
        MinecraftForge.EVENT_BUS.addListener(this::LivingDamageEvent);
    }

    private void LivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() != null) {
            if (ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), this.getId()) > 0) {
                LivingEntity entity = event.getEntity();
                float OriginallyDamage = event.getAmount();
                if (event.getSource().isBypassArmor() || event.getSource().isBypassMagic()) {
                    if (OriginallyDamage > entity.getMaxHealth()) {
                        event.setAmount(Math.max(OriginallyDamage * 0 + entity.getMaxHealth() * 0.4f, 4));
                    } else if (OriginallyDamage > entity.getMaxHealth() * 0.5F) {
                        event.setAmount(Math.max(OriginallyDamage * 0 + entity.getMaxHealth() * 0.2f, 2));
                    }
                } else if (!event.getSource().isBypassMagic() && !event.getSource().isBypassArmor()) {
                    if (OriginallyDamage > entity.getMaxHealth()) {
                        event.setAmount(Math.max(OriginallyDamage * 0 + entity.getMaxHealth() * 0.2f, 4));
                    } else if (OriginallyDamage > entity.getMaxHealth() * 0.5F) {
                        event.setAmount(Math.max(OriginallyDamage * 0 + entity.getMaxHealth() * 0.1f, 2));
                    }
                }
            }
        }
    }
}
