package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ArmorCoating extends ArmorModifier {
    @Override
    public void LivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            int PlayerModifierLevel = ModifierLevel.getAllSlotModifierlevel(player, this.getId());
            if (PlayerModifierLevel > 0) {
                float BlockAmount = Math.min(PlayerModifierLevel * 0.1F * player.getArmorValue(), player.getMaxHealth() * 0.2F);
                event.setAmount(Math.max(event.getAmount() - BlockAmount, 0));
            }
        }
    }
}
