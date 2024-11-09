package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.content.library.context.CurioAttributeContext;
import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class deepoceanchew extends XICModifier {

    @Override
    public void addCurioAttribute(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack, CurioAttributeContext attr) {
        attr.map().put(Attributes.LUCK, new AttributeModifier(UUID.fromString("3367bbc2-913d-4b6b-9229-6cc3ff6f4bc6"), Attributes.LUCK.getDescriptionId(), 2, AttributeModifier.Operation.MULTIPLY_BASE));
        attr.map().put(Attributes.LUCK, new AttributeModifier(UUID.fromString("3367bbc2-913d-4b6b-9229-6cc3ff6f4bc6"), Attributes.ATTACK_SPEED.getDescriptionId(), 0.4, AttributeModifier.Operation.MULTIPLY_BASE));
    }
}
