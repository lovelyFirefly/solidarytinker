package com.marth7th.solidarytinker.tools;

import com.marth7th.solidarytinker.Solidarytinker;
import com.marth7th.solidarytinker.register.solidarytinkerItem;
import net.minecraft.sounds.SoundEvents;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

public class toolDefinitions {
    public toolDefinitions() {
    }
    public static final ToolDefinition MEKATOOL = ToolDefinition.create(solidarytinkerItem.mekatool);
    public static final ToolDefinition TRIDENT = ToolDefinition.create(solidarytinkerItem.trident);
    public static final ToolDefinition Soulge = ToolDefinition.create(solidarytinkerItem.soulge);
    public static final ModifiableArmorMaterial ENERGY_PLATE = ModifiableArmorMaterial.create(Solidarytinker.getResource("energy_plate"), SoundEvents.PUFFER_FISH_BLOW_OUT);
}
