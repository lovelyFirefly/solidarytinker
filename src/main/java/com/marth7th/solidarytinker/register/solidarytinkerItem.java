package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Items.ingot.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class solidarytinkerItem {
    public solidarytinkerItem(){}
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "solidarytinker");

    public static final RegistryObject<Item> takeru = ITEMS.register("takeru", ( ) -> new takeru(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> dwarf_ingot = ITEMS.register("dwarf_ingot", ( ) -> new dwarf_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> bloodmeat_ingot = ITEMS.register("bloodmeat_ingot", ( ) -> new bloodmeat_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> starfall_ingot = ITEMS.register("starfall_ingot", ( ) -> new starfall_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> icefantasy_ingot = ITEMS.register("icefantasy_ingot", ( ) -> new icefantasy_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> kemo33 = ITEMS.register("kemo33", ( ) -> new kemo33(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> magicuranium_ingot = ITEMS.register("magicuranium_ingot", ( ) -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> rainbow_ingot = ITEMS.register("rainbow_ingot", ( ) -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> violane = ITEMS.register("violane", ( ) -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> damascus_steel_ingot = ITEMS.register("damascus_steel_ingot", ( ) -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> saint_chief_ingot = ITEMS.register("saint_chief_ingot", ( ) -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> experience_steel_ingot = ITEMS.register("experience_steel_ingot", ( ) -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<BlockItem> dwarf_block = ITEMS.register("dwarf_block",( ) -> new BlockItem(solidarytinkerBlock.dwarf_block.get(),new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
}
