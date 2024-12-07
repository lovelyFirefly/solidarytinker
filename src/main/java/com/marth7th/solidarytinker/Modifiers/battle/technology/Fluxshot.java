package com.marth7th.solidarytinker.Modifiers.battle.technology;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.shelf.energy.FluxStorage;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.item.CrystalshotItem;

import java.util.function.Predicate;

public class Fluxshot extends BattleModifier implements BowAmmoModifierHook {
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BOW_AMMO);
    }

    public int getPriority() {
        return 60;
    }

    @Override
    public ItemStack findAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack standardAmmo, Predicate<ItemStack> ammoPredicate) {
        if (FluxStorage.getEnergyStored(tool) >= 100) {
            return CrystalshotItem.withVariant(tool.getPersistentData().getString(getId()), 64);
        }
        return standardAmmo;
    }

    @Override
    public void shrinkAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack ammo, int needed) {
        FluxStorage.removeEnergy(tool, 100, false, false);
    }
}
