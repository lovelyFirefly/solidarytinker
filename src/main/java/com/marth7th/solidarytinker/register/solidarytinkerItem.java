package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Items.ingot.*;
import com.marth7th.solidarytinker.tools.tinkeritem.MekaTool;
import com.marth7th.solidarytinker.tools.tinkeritem.trident;
import com.marth7th.solidarytinker.tools.toolDefinitions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;


public class solidarytinkerItem {
    public static final ItemDeferredRegisterExtension OTHER_ITEM = new ItemDeferredRegisterExtension("solidarytinker");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "solidarytinker");
    public static final RegistryObject<Item> leadamalgamation_ingot = ITEMS.register("leadamalgamation_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    private static final Item.Properties TOOL = (new Item.Properties()).tab(solidarytinkerTab.TOOL).stacksTo(1);
    private static final Item.Properties PART = (new Item.Properties()).tab(solidarytinkerTab.TOOL).stacksTo(64);
    public static final RegistryObject<ModifiableItem> trident = ITEMS.register("trident", () -> new trident(TOOL, toolDefinitions.TRIDENT));
    public static final RegistryObject<Item> takeru = ITEMS.register("takeru", () -> new takeru(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> dwarf_ingot = ITEMS.register("dwarf_ingot", () -> new dwarf_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> bloodmeat_ingot = ITEMS.register("bloodmeat_ingot", () -> new bloodmeat_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> starfall_ingot = ITEMS.register("starfall_ingot", () -> new starfall_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<ToolPartItem> mining_core = ITEMS.register("mining_core", () -> new ToolPartItem(PART, StatlessMaterialStats.BINDING.getIdentifier()));
    public static final RegistryObject<ModifiableItem> mekatool = ITEMS.register("mekatool", () -> new MekaTool(TOOL, toolDefinitions.MEKATOOL));
    public static final RegistryObject<Item> icefantasy_ingot = ITEMS.register("icefantasy_ingot", () -> new icefantasy_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> kemo33 = ITEMS.register("kemo33", () -> new kemo33(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> magicuranium_ingot = ITEMS.register("magicuranium_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> rainbow_ingot = ITEMS.register("rainbow_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> violane = ITEMS.register("violane", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> damascus_steel_ingot = ITEMS.register("damascus_steel_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> saint_chief_ingot = ITEMS.register("saint_chief_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> experience_steel_ingot = ITEMS.register("experience_steel_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> extremelycoldsteel_ingot = ITEMS.register("extremelycoldsteel_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<BlockItem> dwarf_block = ITEMS.register("dwarf_block", () -> new BlockItem(solidarytinkerBlock.dwarf_block.get(), new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    private static final Item.Properties CASTS = (new Item.Properties()).tab(solidarytinkerTab.CASTS).stacksTo(64);
    public static final CastItemObject trident_head_cast = OTHER_ITEM.registerCast("trident_head", CASTS);
    public static final RegistryObject<ToolPartItem> trident_head = ITEMS.register("trident_head", () -> new ToolPartItem(PART, HeadMaterialStats.ID));
    public static final RegistryObject<Item> cold_chroma_alloy_ingot = ITEMS.register("cold_chroma_alloy_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public solidarytinkerItem() {
    }
}
