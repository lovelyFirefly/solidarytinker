package com.marth7th.solidarytinker.extend.superclass;

import com.marth7th.solidarytinker.extend.interfaces.AboutArrow;
import com.marth7th.solidarytinker.extend.interfaces.AboutAttack;
import com.marth7th.solidarytinker.extend.interfaces.AboutBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class BattleModifier extends Modifier implements AboutAttack, AboutBuilder, AboutArrow, EquipmentChangeModifierHook {
    public BattleModifier() {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::LivingAttackEvent);
        MinecraftForge.EVENT_BUS.addListener(this::LivingDamageEvent);
    }

    protected void registerHooks(ModuleHookMap.@NotNull Builder builder) {
        this.initattackinterface(builder);
        this.initbuilderinterface(builder);
        this.initarrowinterface(builder);
        builder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
        super.registerHooks(builder);
    }

    public void LivingDamageEvent(LivingDamageEvent event) {
    }

    public void LivingHurtEvent(LivingHurtEvent event) {
    }

    public void LivingAttackEvent(LivingAttackEvent event) {
    }

    public boolean havenolevel() {
        return false;
    }

    public @NotNull Component getDisplayName(int level) {
        return this.havenolevel() ? super.getDisplayName() : super.getDisplayName(level);
    }

    public boolean hidden() {
        return false;
    }

    public boolean shouldDisplay(boolean advanced) {
        return this.hidden() ? advanced : true;
    }

    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
    }

    public @Nullable Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        return null;
    }
}
