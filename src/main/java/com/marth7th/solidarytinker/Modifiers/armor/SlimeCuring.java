package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

public class SlimeCuring extends ArmorModifier {
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        OverslimeModifier.OVERSLIME_STAT.add(builder, 300 * modifier.getLevel());
    }

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
                IToolStackView head = ToolStack.from(player.getItemBySlot(EquipmentSlot.HEAD));
                IToolStackView chest = ToolStack.from(player.getItemBySlot(EquipmentSlot.CHEST));
                IToolStackView legs = ToolStack.from(player.getItemBySlot(EquipmentSlot.LEGS));
                IToolStackView feet = ToolStack.from(player.getItemBySlot(EquipmentSlot.FEET));
                float totalSlime = TinkerModifiers.overslime.get().getShield(head) + TinkerModifiers.overslime.get().getShield(chest) + TinkerModifiers.overslime.get().getShield(legs) + TinkerModifiers.overslime.get().getShield(feet);
                if (event.getSource().isExplosion() || event.getSource().getEntity() instanceof Mob) {
                    event.setAmount(Math.max(1 - (totalSlime / 50F * 0.01F), 0.6F));
                }
                var armor = new ItemStack[]{player.getItemBySlot(EquipmentSlot.HEAD), player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS), player.getItemBySlot(EquipmentSlot.FEET)};
                for (ItemStack armors : armor) {
                    IToolStackView toolview = ToolStack.from(armors);
                    ModDataNBT tooldata = toolview.getPersistentData();
                    OverslimeModifier overslimeModifier = TinkerModifiers.overslime.get();
                    int shield = overslimeModifier.getShield(toolview);
                    float maxshield = toolview.getStats().get(OverslimeModifier.OVERSLIME_STAT);
                    if (TinkerModifiers.overslime.get().getShield(toolview) > toolview.getStats().get(OverslimeModifier.OVERSLIME_STAT) * 0.25F) {
                        TinkerModifiers.overslime.get().setShield(tooldata, (int) (shield - maxshield * 0.25F));
                        event.setAmount(event.getAmount() * 0.1f);
                        break;
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (ModifierLevel.EquipHasModifierlevel(player, this.getId())) {
                IToolStackView head = ToolStack.from(player.getItemBySlot(EquipmentSlot.HEAD));
                IToolStackView chest = ToolStack.from(player.getItemBySlot(EquipmentSlot.CHEST));
                IToolStackView legs = ToolStack.from(player.getItemBySlot(EquipmentSlot.LEGS));
                IToolStackView feet = ToolStack.from(player.getItemBySlot(EquipmentSlot.FEET));
                float totalSlime = TinkerModifiers.overslime.get().getShield(head) + TinkerModifiers.overslime.get().getShield(chest) + TinkerModifiers.overslime.get().getShield(legs) + TinkerModifiers.overslime.get().getShield(feet);
                event.setAmount(Math.max(1 + (totalSlime / 50F * 0.01F), 1.4F));
            }
        }
    }
}
