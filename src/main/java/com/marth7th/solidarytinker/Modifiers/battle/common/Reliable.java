package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import com.xiaoyue.tinkers_ingenuity.register.TIModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;


public class Reliable extends BattleModifier {
    boolean ti = ModList.get().isLoaded("tinkers_ingenuity");
    boolean etsh = ModList.get().isLoaded("etshtinker");
    public boolean havenolevel() {
        return true;
    }
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ModifierLevel.HandsHaveModifierlevel(player, this.getId())) {
                player.setArrowCount(player.getArrowCount() + 2);
                if (etsh) {
                    if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(etshtinkerItems.IONIZED_CANNON.get()) || player.getItemBySlot(EquipmentSlot.MAINHAND).is(etshtinkerItems.IONIZED_CANNON.get())) {
                        event.setAmount(event.getAmount() * 0.5f);
                    }
                }
                if (player.getArrowCount() > 10) {
                    if (player.hasEffect(solidarytinkerEffects.bloodanger.get())) {
                        int effectlevel = player.getEffect(solidarytinkerEffects.bloodanger.get()).getAmplifier();
                        int effecttime = player.getEffect(solidarytinkerEffects.bloodanger.get()).getDuration();
                        player.addEffect(new MobEffectInstance(solidarytinkerEffects.bloodanger.get(), effecttime + 100, effectlevel));
                    } else if (!player.hasEffect(solidarytinkerEffects.bloodanger.get())) {
                        player.addEffect(new MobEffectInstance(solidarytinkerEffects.bloodanger.get(), 100, 0));
                    }
                }
                if (ti) {
                    if (ModifierLevel.getMainhandModifierlevel(player, TIModifiers.SEA_DREAM.getId()) == 0) {
                        event.setAmount(event.getAmount() * 0.5f);
                    }
                }
            }
        }
    }

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if(livingTarget!=null) {
            if (livingTarget.getHealth() >livingTarget.getMaxHealth() * 0.5f){
                return damage * 2f;
            }
        }
        return damage;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        if (attacker instanceof Player player) {
            if (ti) {
                if (ModifierLevel.HandsHaveModifierlevel(attacker, TIModifiers.SEA_DREAM.getId())) {
                    var armor = new ItemStack[]{player.getItemBySlot(EquipmentSlot.HEAD), player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS), player.getItemBySlot(EquipmentSlot.FEET), player.getItemBySlot(EquipmentSlot.OFFHAND), player.getItemBySlot(EquipmentSlot.MAINHAND)};
                    player.setSpeed(1.3f);
                    player.setAbsorptionAmount(Math.min(player.getMaxHealth() * 3.5f, damageDealt));
                    if (RANDOM.nextInt(10) > 8) {
                        for (ItemStack itemStack1 : armor) {
                            player.getCooldowns().removeCooldown(itemStack1.getItem());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit,AbstractArrow arrow,  LivingEntity attacker, LivingEntity target) {
        if(target.getHealth()>target.getMaxHealth() * 0.5f){
            arrow.setBaseDamage(arrow.getBaseDamage() *2f);
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (ti) {
            if (!ModifierLevel.HandsHaveModifierlevel(entity, TIModifiers.SEA_DREAM.getId())) {
                if (ModifierLevel.HandsHaveModifierlevel(entity, modifier.getId())) {
                    if (entity.getLevel().isNight()) {
                        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false));
                        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0, false, false));
                    }
                }
            }
        }
    }
}
