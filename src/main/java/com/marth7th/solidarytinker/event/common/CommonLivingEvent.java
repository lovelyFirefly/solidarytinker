package com.marth7th.solidarytinker.event.common;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.register.TinkerCuriosModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.shelf.damagesource.STDamageSource;
import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;
import static com.marth7th.solidarytinker.util.ModloadCotext.isLoadedIngenuity;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommonLivingEvent {
    private static final ResourceLocation DEATH = solidarytinker.getResource("death");

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void CommonLivingAttackEvent(LivingAttackEvent event) {
    }
    @SubscribeEvent
    public static void LivingHealEvent( LivingHealEvent event) {
        if (event.getEntity() != null) {
            float value = SolidarytinkerConfig.Injured.get().floatValue();
            if (event.getEntity().hasEffect(solidarytinkerEffects.seriously_injured.get())) {
                int level = event.getEntity().getEffect(solidarytinkerEffects.seriously_injured.get()).getAmplifier() + 1;
                if (level <= 3) {
                    event.setAmount(event.getAmount() * Math.max(1 - (value * level), 0));
                } else
                    event.setCanceled(true);
            }
            if (event.getEntity().hasEffect(solidarytinkerEffects.mercurypoisoning.get())) {
                event.setAmount(event.getAmount() * 0.2F);
            }
        }
    }

    private static boolean isBypass(DamageSource source) {
        return source.isBypassArmor() || source.isBypassMagic();
    }

    private static float amountShouldCost(Player player, LivingDamageEvent event) {
        if (isBypass(event.getSource())) {
            return Math.max(player.getMaxHealth() * 0.3F, 4);
        }
        return Math.max(player.getMaxHealth() * 0.15F, 2);
    }

    @SubscribeEvent
    public static void TICurioEvents(LivingDamageEvent event) {
        if (isLoadedIngenuity) {
            if (event.getEntity() instanceof Player player) {
                List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
                float OriginallyDamage = event.getAmount();
                for (ItemStack curios : curio) {
                    if (ModifierUtil.getModifierLevel(curios, TinkerCuriosModifier.ANGELPROTECT_STATIC_MODIFIER.getId()) > 0) {
                        if ((event.getSource().getEntity() instanceof Player player1 && player != player1) || event.getAmount() > player.getMaxHealth() * 100) {
                            return;
                        }
                        float FinalAmount = amountShouldCost(player, event);
                        if (OriginallyDamage > player.getMaxHealth()) {
                            event.setAmount(FinalAmount * 2);
                        } else if (OriginallyDamage > player.getMaxHealth() * 0.5F) {
                            event.setAmount(FinalAmount);
                        }
                    }
                }
            }
        }
    }
    private static void applyDamage(LivingEntity entity, LivingEntity attacker, boolean should) {
        if (should && attacker instanceof Player player) {
            entity.hurt(DamageSource.playerAttack(player).bypassArmor().bypassInvul().bypassEnchantments().bypassMagic(), Float.MAX_VALUE);
        } else {
            entity.hurt(STDamageSource.MercuryPoisoning, Float.MAX_VALUE);
        }
    }
    @SubscribeEvent
    public static void soulGe(LivingEvent.LivingTickEvent event) {
        var livingEntity = event.getEntity();
        var entityData = livingEntity.getPersistentData();
        var dieTick = entityData.getInt("ready_to_die");
        var attacker = livingEntity.getLastHurtByMob();
        if(entityData.contains("ready_to_die")){
            entityData.putInt("ready_to_die", dieTick-1);
            switch (dieTick) {
                case 1 -> applyDamage(livingEntity, attacker,true);
                case 2-4-> applyDamage(livingEntity, attacker, livingEntity.isOnGround());
                case 5 -> livingEntity.setDeltaMovement(new Vec3(0, -2.5, 0));
            }
        }
    }
    @SubscribeEvent
    public static void test(LivingDamageEvent event){
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void LivingDeathEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player&&player.isDeadOrDying()) {
            List<ServerPlayer> haveDarkStar = new ArrayList<>();
            if (player.getLevel() instanceof ServerLevel serverLevel) {
                List<ServerPlayer> playerList = serverLevel.players();
                for (ServerPlayer players : playerList) {
                    if (ModifierLevel.inventoryHasThisModifier(players, solidarytinkerModifiers.DARKSTAR_STATIC_MODIFIER.getId())) {
                        haveDarkStar.add(players);
                    }
                }
                for (ServerPlayer players : haveDarkStar) {
                    for (ItemStack stack : players.getInventory().items) {
                        if (stack.getItem() instanceof ModifiableItem) {
                            var view = ToolStack.from(stack).getPersistentData();
                            if (ModifierUtil.getModifierLevel(stack, solidarytinkerModifiers.DARKSTAR_STATIC_MODIFIER.getId()) > 0) {
                                float count = view.getFloat(DEATH);
                                float max = SolidarytinkerConfig.DwarfMaxDamage.get().floatValue();
                                if (count < max) {
                                    view.putFloat(DEATH, player.getMaxHealth() * 0.33f/haveDarkStar.size()+1 + view.getFloat(DEATH));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
