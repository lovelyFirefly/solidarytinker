package com.marth7th.solidarytinker.tools.tinkeritem;

import com.marth7th.solidarytinker.register.SolidarytinkerSound;
import com.marth7th.solidarytinker.register.TinkerCuriosModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import com.marth7th.solidarytinker.shelf.energy.FluxStorage;
import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.compound.DynamicComponentUtil;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElectricBatons extends ModifiableItem {

    public ElectricBatons(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    private static ResourceLocation COOLING = solidarytinker.getResource("electric_baton_cooling");

    @Override
    public @NotNull List<Component> getStatInformation(@NotNull IToolStackView tool, @Nullable Player player, @NotNull List<Component> tooltips, @NotNull TooltipKey key, @NotNull TooltipFlag tooltipFlag) {
        int[] color = new int[]{0x092afd, 0xe3eefd};
        TooltipBuilder builder = new TooltipBuilder(tool, tooltips);
        if (tool.hasTag(TinkerTags.Items.DURABILITY)) {
            builder.addDurability();
        }
        if (tool.hasTag(TinkerTags.Items.MELEE)) {
            builder.add(ToolStats.ATTACK_DAMAGE);
            builder.add(ToolStats.ATTACK_SPEED);
        }
        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "连锁间距",
                ":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.DETECTION_RANGE).intValue()) + "格", color, 20, 20, false));
        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "连锁数量",
                ":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.TARGET_AMOUNT).intValue()) + "位", color, 20, 20, false));
        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "当前能量存储",
                ":" + getEnergyStorage(tool) + "/" + getMaxEnergyStorage(tool), color, 20, 20, false));
        builder.addAllFreeSlots();
        for (ModifierEntry entry : tool.getModifierList()) {
            entry.getHook(ModifierHooks.TOOLTIP).addTooltip(tool, entry, player, tooltips, key, tooltipFlag);
        }
        return tooltips;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        var random = new Random();

        if (!(target instanceof Mob mob) || !(playerIn instanceof ServerPlayer player)) return InteractionResult.PASS;
        if (mob instanceof TamableAnimal) return InteractionResult.PASS;
        if (mob instanceof Villager) return InteractionResult.PASS;

        ServerLevel level = player.getLevel();
        Mob currentTarget = mob;
        IToolStackView tool = ToolStack.from(stack);
        var persistentData = tool.getPersistentData();

        float range = tool.getStats().get(solidarytinkerToolstats.DETECTION_RANGE);
        float amount = tool.getStats().get(solidarytinkerToolstats.TARGET_AMOUNT);
        int powerfulAttackLevel = ModifierUtil.getModifierLevel(stack, solidarytinkerModifiers.ENERGY_POWER_FUL_STATIC_MODIFIER.getId());
        boolean houGuoYu = ModifierUtil.getModifierLevel(stack, solidarytinkerModifiers.HOU_GUO_YU_STATIC_MODIFIER.getId())>0;

        int coolingDownTick = persistentData.getInt(COOLING);
        if (coolingDownTick > 0) {
            if(houGuoYu){
                level.playSound(null, player.getOnPos(), SolidarytinkerSound.electric_cooldown.get(), SoundSource.AMBIENT, 1, 1);
            }
            playerIn.displayClientMessage(Component.literal("还在冷却,冷却时长为" + coolingDownTick + "秒").withStyle(style -> style.withColor(0xe8a6fd)), true);
            return InteractionResult.PASS;
        }
        int currentEnergy = getEnergyStorage(tool);
        if (currentEnergy < 50000) {
            if(houGuoYu){
                level.playSound(null, player.getOnPos(), SolidarytinkerSound.electric_not_full.get(), SoundSource.AMBIENT, 1, 1);
            }
            playerIn.displayClientMessage(Component.literal("电量不足以发动一次基础攻击").withStyle(style -> style.withColor(0xbdb2fd)), true);
            return InteractionResult.PASS;
        }

        //成功攻击
        mob.getPersistentData().putInt("electric_batons_extra_hurt", 3);

        int cooldownTick= calculateCooldown(tool,player,random);
        if(cooldownTick>0){
            persistentData.putInt(COOLING,cooldownTick);
        }
        if(player.isCreative()){
            player.containerMenu.broadcastChanges();
        }
        costEnergy(tool, 50000);

        //主目标
        ToolAttackUtil.attackEntity(tool, player, InteractionHand.MAIN_HAND, mob, () -> 1, false);
        int currentCost = Math.round(getMaxEnergyStorage(tool) * 0.3f);
        if (powerfulAttackLevel > 0) {
            runPowerfulAttack(mob, player, powerfulAttackLevel, 1,currentCost);
            costEnergy(tool, currentCost);
        }

        //连锁伤害
        List<Mob> hasBeenAttackedMob = new ArrayList<>();
        hasBeenAttackedMob.add(mob);
        level.playSound(null, player.getOnPos(), SolidarytinkerSound.electric_hit.get(), SoundSource.AMBIENT, 1, 1);
        for (int i = 0; i < amount; i++) {
            Mob nextMob = getNearestMob(currentTarget, hasBeenAttackedMob, range);
            if (nextMob == null) break;
            if (nextMob instanceof TamableAnimal) continue;
            if (nextMob instanceof Villager) continue;
            ToolAttackUtil.attackEntity(tool, player, InteractionHand.MAIN_HAND, nextMob, () -> 1, false);
            if (powerfulAttackLevel > 0) {
                runPowerfulAttack(nextMob, player, powerfulAttackLevel, i + 2,currentCost);
            }
            drawParticleBeam(currentTarget, nextMob, random, level);
            currentTarget = nextMob;
            hasBeenAttackedMob.add(nextMob);
        }
        return InteractionResult.SUCCESS;
    }
    private int calculateCooldown(IToolStackView view, Player player,Random random){
        int fastChargeLevel = view.getModifierLevel(solidarytinkerModifiers.FAST_CHARGE_STATIC_MODIFIER.getId());
        int rhyLevel=view.getModifierLevel(solidarytinkerModifiers.DEADLY_RHYTHM_STATIC_MODIFIER.getId());
        if(rhyLevel>0&&random.nextInt(10)<7)return 0;
        int baseCoolingDownTick = Math.max(8 - fastChargeLevel, 3);
        if (solidarytinker.TI && ModifierLevel.curioModifierLevel(player, TinkerCuriosModifier.BHA_STATIC_MODIFIER.getId()) > 0) {
            if (baseCoolingDownTick < 4) {
                return 1;
            } else return (int) (baseCoolingDownTick / 2f);
        }else
            return baseCoolingDownTick;

    }

    private void drawParticleBeam(Mob mob1, Mob mob2, Random random, ServerLevel level) {
        double d0 = mob2.getX() - mob1.getX();
        double d1 = mob2.getY() + (double) (mob2.getBbHeight() * 0.5F) - (mob1.getY() + (double) mob1.getEyeHeight() * 0.5D);
        double d2 = mob2.getZ() - mob1.getZ();
        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 = d0 / d3;
        d1 = d1 / d3;
        d2 = d2 / d3;
        double d4 = random.nextDouble();
        while (d4 < d3) {
            d4 += 0.3D;
            level.sendParticles(ParticleTypes.FIREWORK, mob1.getX() + d0 * d4, mob1.getY() + d1 * d4 + (double) mob1.getEyeHeight() * 0.5D, mob1.getZ() + d2 * d4, 2, 0.0D, 0.0D, 0.0D, 0);
        }
    }

    private Mob getNearestMob(Mob mob1, List<Mob> beenAttackedMob, double range) {
        AABB aabb = new AABB(mob1.getOnPos()).inflate(range);
        var list = mob1.level.getEntitiesOfClass(Mob.class, aabb, entity -> entity != mob1);
        Mob nearestMob = null;
        double currentRange = Double.MAX_VALUE;
        for (Mob mob : list) {
            if (beenAttackedMob.contains(mob)) continue;
            if (mob.distanceTo(mob1) <= currentRange) {
                currentRange = mob.distanceTo(mob1);
                nearestMob = mob;
            }
        }
        return nearestMob;
    }

    private int getEnergyStorage(IToolStackView view) {
        return view.getPersistentData().getInt(FluxStorage.STORED_ENERGY);
    }

    private int getMaxEnergyStorage(IToolStackView view) {
        int add = view.getStats().getInt(solidarytinkerToolstats.ENERGY_CAPACITY);
        int base = view.getVolatileData().getInt(FluxStorage.MAX_ENERGY);
        return add + base;
    }

    private void costEnergy(IToolStackView view, int cost) {
        FluxStorage.removeEnergy(view, cost, false, true);
    }

    private void runPowerfulAttack( LivingEntity entity, LivingEntity attacker, int modifierLevel, int index,int cost) {

        float scale = 1.2f * (1 - 0.06f * (index - 1));
        float extraDamage = cost * 0.01f * modifierLevel;
        float totalDamage = extraDamage * scale;
        DamageSource powerAttack = new EntityDamageSource("power_attack", attacker).bypassArmor();
        entity.invulnerableTime = 0;
        entity.hurt(powerAttack, totalDamage);

    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn.tickCount % 20 != 0) return;
        IToolStackView tool = ToolStack.from(stack);
        var persistentData = tool.getPersistentData();
        int coolingDownTick = persistentData.getInt(COOLING);
        if (coolingDownTick > 0) {
            persistentData.putInt(COOLING, coolingDownTick - 1);
        }
    }
}
