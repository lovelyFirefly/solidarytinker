package com.marth7th.solidarytinker.extend.superclass;

import com.marth7th.solidarytinker.extend.interfaces.aboutarrow;
import com.marth7th.solidarytinker.extend.interfaces.aboutattack;
import com.marth7th.solidarytinker.extend.interfaces.aboutbuilder;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;

public class BattleModifier extends Modifier implements aboutattack, aboutbuilder, aboutarrow,EquipmentChangeModifierHook {
    public BattleModifier() {
    }

    protected void registerHooks(ModuleHookMap.@NotNull Builder builder) {
        this.initattackinterface(builder);
        this.initbuilderinterface(builder);
        this.initarrowinterface(builder);
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
    }

    public boolean havenolevel() {
        return false;
    }

    public @NotNull Component getDisplayName(int level) {
        if (havenolevel()) {
            return super.getDisplayName();
        } else
            return super.getDisplayName(level);
    }

    public boolean hidden() {
        return true;
    }
    public boolean shouldDisplay(boolean advanced) {
        if (hidden()) {
            return hidden() || advanced;
        } else return advanced;
    }
}
