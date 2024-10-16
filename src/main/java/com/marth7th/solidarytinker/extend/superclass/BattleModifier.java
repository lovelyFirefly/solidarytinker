package com.marth7th.solidarytinker.extend.superclass;

import com.marth7th.solidarytinker.extend.interfaces.aboutarrow;
import com.marth7th.solidarytinker.extend.interfaces.aboutattack;
import com.marth7th.solidarytinker.extend.interfaces.aboutbuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class BattleModifier extends Modifier implements aboutattack, aboutbuilder, aboutarrow,EquipmentChangeModifierHook {
    public BattleModifier() {
    }


    protected void registerHooks(ModuleHookMap.@NotNull Builder builder) {
        this.initattackinterface(builder);
        this.initbuilderinterface(builder);
        this.initarrowinterface(builder);
        builder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
        super.registerHooks(builder);
    }
    {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(this::LivingAttackEvent);
    }

    public void LivingHurtEvent(LivingHurtEvent event) {
    }
    public void LivingAttackEvent(LivingAttackEvent event) {

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
        return false;
    }
    public boolean shouldDisplay(boolean advanced) {
        if(hidden()){
            return advanced;
        }
        else
            return true;
    }

    @Override
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
    }

    @Nullable
    @Override
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        return null;
    }
}
