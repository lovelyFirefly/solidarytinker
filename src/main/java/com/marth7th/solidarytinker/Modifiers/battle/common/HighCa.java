package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class HighCa extends BattleModifier implements BlockBreakModifierHook {
    {
        MinecraftForge.EVENT_BUS.addListener(this::MobEffectAdd);
    }

    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder builder) {
        builder.addHook(this, ModifierHooks.BLOCK_BREAK);
    }

    private void MobEffectAdd(MobEffectEvent.Applicable event) {
        if (event.getEntity().hasEffect(solidarytinkerEffects.higtca.get())) {
            event.setResult(Event.Result.DENY);
        }
        if (ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), this.getId()) > 0 && !event.getEffectInstance().getEffect().isBeneficial()) {
            int a = RANDOM.nextInt(10);
            if (a < ModifierLevel.getTotalArmorModifierlevel(event.getEntity(), this.getId()) * 2) {
                event.setResult(Event.Result.DENY);
                event.getEntity().heal(2);
            }
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity.tickCount % 5 > 0 && entity.hasEffect(solidarytinkerEffects.higtca.get())) {
            ToolDamageUtil.repair(tool, 1);
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int a = RANDOM.nextInt(10);
        if (a < 2 * modifier.getLevel()) {
            context.getAttacker().addEffect(new MobEffectInstance(solidarytinkerEffects.higtca.get(), 200 * modifier.getLevel(), 0));
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolHarvestContext toolHarvestContext) {
        int a = RANDOM.nextInt(10);
        if (a < 2 * modifierEntry.getLevel()) {
            if (toolHarvestContext.getPlayer() != null) {
                toolHarvestContext.getPlayer().addEffect(new MobEffectInstance(solidarytinkerEffects.higtca.get(), 200 * modifierEntry.getLevel(), 0));
            }
        }
    }
}
