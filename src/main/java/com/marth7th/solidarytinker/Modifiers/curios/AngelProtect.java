package com.marth7th.solidarytinker.Modifiers.curios;


import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

import java.util.List;

public class AngelProtect extends XICModifier {
    {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::LivingDamageEvent);
    }

    private boolean isBypass(DamageSource source) {
        return source.isBypassArmor() || source.isBypassMagic();
    }

    private float amountShouldCost(Player player, LivingDamageEvent event) {
        if (this.isBypass(event.getSource())) {
            return Math.max(player.getMaxHealth() * 0.3F, 4);
        }
        return Math.max(player.getMaxHealth() * 0.15F, 2);
    }
    private void LivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            float OriginallyDamage = event.getAmount();
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0) {
                    if ((event.getSource().getEntity() instanceof Player player1 && player != player1) || event.getAmount() > player.getMaxHealth() * 100) {
                        return;
                    }
                    float FinalAmount = this.amountShouldCost(player, event);
                    if (OriginallyDamage > player.getMaxHealth()) {
                        event.setAmount(FinalAmount * 2);
                    } else if (OriginallyDamage > player.getMaxHealth() * 0.5F) {
                        event.setAmount(FinalAmount);
                    }
                }
            }
        }
    }
}
