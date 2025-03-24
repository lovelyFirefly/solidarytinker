package com.marth7th.solidarytinker.event.common;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommonLivingEvent {
    @SubscribeEvent
    public static void CommonLivingAttackEvent(LivingAttackEvent event) {
    }

    @SubscribeEvent
    public static void BaseLivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null && event.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity target = event.getEntity();
            DamageSource source = event.getSource();
            CompatLivingHurtEvent(event, target, attacker, source);
        }
    }

    private static void CompatLivingHurtEvent(LivingHurtEvent event, LivingEntity target, LivingEntity attacker, DamageSource source) {
        MobEffect spp = solidarytinkerEffects.SuperPoison.get();
        if (target.hasEffect(spp) && target.getEffect(spp) != null) {
            int a = target.getEffect(spp).getAmplifier();
            event.setAmount(event.getAmount() * 1 + 0.2f * (a + 1));
        }
    }

    @SubscribeEvent
    public static void LivingHealEvent(LivingHealEvent event) {
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
}
