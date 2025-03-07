package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnderInhibit extends ArmorModifier {
    @Override
    public void PlayerLivingHurt(LivingHurtEvent event, LivingEntity enemy, Player player) {
        if (enemy instanceof EnderMan enderMan && ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
            int a = ModifierLevel.getTotalArmorModifierlevel(player, this.getId());
            enderMan.hurt(DamageSource.playerAttack(player).bypassArmor().bypassMagic(), enderMan.getMaxHealth() * 0.1F * a);
            event.setAmount(event.getAmount() * Math.max(0.1F, 1 - 0.3F * a));
        }
    }
}
