package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class angelprotect extends XICModifier {
    @Override
    public void onCurioTakeHurt(IToolStackView curio, LivingHurtEvent event, LivingEntity entity, DamageSource source, int level) {
        event.setAmount(1);
    }
}
