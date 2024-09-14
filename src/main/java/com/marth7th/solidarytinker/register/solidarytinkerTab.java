package com.marth7th.solidarytinker.register;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class solidarytinkerTab {
    public static final CreativeModeTab MATERIALS = new CreativeModeTab("materials") {
        @Override
        public  ItemStack makeIcon() {
            return new ItemStack(solidarytinkerItem.takeru.get());
        }
    };
    public solidarytinkerTab(){}
}
