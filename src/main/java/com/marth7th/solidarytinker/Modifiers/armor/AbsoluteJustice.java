package com.marth7th.solidarytinker.Modifiers.armor;

import com.gjhi.tinkersinnovation.register.TinkersInnovationItems;
import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;


public class AbsoluteJustice extends ArmorModifier {
    @Override
    public boolean havenolevel() {
        return true;
    }

    boolean TN = ModList.get().isLoaded("tinkersinnovation");

    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            if (ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), solidarytinkerModifiers.ABSOLUTEJUSTICE_STATIC_MODIFIER.getId()) > 0) {
                if (event.getSource().getEntity() == null) {
                    event.setCanceled(event.getSource() != DamageSource.OUT_OF_WORLD || !(event.getAmount() > Float.MAX_VALUE));
                }
            }
        }
    }

    public void LivingAttackEvent(LivingAttackEvent event) {
        if (ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), solidarytinkerModifiers.ABSOLUTEJUSTICE_STATIC_MODIFIER.getId()) > 0) {
            if (TN) {
                if (event.getEntity() instanceof Player player) {
                    if (event.getSource().getEntity() == null && player.getItemBySlot(EquipmentSlot.OFFHAND).is(TinkersInnovationItems.heavy_shield.get())) {
                        event.getEntity().invulnerableTime = 80;
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
