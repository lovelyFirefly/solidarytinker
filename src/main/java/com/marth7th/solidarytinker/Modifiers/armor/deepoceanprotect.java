package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Timer;
import java.util.TimerTask;

public class deepoceanprotect extends ArmorModifier {
    public deepoceanprotect() {
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtEvent);
    }

    private void livingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            int a = modifierlevel.getTotalArmorModifierlevel(entity, solidarytinkerModifiers.DEEPOCEANPROTECT_STATIC_MODIFIER.getId());
            if (a > 0) {
                float cost = player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f * a;
                event.setAmount(event.getAmount() * Math.max(1 - cost * 0.01f, 0.1f));
            }
        }
    }

    private int a = 1;
    private boolean isWaiting = false;

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (a == 1) {
            if (entity instanceof Player player) {
                if (entity.getAbsorptionAmount() < player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f * level / 10) {
                    entity.setAbsorptionAmount(entity.getAbsorptionAmount() + entity.getMaxHealth() * 0.5f * level);
                    entity.setAbsorptionAmount(entity.getAbsorptionAmount() + 5);
                    a = 0;
                    if (!isWaiting) {
                        isWaiting = true;
                        TimerTask t = new TimerTask() {
                            @Override
                            public void run() {
                                a = 1;
                                isWaiting = false;
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(t, 12000);
                    }
                }
            }
        }
        return amount;
    }
}