package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.List;

public class inheart extends BattleModifier {
    boolean fm = ModList.get().isLoaded("farmersdelight");
    public boolean havenolevel() {
        return true;
    }


    @Override
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
        if (fm) {
            int ran = RANDOM.nextInt(1000);
            if (ran == 1) {
                list.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            } else if (ran == 2) {
                list.add(new ItemStack(Items.DRAGON_HEAD));
            } else if (ran <= 20) {
                list.add(new ItemStack(Items.GOLDEN_APPLE));
            } else if (ran <= 100) {
                list.add(new ItemStack(Items.APPLE));
            } else if (ran <= 200) {
                list.add(new ItemStack(Items.POTATO));
            } else if (ran <= 300) {
                list.add(new ItemStack(ModItems.RICE.get()));
            } else if (ran <= 400) {
                list.add(new ItemStack(Items.CARROT));
            } else if (ran <= 500) {
                list.add(new ItemStack(ModItems.ONION.get()));
            } else if (ran <= 600) {
                list.add(new ItemStack(ModItems.TOMATO.get()));
            }
        }
    }
}
