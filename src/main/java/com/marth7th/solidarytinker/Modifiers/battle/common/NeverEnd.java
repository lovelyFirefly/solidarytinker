package com.marth7th.solidarytinker.Modifiers.battle.common;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.EnchantmentModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Map;

public class NeverEnd extends NoLevelsModifier implements EnchantmentModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ENCHANTMENTS);
    }

    @Override
    public int updateEnchantmentLevel(IToolStackView tool, ModifierEntry modifierEntry, Enchantment enchantment, int level) {
        int bonus = modifierEntry.getLevel()* 2;
        if (bonus>0&&(enchantment== Enchantments.MOB_LOOTING||enchantment==Enchantments.BLOCK_FORTUNE)){
            level+=bonus;
        }
        return level;
    }

    @Override
    public void updateEnchantments(IToolStackView tool, ModifierEntry modifierEntry, Map<Enchantment, Integer> map) {
        int bonus = modifierEntry.getLevel()* 2;
        if (bonus>0){
            EnchantmentModifierHook.addEnchantment(map, Enchantments.BLOCK_FORTUNE,bonus);
            EnchantmentModifierHook.addEnchantment(map, Enchantments.MOB_LOOTING,bonus);
        }
    }
}
