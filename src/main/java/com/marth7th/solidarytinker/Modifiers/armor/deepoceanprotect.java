package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class deepoceanprotect extends ArmorModifier {
    public deepoceanprotect() {
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtEvent);
    }

    private void livingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity enemy = (LivingEntity) event.getSource().getEntity();
        if (entity instanceof Player player) {
            if (modifierlevel.getsinglearmorlevel(entity, solidarytinkerModifiers.DEEPOCEANPROTECT_STATIC_MODIFIER.getId()) > 0) {
                float cost = player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f;
                event.setAmount(event.getAmount() * Math.max(1 - cost * 0.01f, 0.1f));
            }
        }
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        entity.setAbsorptionAmount(entity.getMaxHealth() * 0.5f);
        return amount;
    }
}