package com.marth7th.solidarytinker.Modifiers.curios;


import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils;
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
    private void LivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            float OriginallyDamage = event.getAmount();
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0) {
                    if (event.getSource().getEntity() instanceof Player player1 && player != player1 && event.getAmount() > event.getEntity().getMaxHealth() * 100) {
                        if (event.getSource().isBypassArmor() || event.getSource().isBypassMagic()) {
                            if (OriginallyDamage > player.getMaxHealth()) {
                                event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.4f, 4));
                            } else if (OriginallyDamage > player.getMaxHealth() * 0.5F) {
                                event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.2f, 2));
                            }
                        } else if (!event.getSource().isBypassMagic() && !event.getSource().isBypassArmor()) {
                            if (OriginallyDamage > player.getMaxHealth()) {
                                event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.2f, 4));
                            } else if (OriginallyDamage > player.getMaxHealth() * 0.5F) {
                                event.setAmount(Math.max(OriginallyDamage * 0 + player.getMaxHealth() * 0.1f, 2));
                            }
                        }
                    }
                }
            }
        }
    }
}
