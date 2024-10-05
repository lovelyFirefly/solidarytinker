package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class protonation extends ArmorModifier {
    @Override
    public boolean havenolevel() {return true;}

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        a= modifierlevel.getTotalArmorModifierlevel(entity,solidarytinkerModifiers.PROTONATION_STATIC_MODIFIER.getId());
        return amount;
    }
    public int a;

    @Override
    public void addAttributes(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentSlot equipmentSlot, BiConsumer<Attribute, AttributeModifier> biConsumer) {
        if(a==4) {
            biConsumer.accept(Attributes.ARMOR, new AttributeModifier(UUID.fromString("115d9b72-da0c-4395-bfab-2f2287d6d1eb"), Attributes.ARMOR.getDescriptionId(), 2024, AttributeModifier.Operation.ADDITION));
            biConsumer.accept(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("93bdef84-fac5-4445-90b6-fed434adcdfe"), Attributes.ARMOR_TOUGHNESS.getDescriptionId(), 2024, AttributeModifier.Operation.ADDITION));
        }
    }
}
