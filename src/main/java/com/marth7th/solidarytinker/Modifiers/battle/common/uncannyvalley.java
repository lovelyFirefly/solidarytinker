package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class uncannyvalley extends BattleModifier {
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof Player player) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            List<Mob> mobbbb = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 40, y + 40, z + 40, x - 40, y - 40, z - 40));
            List<Player> plaaaaaayer = player.level.getEntitiesOfClass(Player.class, new AABB(x + 40, y + 40, z + 40, x - 40, y - 40, z - 40));
            for (Mob targets : mobbbb) {
                if (entity.hasLineOfSight(targets)) {
                    targets.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 10));
                    targets.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3));
                }
            }
            for (Player targets : plaaaaaayer) {
                if (entity.hasLineOfSight(targets)){
                    targets.addEffect(new MobEffectInstance(MobEffects.SATURATION,10,1));
                    targets.addEffect(new MobEffectInstance(MobEffects.REGENERATION,200,1));
                }
            }
        }
    }
}
