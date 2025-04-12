package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Collection;
import java.util.List;

public class CleanCurio extends XICModifier {
    {
        MinecraftForge.EVENT_BUS.addListener(this::AddMobEffect);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    private void onPlayerTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0) {
                    Collection<MobEffectInstance> harmeffect = player.getActiveEffects();
                    for (int i = 0; i < harmeffect.size(); i++) {
                        MobEffectInstance effect = harmeffect.stream().toList().get(i);
                        MobEffect mobEffect = effect.getEffect();
                        if (mobEffect.getCategory() != MobEffectCategory.BENEFICIAL) {
                            player.removeEffect(mobEffect);
                        }
                    }
                }
            }
        }
    }

    private void AddMobEffect(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0 && !event.getEffectInstance().getEffect().isBeneficial()) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

}
