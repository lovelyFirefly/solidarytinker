package com.marth7th.solidarytinker.Modifiers.battle.technology;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;

import java.util.List;

public class DarkStar extends BattleModifier {
    private static final ResourceLocation DEATH = solidarytinker.getResource("death");
    @Override
    public @Nullable Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(DEATH);
        return null;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        ModDataNBT tooldata = ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getPersistentData();
        if (target != null && target.isInWater()) {
            arrow.setBaseDamage((arrow.getBaseDamage() + tooldata.getFloat(DEATH)) * 2f);
        } else arrow.setBaseDamage(arrow.getBaseDamage() + tooldata.getFloat(DEATH));
    }

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        ModDataNBT tooldata = ToolStack.from(attacker.getItemBySlot(EquipmentSlot.MAINHAND)).getPersistentData();
        if (livingTarget.isInWater()) {
            return (damage + tooldata.getFloat(DEATH)) * 2f;
        } else return damage + tooldata.getFloat(DEATH);
    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT tooldata = iToolStackView.getPersistentData();
            float death = tooldata.getFloat(DEATH);
            list.add(Component.literal("已提升伤害：" + death).withStyle(ChatFormatting.GRAY));
        }
    }
}
