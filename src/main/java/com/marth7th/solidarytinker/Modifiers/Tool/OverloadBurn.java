package com.marth7th.solidarytinker.Modifiers.Tool;

import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class OverloadBurn extends NoLevelsModifier implements ValidateModifierHook {
    @Override
    public @Nullable Component validate(IToolStackView tool, ModifierEntry modifier) {
        if(!(tool.getModifierLevel(solidarytinkerModifiers.EXTRA_BURN_STATIC_MODIFIER.getId())>0)){
            return Component.literal("需要加力燃烧作为前置");
        }else return null;
    }
}
