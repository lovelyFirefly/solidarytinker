package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Items.common.FirstAddItem;
import com.marth7th.solidarytinker.Items.ingot.*;
import com.marth7th.solidarytinker.tools.Stats.CoreBatteryMaterialStats;
import com.marth7th.solidarytinker.tools.Stats.SoulGeHeartMaterialStats;
import com.marth7th.solidarytinker.tools.tinkeritem.*;
import com.marth7th.solidarytinker.tools.toolDefinitions;
import com.marth7th.solidarytinker.util.compound.DynamicComponentUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.List;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;


public class solidarytinkerItem {
    public static final ItemDeferredRegisterExtension OTHER_ITEM = new ItemDeferredRegisterExtension(MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final ItemDeferredRegisterExtension ModifiableArmor = new ItemDeferredRegisterExtension(MOD_ID);
    private static final Item.Properties TOOL = (new Item.Properties()).tab(solidarytinkerTab.TOOL).stacksTo(1);
    private static final Item.Properties PART = (new Item.Properties()).tab(solidarytinkerTab.TOOL).stacksTo(64);
    private static final Item.Properties CASTS = (new Item.Properties()).tab(solidarytinkerTab.CASTS).stacksTo(64);
    public static final CastItemObject trident_head_cast = OTHER_ITEM.registerCast("trident_head", CASTS);
    public static final RegistryObject<Item> leadamalgamation_ingot = ITEMS.register("leadamalgamation_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<ModifiableItem> trident = ITEMS.register("trident", () -> new trident(TOOL, toolDefinitions.TRIDENT));
    public static final RegistryObject<ModifiableItem> soulge = ITEMS.register("soulge", () -> new SoulGe(TOOL, toolDefinitions.Soulge));
    public static final EnumObject<ArmorSlotType,ModifiableArmorItem> energy_plate = ModifiableArmor.registerEnum("energy_plate",ArmorSlotType.values(), type -> new EnergyPlateArmor(toolDefinitions.ENERGY_PLATE, type, TOOL));
    public static final RegistryObject<Item> takeru = ITEMS.register("takeru", () -> new takeru(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> dwarf_ingot = ITEMS.register("dwarf_ingot", () -> new dwarf_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> bloodmeat_ingot = ITEMS.register("bloodmeat_ingot", () -> new bloodmeat_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> starfall_ingot = ITEMS.register("starfall_ingot", () -> new starfall_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<ToolPartItem> mining_core = ITEMS.register("mining_core", () -> new ToolPartItem(PART, StatlessMaterialStats.BINDING.getIdentifier()));
    public static final RegistryObject<ToolPartItem> soulge_heart = ITEMS.register("soulge_heart", () -> new ToolPartItem(PART, SoulGeHeartMaterialStats.ID));
    public static final RegistryObject<ToolPartItem> core_battery = ITEMS.register("core_battery", () -> new ToolPartItem(PART, CoreBatteryMaterialStats.ID));
    public static final RegistryObject<ToolPartItem> trident_head = ITEMS.register("trident_head", () -> new ToolPartItem(PART, HeadMaterialStats.ID));
    public static final RegistryObject<ModifiableItem> mekatool = ITEMS.register("mekatool", () -> new MekaTool(TOOL, toolDefinitions.MEKATOOL));
    public static final RegistryObject<ModifiableItem> ElectricBatons = ITEMS.register("electric_batons", () -> new ElectricBatons(TOOL, toolDefinitions.electric_batons));
    public static final RegistryObject<Item> icefantasy_ingot = ITEMS.register("icefantasy_ingot", () -> new icefantasy_ingot(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> kemo33 = ITEMS.register("kemo33", () -> new kemo33(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> magicuranium_ingot = ITEMS.register("magicuranium_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> heavy_armor_steel_ingot = ITEMS.register("heavy_armor_steel_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> violane = ITEMS.register("violane", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> damascus_steel_ingot = ITEMS.register("damascus_steel_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> saint_chief_ingot = ITEMS.register("saint_chief_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> experience_steel_ingot = ITEMS.register("experience_steel_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> extremelycoldsteel_ingot = ITEMS.register("extremelycoldsteel_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<BlockItem> dwarf_block = ITEMS.register("dwarf_block", () -> new BlockItem(solidarytinkerBlock.dwarf_block.get(), new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> cold_chroma_alloy_ingot = ITEMS.register("cold_chroma_alloy_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> first_add_item = ITEMS.register("first_add_item", () -> new FirstAddItem(new Item.Properties().tab(solidarytinkerTab.MATERIALS)));
    public static final RegistryObject<Item> blast_burner = ITEMS.register("blast_burner", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.literal("跑刀露天刷新的我你爱答不理,CTI里的我你高攀不起").withStyle(style -> style.withColor(0xaa00ff)));
        }
    });
    public static final RegistryObject<Item> elysia = ITEMS.register("elysia_ingot", () -> new Item(new Item.Properties().tab(solidarytinkerTab.MATERIALS)){
        @Override
        public Component getName(ItemStack p_41458_) {
            return Component.literal("爱莉希雅").withStyle(style -> style.withColor(0xffaaff));
        }

        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(DynamicComponentUtil.scrollColorfulText.getColorfulText("素手挽清风",null,new int[]{0xffd0f8,0xffaaff,0xed8eff},20,20,false));
        }
    });
    public solidarytinkerItem() {
    }
}
