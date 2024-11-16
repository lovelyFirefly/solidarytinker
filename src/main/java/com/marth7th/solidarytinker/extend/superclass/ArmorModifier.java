package com.marth7th.solidarytinker.extend.superclass;

import com.marth7th.solidarytinker.extend.interfaces.aboutarmor;
import com.marth7th.solidarytinker.extend.interfaces.aboutbuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.*;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.EntityInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class ArmorModifier extends Modifier implements aboutarmor, DamageBlockModifierHook, OnAttackedModifierHook, ModifyDamageModifierHook, ProtectionModifierHook, ElytraFlightModifierHook, EquipmentChangeModifierHook, aboutbuilder, VolatileDataModifierHook, GeneralInteractionModifierHook, EntityInteractionModifierHook {
    public boolean havenolevel(){return false;}

    public @NotNull Component getDisplayName(int level) {
        if(havenolevel()){
            return super.getDisplayName();
        }else
            return super.getDisplayName(level);
    }
    {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(this::LivingAttackEvent);
        MinecraftForge.EVENT_BUS.addListener(this::MobEffectEvent);
        MinecraftForge.EVENT_BUS.addListener(this::WhenEffectRemove);
    }

    public void MobEffectEvent(MobEffectEvent.Applicable event) {
    }

    public void WhenEffectRemove(MobEffectEvent.Remove event) {
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        return super.getDisplayName(tool, entry);
    }

    public void LivingAttackEvent(LivingAttackEvent event) {
    }

    public void LivingHurtEvent(LivingHurtEvent event) {
        if(event.getSource().getEntity() instanceof LivingEntity entity&&event.getEntity() instanceof Player player){
            this.LivingHurt(event,entity,player);
        }
    }

    public void LivingHurt(LivingHurtEvent event, LivingEntity entity, Player player) {
    }

    public boolean hidden(){return false;}
    public boolean shouldDisplay(boolean advanced) {
        if(hidden()){
            return advanced;
        }
        else
            return true;
    }
    public ArmorModifier(){
    }
    protected void registerHooks(ModuleHookMap.Builder builder) {
        this.initarmorinterface(builder);
        builder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE,ModifierHooks.ELYTRA_FLIGHT,ModifierHooks.MODIFY_HURT,ModifierHooks.VOLATILE_DATA);
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK, ModifierHooks.ON_ATTACKED, ModifierHooks.MODIFY_DAMAGE);
        builder.addHook(this, ModifierHooks.VALIDATE, ModifierHooks.ENTITY_INTERACT);
        builder.addHook(this, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOL_STATS, ModifierHooks.INVENTORY_TICK, ModifierHooks.TOOL_DAMAGE, ModifierHooks.TOOLTIP,ModifierHooks.GENERAL_INTERACT);
    }
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
    }

    @Nullable
    @Override
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        return null;
    }
}
