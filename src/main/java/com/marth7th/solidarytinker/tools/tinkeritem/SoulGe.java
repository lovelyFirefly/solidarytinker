package com.marth7th.solidarytinker.tools.tinkeritem;


import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import com.marth7th.solidarytinker.shelf.Network.Packet.SoulGeAttackPacket;
import com.marth7th.solidarytinker.shelf.Network.STChannel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.*;

public class SoulGe extends ModifiableItem {
    public SoulGe(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    private final Random rand = new Random();

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
        ItemStack itemstack = playerIn.getItemInHand(hand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public @NotNull List<Component> getStatInformation(@NotNull IToolStackView tool, @Nullable Player player, @NotNull List<Component> tooltips, @NotNull TooltipKey key, @NotNull TooltipFlag tooltipFlag) {
        TooltipBuilder builder = new TooltipBuilder(tool, tooltips);
        if (tool.hasTag(TinkerTags.Items.DURABILITY)) {
            builder.addDurability();
        }
        if (tool.hasTag(TinkerTags.Items.MELEE)) {
            builder.add(ToolStats.ATTACK_DAMAGE);
            builder.add(ToolStats.ATTACK_SPEED);
        }
        builder.add(Component.translatable("tool_stat.solidarytinker.detection_range").withStyle(style -> style.withColor(solidarytinkerToolstats.DETECTION_RANGE.getColor()))
                .append(":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.DETECTION_RANGE).intValue())).withStyle(style -> style.withColor(solidarytinkerToolstats.DETECTION_RANGE.getColor())));
        builder.add(Component.translatable("tool_stat.solidarytinker.exert_times").withStyle(style -> style.withColor(solidarytinkerToolstats.EXERT_TIMES.getColor()))
                .append(":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.EXERT_TIMES).intValue())).withStyle(style -> style.withColor(solidarytinkerToolstats.EXERT_TIMES.getColor())));
        builder.add(Component.translatable("tool_stat.solidarytinker.attack_frequency").withStyle(style -> style.withColor(solidarytinkerToolstats.ATTACK_FREQUENCY.getColor()))
                .append(":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.ATTACK_FREQUENCY).intValue())).withStyle(style -> style.withColor(solidarytinkerToolstats.ATTACK_FREQUENCY.getColor())));
        builder.add(Component.translatable("tool_stat.solidarytinker.kill_threshold").withStyle(style -> style.withColor(solidarytinkerToolstats.KILLTHRESHOLD.getColor()))
                .append(":" + String.format("%s", String.format("%d", Math.round(tool.getStats().get(solidarytinkerToolstats.KILLTHRESHOLD) * 100)) + "%")).withStyle(style -> style.withColor(solidarytinkerToolstats.KILLTHRESHOLD.getColor())));
        builder.addAllFreeSlots();
        for (ModifierEntry entry : tool.getModifierList()) {
            entry.getHook(ModifierHooks.TOOLTIP).addTooltip(tool, entry, player, tooltips, key, tooltipFlag);
        }
        return tooltips;
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity attacker, @NotNull ItemStack stack, int timeLeft) {
        IToolStackView tool = ToolStack.from(stack);
        var level = attacker.getLevel();
        int dist = Math.round(tool.getStats().get(solidarytinkerToolstats.DETECTION_RANGE));
        int attackFrequency = Math.round(tool.getStats().get(solidarytinkerToolstats.ATTACK_FREQUENCY));
        int exertTimes = Math.round(tool.getStats().get(solidarytinkerToolstats.EXERT_TIMES));
        var pointedEntity = this.getPointedEntity(attacker, level, dist);
        if (pointedEntity != null && pointedEntity.isAlive()) {
            var targetedTimes = pointedEntity.getPersistentData().getInt("targeted");
            if (targetedTimes < exertTimes * 3) {
                pointedEntity.getPersistentData().putInt("targeted", Math.min(exertTimes * 3, exertTimes + targetedTimes));
            }

        }
        if (attacker.tickCount % attackFrequency == 0) {
            this.attackTargets(attacker, tool, dist,exertTimes);
        }
    }

    public LivingEntity getPointedEntity(LivingEntity attacker, Level level, int dist) {
        Vec3 playerEyePosition = attacker.getEyePosition(1F);
        Vec3 playerLook = attacker.getViewVector(1F);
        Vec3 Vector3d2 = playerEyePosition.add(playerLook.x * dist, playerLook.y * dist, playerLook.z * dist);
        LivingEntity pointedEntity = null;
        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().expandTowards(playerLook.x * dist, playerLook.y * dist, playerLook.z * dist).inflate(1.0F, 1.0F, 1.0F));
        double d2 = dist;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            AABB axisAlignedBB = nearbyEntity.getBoundingBox().inflate(nearbyEntity.getPickRadius());
            Optional<Vec3> optional = axisAlignedBB.clip(playerEyePosition, Vector3d2);
            if (axisAlignedBB.contains(playerEyePosition)) {
                if (d2 >= (double) 0.0F) {
                    pointedEntity = nearbyEntity;
                    d2 = 0.0F;
                }
            } else if (optional.isPresent()) {
                double d3 = playerEyePosition.distanceTo(optional.get());
                if (d3 < d2 || d2 == (double) 0.0F) {
                    if (nearbyEntity.getRootVehicle() == attacker.getRootVehicle() && !attacker.canRiderInteract()) {
                        if (d2 == (double) 0.0F) {
                            pointedEntity = nearbyEntity;
                        }
                    } else {
                        pointedEntity = nearbyEntity;
                    }
                    return pointedEntity;
                }
            }
        }
        return null;
    }

    private void drawParticleBeam(LivingEntity player, LivingEntity target) {
        double d0 = target.getX() - player.getX();
        double d1 = target.getY() + (double) (target.getBbHeight() * 0.5F)
                - (player.getY() + (double) player.getEyeHeight() * 0.5D);
        double d2 = target.getZ() - player.getZ();
        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 = d0 / d3;
        d1 = d1 / d3;
        d2 = d2 / d3;
        double d4 = this.rand.nextDouble();
        while (d4 < d3) {
            d4 += 1.0D;
            player.level.addParticle(ParticleTypes.SOUL, player.getX() + d0 * d4, player.getY() + d1 * d4 + (double) player.getEyeHeight() * 0.5D, player.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
        }
    }

    private void attackTargets(LivingEntity livingEntity, IToolStackView view, int dist,int exertTimes) {
        float killThreshold = view.getStats().get(solidarytinkerToolstats.KILLTHRESHOLD);
        var x = livingEntity.getX();
        var y = livingEntity.getY();
        var z = livingEntity.getZ();
        List<Mob> targetedMob = new ArrayList<>();
        List<Mob> mobList = livingEntity.level.getEntitiesOfClass(Mob.class, new AABB(x + dist, y + dist, z + dist, x - dist, y - dist, z - dist));
        for (Mob mob : mobList) {
            if (targetedMob.size() < 11&&mob.getPersistentData().contains("targeted")) {
                targetedMob.add(mob);
            }
        }
        for (Mob mob : mobList) {
            var persistentData = mob.getPersistentData();
            var targetedTimes = persistentData.getInt("targeted");
            if (targetedTimes > 0 && livingEntity instanceof Player player && !persistentData.contains("ready_to_die")) {
                ToolAttackUtil.attackEntity(view, player, mob);
                ToolDamageUtil.damageAnimated(view, 1, player);
                if(mob.getLevel().isClientSide()){
                    var clientTargetedTimes = mob.getPersistentData().getInt("targeted");
                    var uuid = mob.getUUID();
                    STChannel.SendToServer(new SoulGeAttackPacket(uuid, clientTargetedTimes));
                }
                persistentData.putInt("targeted", targetedTimes - 1);
                if (mob.getHealth() < mob.getMaxHealth() * killThreshold && mob.isAlive() && !persistentData.contains("ready_to_die")) {
                    mob.getActiveEffects().removeAll(mob.getActiveEffects());
                    mob.setDeltaMovement(new Vec3(0, 2.5, 0));
                    persistentData.putInt("ready_to_die", 5);
                }
                this.drawParticleBeam(player,mob);
                if (!mob.isAlive()) {
                    persistentData.remove("targeted");
                }
            }
        }
    }
}
