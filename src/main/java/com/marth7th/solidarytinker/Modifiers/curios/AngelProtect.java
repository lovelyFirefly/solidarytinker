package com.marth7th.solidarytinker.Modifiers.curios;


import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

import java.util.List;

public class AngelProtect extends XICModifier {
    {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
    }

    private void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0) {
                    if (event.getAmount() > player.getMaxHealth() * 0.5F) {
                        event.setAmount(player.getMaxHealth() * 0.3f);
                    }
                }
            }
        }
    }
}