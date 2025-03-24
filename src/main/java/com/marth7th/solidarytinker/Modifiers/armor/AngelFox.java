package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AngelFox extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    private boolean isBypass(DamageSource source) {
        return source.isBypassArmor() || source.isBypassMagic();
    }

    private float amountShouldCost(Player player, LivingDamageEvent event) {
        if (this.isBypass(event.getSource())) {
            return Math.max(player.getMaxHealth() * 0.3F, 4);
        }
        return Math.max(player.getMaxHealth() * 0.15F, 2);
    }

    public void LivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            float OriginallyDamage = event.getAmount();
            if (ModifierLevel.getTotalArmorModifierlevel(player, this.getId()) > 0) {
                if ((event.getSource().getEntity() instanceof Player player1 && player != player1) || event.getAmount() > player.getMaxHealth() * 100) {
                    return;
                }
                float FinalAmount = this.amountShouldCost(player, event);
                if (OriginallyDamage > player.getMaxHealth()) {
                    event.setAmount(FinalAmount * 2);
                } else if (OriginallyDamage > player.getMaxHealth() * 0.5F) {
                    event.setAmount(FinalAmount);
                }
            }
        }
    }
}
