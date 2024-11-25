package com.marth7th.solidarytinker.register;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class solidarytinkerTab {
    public static final CreativeModeTab MATERIALS = new CreativeModeTab("solidarytinker.materials") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(solidarytinkerItem.takeru.get());
        }
    };
    public static final CreativeModeTab TOOL = new CreativeModeTab("solidarytinker.tool") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(solidarytinkerItem.trident.get());
        }
    };
    public static final CreativeModeTab CASTS = new CreativeModeTab("solidarytinker.tool") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(solidarytinkerItem.rainbow_ingot.get());
        }
    };

    public solidarytinkerTab() {
    }
}
