package com.marth7th.solidarytinker.Modifiers.Tool;


import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SoulGe extends BattleModifier implements GeneralInteractionModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder builder) {
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }
    public @NotNull InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource == InteractionSource.RIGHT_CLICK) {
            GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }
    private final Random rand = new Random();

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 1000;
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        solidarytinkerToolstats.DETECTION_RANGE.add(builder,10);
    }

    @Override
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        if(entity instanceof Player player){
            var level=player.getLevel();
            float dist= tool.getStats().get(ToolStats.ATTACK_DAMAGE);
            Vec3 playerEyePosition = player.getEyePosition(0F);
            Vec3 playerLook = player.getViewVector(0F);
            Vec3 Vector3d2 = playerEyePosition.add(playerLook.x * dist, playerLook.y * dist, playerLook.z * dist);
            List<Mob> targetedMob=new ArrayList<>();
            Entity pointedEntity = null;
            var x=player.getX();
            var y=player.getY();
            var z=player.getZ();
            List<Entity> nearbyEntities = level.getEntities(player, player.getBoundingBox().expandTowards(playerLook.x * dist, playerLook.y * dist, playerLook.z * dist).inflate(1.0F, 1.0F, 1.0F));
            List<Mob> mobList = player.level.getEntitiesOfClass(Mob.class, new AABB(x + dist, y + dist, z + dist, x - dist, y - dist, z - dist));
            for(Mob mob:mobList){
                if(targetedMob.size()<11){
                    targetedMob.add(mob);
                }
            }
            double d2 = dist;
            for(Entity nearbyEntity : nearbyEntities) {
                AABB axisAlignedBB = nearbyEntity.getBoundingBox().inflate(nearbyEntity.getPickRadius());
                Optional<Vec3> optional = axisAlignedBB.clip(playerEyePosition, Vector3d2);
                if (axisAlignedBB.contains(playerEyePosition)) {
                    if (d2 >= (double)0.0F) {
                        pointedEntity = nearbyEntity;
                        d2 = 0.0F;
                    }
                } else if (optional.isPresent()) {
                    double d3 = playerEyePosition.distanceTo(optional.get());
                    if (d3 < d2 || d2 == (double)0.0F) {
                        if (nearbyEntity.getRootVehicle() == player.getRootVehicle() && !player.canRiderInteract()) {
                            if (d2 == (double)0.0F) {
                                pointedEntity = nearbyEntity;
                            }
                        } else {
                            pointedEntity = nearbyEntity;
                            d2 = d3;
                        }
                    }
                }
            }
            if (pointedEntity instanceof LivingEntity) {
                if (!pointedEntity.isAlive()) {
                    return;
                }
                pointedEntity.getPersistentData().putInt("targeted",40);
            }
            this.attackTargets(player,tool,targetedMob);
        }

    }
    @Override
    public @NotNull UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.BOW;
    }
    private void drawParticleBeam(LivingEntity origin, LivingEntity target) {
        double d0 = target.getX() - origin.getX();
        double d1 = target.getY() + (double) (target.getBbHeight() * 0.5F)
                - (origin.getY() + (double) origin.getEyeHeight() * 0.5D);
        double d2 = target.getZ() - origin.getZ();
        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 = d0 / d3;
        d1 = d1 / d3;
        d2 = d2 / d3;
        double d4 = this.rand.nextDouble();
        while (d4 < d3) {
            d4 += 1.0D;
            origin.level.addParticle(ParticleTypes.SOUL, origin.getX() + d0 * d4, origin.getY() + d1 * d4 + (double) origin.getEyeHeight() * 0.5D, origin.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
        }
    }
    private void attackTargets(Player player,IToolStackView view,List<Mob> moblist) {
        for(Mob mob:moblist) {
            var persistentData= mob.getPersistentData();
            var targetedTimes=persistentData.getInt("targeted");
            if(targetedTimes>0){
                ToolAttackUtil.attackEntity(view,player, mob);
                persistentData.putInt("targeted",targetedTimes-1);
                if(mob.getHealth() < mob.getMaxHealth() * 0.14F&& mob.isAlive()){
                    mob.setDeltaMovement(new Vec3(0,2.5,0));
                    persistentData.putInt("ready_to_die",5);
                }
                this.drawParticleBeam(player, mob);
                if (!mob.isAlive()) {
                    persistentData.remove("targeted");
                }
            }
        }
    }
}
