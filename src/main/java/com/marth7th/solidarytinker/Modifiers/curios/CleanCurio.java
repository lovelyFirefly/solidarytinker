package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

import java.util.List;

public class CleanCurio extends XICModifier {
    {
        MinecraftForge.EVENT_BUS.addListener(this::AddMobEffect);
    }

    private void AddMobEffect(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0 && !event.getEffectInstance().getEffect().isBeneficial()) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
