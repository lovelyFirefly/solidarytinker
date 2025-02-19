package com.marth7th.solidarytinker.tools.tinkeritem;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.entity.tinkertrident;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class trident extends ModifiableItem {
    public trident(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    public boolean canAttackBlock(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 18000;
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int duration) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack) - duration;
            if (i >= 10) {
                ToolStack tool = ToolStack.from(stack);
                int j = ModifierLevel.getEachHandsTotalModifierlevel(player, solidarytinkerModifiers.RIPTIDE_STATIC_MODIFIER.getId());
                int k = ModifierLevel.getEachHandsTotalModifierlevel(player, solidarytinkerModifiers.CRCS_STATIC_MODIFIER.getId());
                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, player, (player1) -> {
                        player1.broadcastBreakEvent(livingEntity.getUsedItemHand());
                    });
                    if (j == 0 && k == 0 || (j > 0 && !player.isInWater() && !player.level.isRaining()) && k == 0) {
                        tinkertrident throwntrident = new tinkertrident(level, player, stack);
                        throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float) j * 0.5F, 1.0F);
                        float value = SolidarytinkerConfig.TridentDamage.get().floatValue();
                        float baseArrowDamage = tool.getStats().get(ToolStats.ATTACK_DAMAGE) * value;
                        throwntrident.setBaseDamage(ConditionalStatModifierHook.getModifiedStat(tool, player, ToolStats.ATTACK_DAMAGE, baseArrowDamage));
                        ModifierNBT modifiers = tool.getModifiers();
                        throwntrident.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(cap -> cap.setModifiers(modifiers));
                        NamespacedNBT arrowData = PersistentDataCapability.getOrWarn(throwntrident);
                        for (ModifierEntry entry : modifiers.getModifiers()) {
                            entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, livingEntity, throwntrident, throwntrident, arrowData, true);
                        }
                        if (player.getAbilities().instabuild) {
                            throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        level.addFreshEntity(throwntrident);
                        level.playSound(null, throwntrident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                        if (!player.getAbilities().instabuild) {
                            player.getInventory().removeItem(stack);
                        }
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                if (k > 0 || (j > 0 && player.isInWater()) || (j > 0 && player.level.isRaining())) {
                    float f7 = player.getYRot();
                    float f = player.getXRot();
                    float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                    float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                    float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                    float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                    int scala = SolidarytinkerConfig.TridentRipSpeed.get();
                    float f5 = 3.0F * ((1.0F + (float) j * scala) / 4.0F);
                    f1 *= f5 / f4;
                    f2 *= f5 / f4;
                    f3 *= f5 / f4;
                    player.push(f1, f2, f3);
                    player.startAutoSpinAttack(20);
                    IToolStackView stackView = ToolStack.from(player.getMainHandItem());
                    ToolDamageUtil.damageAnimated(stackView, 10, player);
                    if (player.isOnGround()) {
                        float f6 = 1.1999999F;
                        player.move(MoverType.SELF, new Vec3(0.0D, (double) 1.1999999F, 0.0D));
                    }

                    SoundEvent soundevent;
                    if (j >= 3) {
                        soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
                    } else if (j == 2) {
                        soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
                    } else {
                        soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
                    }
                    level.playSound(null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity player) {
        stack.hurtAndBreak(1, player, (player1) -> {
            player1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity entity) {
        if ((double) blockState.getDestroySpeed(level, blockPos) != 0.0D) {
            stack.hurtAndBreak(2, entity, (entity1) -> {
                entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

}
