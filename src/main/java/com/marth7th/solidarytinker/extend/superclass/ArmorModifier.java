package com.marth7th.solidarytinker.extend.superclass;

import com.marth7th.solidarytinker.extend.interfaces.aboutarmor;
import com.marth7th.solidarytinker.extend.interfaces.aboutbuilder;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.*;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;

public class ArmorModifier extends Modifier implements aboutarmor,DamageBlockModifierHook, OnAttackedModifierHook, ModifyDamageModifierHook, ProtectionModifierHook, ElytraFlightModifierHook ,EquipmentChangeModifierHook , aboutbuilder, VolatileDataModifierHook {
    public boolean havenolevel(){return false;}

    public @NotNull Component getDisplayName(int level) {
        if(havenolevel()){
            return super.getDisplayName();
        }else
            return super.getDisplayName(level);
    }
    public boolean hidden(){return  true;};
    public boolean shouldDisplay(boolean advanced) {
        if(hidden()){
            return hidden() || advanced;
        }else return advanced;
    }
    public ArmorModifier(){
    }
    protected void registerHooks(ModuleHookMap.Builder builder) {
        this.initarmorinterface(builder);
        builder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE,ModifierHooks.ELYTRA_FLIGHT,ModifierHooks.MODIFY_HURT,ModifierHooks.VOLATILE_DATA);
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK, ModifierHooks.ON_ATTACKED, ModifierHooks.MODIFY_DAMAGE);
        builder.addHook(this, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOL_STATS, ModifierHooks.INVENTORY_TICK, ModifierHooks.TOOL_DAMAGE, ModifierHooks.TOOLTIP);
    }

}
