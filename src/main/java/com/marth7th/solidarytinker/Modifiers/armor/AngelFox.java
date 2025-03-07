package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AngelFox extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    public void LivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            float OriginallyDamage = event.getAmount();
            if (ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
                if (event.getSource().getEntity() instanceof Player player1 && player != player1 && event.getAmount() > event.getEntity().getMaxHealth() * 100) {
                    if (event.getSource().isBypassArmor() || event.getSource().isBypassMagic()) {
                        if (OriginallyDamage > player.getMaxHealth()) {
                            event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.4f, 4));
                        } else if (OriginallyDamage > player.getMaxHealth() * 0.5F) {
                            event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.2f, 2));
                        }
                    } else if (!event.getSource().isBypassMagic() && !event.getSource().isBypassArmor()) {
                        if (OriginallyDamage > player.getMaxHealth()) {
                            event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.2f, 4));
                        } else if (OriginallyDamage > player.getMaxHealth() * 0.5F) {
                            event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.1f, 2));
                        }
                    }
                }
            }
        }
    }
}
