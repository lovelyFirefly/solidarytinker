package com.marth7th.solidarytinker.tools.tinkeritem;


import com.marth7th.solidarytinker.register.SolidarytinkerSound;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import com.marth7th.solidarytinker.shelf.Network.Packet.SoulGeAttackPacket;
import com.marth7th.solidarytinker.shelf.Network.STChannel;
import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.compound.DynamicComponentUtil;
import com.marth7th.solidarytinker.util.method.SoulgeHelper;
import net.mehvahdjukaar.dummmmmmy.common.TargetDummyEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SoulGe extends ModifiableItem {

    public SoulGe(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    private final Random rand = new Random();
    public static ResourceLocation IS_SINGLE_MODE = solidarytinker.getResource("is_single_mode");
    public static ResourceLocation TEMPERATURE_RISE_TICK = solidarytinker.getResource("temperature_rise_tick");
    public static ResourceLocation TEMPERATURE_COOLDOWN_TICK = solidarytinker.getResource("temperature_cooldown_tick");
    public static ResourceLocation EXTRA_BURN_SCALE = solidarytinker.getResource("extra_burn_scale");

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
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn.tickCount % 5 != 0) return;
        var view = ToolStack.from(stack);
        int cooldownTick = getTemperatureCooldownTick(view);
        if (cooldownTick > 0) {
            setTemperatureCooldownTick(view, cooldownTick - 1);
        } else setTemperatureRiseTick(view, 0);

    }

    @Override
    public @NotNull List<Component> getStatInformation(@NotNull IToolStackView tool, @Nullable Player player, @NotNull List<Component> tooltips, @NotNull TooltipKey key, @NotNull TooltipFlag tooltipFlag) {
        int[] color = new int[]{0xffea95, 0xffaaff, 0x55c4ff};
        int textSpeed = 20;
        int textStep = 20;
        TooltipBuilder builder = new TooltipBuilder(tool, tooltips);
        if (tool.hasTag(TinkerTags.Items.DURABILITY)) {
            builder.addDurability();
        }
        if (tool.hasTag(TinkerTags.Items.MELEE)) {
            builder.add(ToolStats.ATTACK_DAMAGE);
            builder.add(ToolStats.ATTACK_SPEED);
        }
        boolean isSingleMode = isSingleMode(tool);
        String mode = "多头";
        if (isSingleMode) {
            mode = "单头";
            color = new int[]{0xff0000, 0xffff77, 0x2696ff};
            textSpeed = 40;
            textStep = 60;
        }
        var fluidStack = ToolTankHelper.TANK_HELPER.getFluid(tool);
        var fluid = fluidStack.getFluid();

        String fluidName = Language.getInstance().getOrDefault(fluidStack.getTranslationKey());

        float burnScale = 1;
        float extraFuelScale = 1;
        float extraDamageScale = 1;

        boolean hasCorrectFluid = false;
        boolean enoughFluid = fluidStack.getAmount() > 10f;
        boolean hasOverloadModifier = tool.getModifierLevel(solidarytinkerModifiers.OVERLOAD_BURN_STATIC_MODIFIER.getId()) > 0;

        var array = SoulgeFuel.values();
        for (SoulgeFuel fuel : array) {
            if (fuel.getFluid().equals(fluid)) {
                burnScale = fuel.getScale();
                hasCorrectFluid = true;
                break;
            }
        }
        if (hasCorrectFluid && enoughFluid && hasOverloadModifier) {
            extraFuelScale = SoulgeHelper.getExtraBurnScale(tool);
            extraDamageScale = (float) Math.sqrt(extraFuelScale);
        }

        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "当前魂戈模式",
                ":" + mode, color, textStep, textSpeed, false));
        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "tool_stat.solidarytinker.detection_range",
                ":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.DETECTION_RANGE).intValue()), color, textStep, textSpeed, true));
        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "tool_stat.solidarytinker.exert_times",
                ":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.EXERT_TIMES).intValue()), color, textStep, textSpeed, true));
        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "tool_stat.solidarytinker.attack_frequency",
                ":" + String.format("%d", tool.getStats().get(solidarytinkerToolstats.ATTACK_FREQUENCY).intValue()), color, textStep, textSpeed, true));
        builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                "tool_stat.solidarytinker.kill_threshold",
                ":" + String.format("%d", Math.round(tool.getStats().get(solidarytinkerToolstats.KILLTHRESHOLD) * 100)) + "%", color, textStep, textSpeed, true));
        if (isSingleMode) {
            builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                    "流体名称",
                    ":" + fluidName, color, textStep, textSpeed, false));
            if (hasCorrectFluid) {
                builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                        "流体额外伤害倍率",
                        ":" + burnScale * 100 + "%", color, textStep, textSpeed, false));
            } else builder.add(Component.literal("错误或空的流体,现在为默认倍率"));
            if (hasOverloadModifier) {
                builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                        "燃料消耗倍率",
                        ":" + extraFuelScale, color, textStep, textSpeed, false));
                builder.add(DynamicComponentUtil.scrollColorfulText.getColorfulText(
                        "超载燃烧额外伤害倍率",
                        ":" + String.format("%f", extraDamageScale * 100f) + "%", color, textStep, textSpeed, false));
            }
        }
        builder.addAllFreeSlots();
        for (ModifierEntry entry : tool.getModifierList()) {
            entry.getHook(ModifierHooks.TOOLTIP).addTooltip(tool, entry, player, tooltips, key, tooltipFlag);
        }
        return tooltips;
    }

    private static boolean isSingleMode(IToolStackView view) {
        return view.getPersistentData().getBoolean(IS_SINGLE_MODE);
    }

    private static int getTemperatureRiseTick(IToolStackView view) {
        return view.getPersistentData().getInt(TEMPERATURE_RISE_TICK);
    }

    private static void setTemperatureRiseTick(IToolStackView view, int tick) {
        view.getPersistentData().putInt(TEMPERATURE_RISE_TICK, tick);
    }

    private static int getTemperatureCooldownTick(IToolStackView view) {
        return view.getPersistentData().getInt(TEMPERATURE_COOLDOWN_TICK);
    }

    private static void setTemperatureCooldownTick(IToolStackView view, int tick) {
        view.getPersistentData().putInt(TEMPERATURE_COOLDOWN_TICK, tick);
    }


    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity attacker, @NotNull ItemStack stack, int timeLeft) {
        IToolStackView tool = ToolStack.from(stack);
        var level = attacker.getLevel();

        int dist = Math.round(tool.getStats().get(solidarytinkerToolstats.DETECTION_RANGE));
        int attackFrequency = Math.round(tool.getStats().get(solidarytinkerToolstats.ATTACK_FREQUENCY));
        int exertTimes = Math.round(tool.getStats().get(solidarytinkerToolstats.EXERT_TIMES));

        var pointedEntity = this.getPointedEntity(attacker, level, dist);
        if (pointedEntity instanceof Player) return;

        if (isSingleMode(tool)) {
            if (pointedEntity != null && pointedEntity.isAlive()) {
                if (attacker.tickCount % 5 == 0) {
                    setTemperatureRiseTick(tool, getTemperatureRiseTick(tool) + 1);
                    setTemperatureCooldownTick(tool, 12);
                    if (!level.isClientSide()) {
                        checkTemperatureLevel(getTemperatureRiseTick(tool), attacker);
                    }
                }
                if (attacker.tickCount % attackFrequency == 0) {
                    this.attackSingleTargets(attacker, tool, pointedEntity);
                }
            }
        } else {
            if (pointedEntity != null && pointedEntity.isAlive()) {
                var targetedTimes = pointedEntity.getPersistentData().getInt("targeted");
                if (targetedTimes < exertTimes * 3) {
                    pointedEntity.getPersistentData().putInt("targeted", Math.min(exertTimes * 3, exertTimes + targetedTimes));
                }
            }
            if (attacker.tickCount % attackFrequency == 0) {
                this.attackMultipleTargets(attacker, tool, dist, true);
            }
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
            var box=nearbyEntity.getBoundingBox().inflate(nearbyEntity.getPickRadius());
            var finalBox =box.inflate(0.5f);
            Optional<Vec3> optional = finalBox.clip(playerEyePosition, Vector3d2);
            if (finalBox.contains(playerEyePosition)) {
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

    private void drawParticleBeam(LivingEntity player, LivingEntity target, SimpleParticleType type) {
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
            player.level.addParticle(type, player.getX() + d0 * d4, player.getY() + d1 * d4 + (double) player.getEyeHeight() * 0.5D, player.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
        }
    }

    public void attackMultipleTargets(LivingEntity livingEntity, IToolStackView view, int dist, boolean shouldDrawParticleBeam) {
        float killThreshold = view.getStats().get(solidarytinkerToolstats.KILLTHRESHOLD);
        var x = livingEntity.getX();
        var y = livingEntity.getY();
        var z = livingEntity.getZ();
        List<Mob> targetedMob = new ArrayList<>();
        List<Mob> mobList = livingEntity.level.getEntitiesOfClass(Mob.class, new AABB(x + dist, y + dist, z + dist, x - dist, y - dist, z - dist));
        for (Mob mob : mobList) {
            if (targetedMob.size() < 11 && mob.getPersistentData().contains("targeted")) {
                targetedMob.add(mob);
            }
        }
        for (Mob mob : mobList) {
            var persistentData = mob.getPersistentData();
            var targetedTimes = persistentData.getInt("targeted");
            if (targetedTimes > 0 && livingEntity instanceof Player player && !persistentData.contains("ready_to_die")) {
                ToolAttackUtil.attackEntity(view, player, mob);
                ToolDamageUtil.damageAnimated(view, 1, player);
                if (mob.getLevel().isClientSide()) {
                    var clientTargetedTimes = mob.getPersistentData().getInt("targeted");
                    var uuid = mob.getUUID();
                    STChannel.SendToServer(new SoulGeAttackPacket(uuid, clientTargetedTimes));
                }
                persistentData.putInt("targeted", targetedTimes - 1);

                if (mob.getHealth() < mob.getMaxHealth() * killThreshold && mob.isAlive() && !persistentData.contains("ready_to_die")) {
                    boolean loadedDummy = ModList.get().isLoaded("dummmmmmy");
                    if(!loadedDummy ||!(mob instanceof TargetDummyEntity)){
                        mob.getActiveEffects().removeAll(mob.getActiveEffects());
                        mob.setNoGravity(false);
                        mob.setDeltaMovement(new Vec3(0, 2.5, 0));
                        persistentData.putInt("ready_to_die", 9);
                    }
                }
                if (shouldDrawParticleBeam) {
                    this.drawParticleBeam(player, mob, ParticleTypes.SOUL);
                }
                if (!mob.isAlive()) {
                    persistentData.remove("targeted");
                }
            }
        }
    }

    public void attackSingleTargets(LivingEntity attacker, IToolStackView view, LivingEntity target) {
        if (!(target instanceof Mob mob)) return;
        if (!(attacker instanceof Player player)) return;
        ToolAttackUtil.attackEntity(view, player, mob);
        var temperatureTick = getTemperatureRiseTick(view);
        if (temperatureTick >= 24) {
            this.drawParticleBeam(player, mob, ParticleTypes.SOUL_FIRE_FLAME);
        } else if (temperatureTick >= 8) {
            this.drawParticleBeam(player, mob, ParticleTypes.FLAME);
        } else this.drawParticleBeam(player, mob, ParticleTypes.SOUL);
    }

    public void checkTemperatureLevel(int tick, LivingEntity living) {
        if (tick == 24) {
            easyPlaySound(living, 1.4f);
        } else if (tick == 8) {
            easyPlaySound(living, 1.0f);
        }
    }

    private void easyPlaySound(LivingEntity living, float pitch) {
        if (!living.level.isClientSide()) {
            living.level.playSound(null, living.getOnPos(), SolidarytinkerSound.beam_up.get(), SoundSource.AMBIENT, 2, pitch);
        }
    }
}
