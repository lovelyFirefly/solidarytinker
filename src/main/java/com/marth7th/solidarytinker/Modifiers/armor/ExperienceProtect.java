package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ExperienceProtect extends ArmorModifier {
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            float bypassArmorBlock = SolidarytinkerConfig.ExperienceSteelArmorBlockBypassArmor.get().floatValue();
            float Block = SolidarytinkerConfig.ExperienceSteelArmorBlock.get().floatValue();
            LivingEntity entity = event.getEntity();
            if (ModifierLevel.getTotalArmorModifierlevel(entity, this.getId()) > 0) {
                if (entity instanceof Player player) {
                    int a = player.experienceLevel;
                    int level = ModifierLevel.getTotalArmorModifierlevel(player, this.getId());
                    if (a > 0) {
                        if (!event.getSource().isBypassMagic() || !event.getSource().isBypassArmor()) {
                            event.setAmount(Math.max(event.getAmount() - Math.min(a * Block * level, 200), 0));
                        } else
                            event.setAmount(Math.max(event.getAmount() - Math.min(a * bypassArmorBlock * level, 200), 0));
                    }
                }
            }
        }
    }
}
