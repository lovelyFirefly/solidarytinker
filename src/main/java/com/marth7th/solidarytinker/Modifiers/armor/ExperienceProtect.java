package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ExperienceProtect extends ArmorModifier {
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            if (ModifierLevel.getTotalArmorModifierlevel(entity, this.getId()) > 0) {
                if (entity instanceof Player player) {
                    int a = player.experienceLevel;
                    int level = ModifierLevel.getTotalArmorModifierlevel(player, this.getId());
                    if (a > 0) {
                        if (!event.getSource().isBypassMagic() || !event.getSource().isBypassArmor()) {
                            event.setAmount(event.getAmount() - a * 0.04f * level);
                        } else event.setAmount(event.getAmount() - a * 0.08f * level);
                    }
                }
            }
        }
    }
}
