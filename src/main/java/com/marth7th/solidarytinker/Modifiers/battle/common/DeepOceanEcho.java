package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.compound.IceFantasy;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.List;

public class DeepOceanEcho extends BattleModifier {
    public float MaxValue() {
        return SolidarytinkerConfig.iceMaxValue.get().floatValue();
    }
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (attacker instanceof Player player) {
            float a = (Math.max(player.getMaxHealth() * 0.2f, 1) * Math.max(player.getArmorValue() * 0.6f, 1) * Math.max(player.totalExperience * 0.001f, 1)) * 0.5f * level;
            if (livingTarget instanceof Player) {
                return damage * 0f;
            } else return damage + Math.min((a * 0.5f * level), MaxValue());
        }
        return damage;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (attacker instanceof Player player) {
            float a = (Math.max(player.getMaxHealth() * 0.2f, 1) * Math.max(player.getArmorValue() * 0.6f, 1) * Math.max(player.totalExperience * 0.001f, 1)) * 0.5f * level;
            if (target instanceof Player) {
                arrow.setBaseDamage(0);
            } else arrow.setBaseDamage(Math.min((arrow.getBaseDamage() + (a * 0.5 * level)), MaxValue()));
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            int level = modifier.getLevel();
            float a = (Math.max(player.getMaxHealth() * 0.2f, 1) * Math.max(player.getArmorValue() * 0.6f, 1) * Math.max(player.totalExperience * 0.001f, 1)) * 0.5f * level;
            list.add(applyStyle(Component.literal(IceFantasy.GetColor("当前回声点数")).append(IceFantasy.GetColor(a + ""))));
            list.add(applyStyle(Component.literal(IceFantasy.GetColor("每点回声所增幅的伤害")).append(IceFantasy.GetColor(level * 0.5f + "攻击力"))));
            list.add(applyStyle(Component.literal(IceFantasy.GetColor("实际提升的总伤害")).append(IceFantasy.GetColor(Math.min((level * 0.5f) * a, MaxValue()) + "攻击力"))));
            list.add(applyStyle(Component.literal(IceFantasy.GetColor("你已是完全之龙，足以审判众神")).append(IceFantasy.GetColor("你已经掌握" + level + "层权能"))));
        }
    }
}
