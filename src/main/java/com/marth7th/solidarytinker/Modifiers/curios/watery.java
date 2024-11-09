package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.SlotContext;

public class watery extends XICModifier {
    @Override
    public void onCurioTick(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack) {
        if (entity instanceof Player player) {
            var equips = new ItemStack[]{player.getItemBySlot(EquipmentSlot.HEAD), player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS), player.getItemBySlot(EquipmentSlot.FEET), player.getItemBySlot(EquipmentSlot.OFFHAND), player.getItemBySlot(EquipmentSlot.MAINHAND)};
            for (ItemStack itemStack1 : equips) {
                if (player.getCooldowns().getCooldownPercent(itemStack1.getItem(), 0) > 0) {
                    player.getCooldowns().addCooldown(itemStack1.getItem(), -1);
                }
            }
        }
    }
}
