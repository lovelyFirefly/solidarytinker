package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.SlotContext;

public class PoisonProtect extends XICModifier {
    @Override
    public void onCurioTick(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack) {
        if (entity.hasEffect(MobEffects.POISON)) {
            entity.removeEffect(MobEffects.POISON);
        }
    }
}
