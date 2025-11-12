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

public class PeriodicPulsation extends XICModifier {
    @Override
    public void addCurioAttribute(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack, CurioAttributeContext attr) {
        attr.map().put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("b556c9c1-d5cf-4d15-807a-0c241c577f01"), Attributes.MAX_HEALTH.getDescriptionId(), 0.66 * level, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
