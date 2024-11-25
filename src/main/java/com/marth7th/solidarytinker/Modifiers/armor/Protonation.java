package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class Protonation extends ArmorModifier {
    @Override
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void addAttributes(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentSlot equipmentSlot, BiConsumer<Attribute, AttributeModifier> biConsumer) {
        if (modifierEntry.getLevel() > 0) {
            biConsumer.accept(Attributes.ARMOR, new AttributeModifier(UUID.fromString("115d9b72-da0c-4397-bfab-2f2287d6d1eb"), Attributes.ARMOR.getDescriptionId(), 2024, AttributeModifier.Operation.ADDITION));
            biConsumer.accept(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("93bdef84-fac8-4445-90b6-fed434adcdfe"), Attributes.ARMOR_TOUGHNESS.getDescriptionId(), 2024, AttributeModifier.Operation.ADDITION));
        }
    }
}
