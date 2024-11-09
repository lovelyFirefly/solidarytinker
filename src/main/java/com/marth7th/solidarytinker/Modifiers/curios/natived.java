package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.content.library.context.CurioAttributeContext;
import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class natived extends XICModifier {
    @Override
    public int onCurioGetFortune(IToolStackView curio, SlotContext slotContext, LootContext lootContext, ItemStack stack, int fortune, int level) {
        return fortune + level * 2;
    }

    @Override
    public void addCurioAttribute(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack, CurioAttributeContext attr) {
        attr.map().put(Attributes.LUCK, new AttributeModifier(UUID.fromString("3367bbc2-547b-4b6b-9229-6cc3ff6f4bc6"), Attributes.LUCK.getDescriptionId(), 2 * level, AttributeModifier.Operation.ADDITION));
        attr.map().put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3367bbc2-542d-4b6b-9229-6cc3ff6f4bc6"), Attributes.ATTACK_SPEED.getDescriptionId(), 0.266 * level, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public void onCurioTakeHurt(IToolStackView curio, LivingHurtEvent event, LivingEntity entity, DamageSource source, int level) {
        if (event.getAmount() > entity.getMaxHealth() * 0.5f) {
            event.setAmount(entity.getMaxHealth() * 0.3f);
        }
    }
}
