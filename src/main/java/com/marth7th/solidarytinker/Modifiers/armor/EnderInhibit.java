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
    public void LivingHurt(LivingHurtEvent event, LivingEntity entity, Player player) {
        if (entity instanceof EnderMan enderMan && ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
            enderMan.hurt(DamageSource.playerAttack(player), enderMan.getMaxHealth() * 0.5F);
            event.setAmount(event.getAmount() * 0.3f);
        }
    }
}
