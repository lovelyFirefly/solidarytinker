package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class anglefox extends ArmorModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if(amount>entity.getMaxHealth()){
            return Math.max(amount * 0 + entity.getMaxHealth() * 0.2f,4);
        }
        else if(amount>entity.getMaxHealth() * 0.5f){
            return Math.max(amount*0+entity.getMaxHealth() * 0.1f,2);
        }
        return amount;
    }
}
