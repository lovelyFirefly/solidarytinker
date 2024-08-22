package com.marth7th.solidarytinker.register;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class solidarytinkerTab {
    public static final CreativeModeTab MATERIALS = new CreativeModeTab("materials") {
        @Override
        public  ItemStack makeIcon() {
            return new ItemStack((ItemLike) solidarytinkerItem.dwarf_ingot.get());
        }
    };
    public solidarytinkerTab(){}
}
