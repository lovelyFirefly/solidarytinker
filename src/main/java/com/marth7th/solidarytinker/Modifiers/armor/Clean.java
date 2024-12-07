package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Clean extends ArmorModifier {
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        Collection<MobEffectInstance> harmeffect = entity.getActiveEffects();
        for (int i = 0; i < harmeffect.size(); i++) {
            MobEffectInstance effect = harmeffect.stream().toList().get(i);
            MobEffect mobEffect = effect.getEffect();
            if (mobEffect.getCategory() != MobEffectCategory.BENEFICIAL) {
                entity.removeEffect(mobEffect);
            }
        }
    }

    public boolean havenolevel() {
        return true;
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
        if (event.getEntity() != null && event.getEntity() instanceof LivingEntity) {
            if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), solidarytinkerModifiers.CLEAN_STATIC_MODIFIER.getId())) {
                if (!event.getEffectInstance().getEffect().isBeneficial())
                    event.setResult(Event.Result.DENY);
            }
        }
    }

    @Override
    public void WhenEffectRemove(MobEffectEvent.Remove event) {
        if (ModifierLevel.EquipHasModifierlevel(event.getEntity(), this.getId())) {
            if (event.getEffectInstance() != null) {
                if (event.getEffectInstance().getEffect().isBeneficial()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && ModifierLevel.EquipHasModifierlevel(event.getEntity(), this.getId())) {
            List<MobEffectInstance> BeneficialEffects = player.getActiveEffects().stream().toList();
            List<MobEffect> Beneficial = new ArrayList<>();
            for (int i = 0; i < BeneficialEffects.size(); i++) {
                MobEffect effect = BeneficialEffects.stream().toList().get(i).getEffect();
                if (effect.isBeneficial()) {
                    Beneficial.add(effect);
                }
            }
            event.setAmount(event.getAmount() * Math.max(1 - 0.06F * Beneficial.size(), 0.4F));
        }
    }
}
