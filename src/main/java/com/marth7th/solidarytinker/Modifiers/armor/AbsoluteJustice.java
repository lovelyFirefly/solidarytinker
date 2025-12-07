package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;


public class AbsoluteJustice extends NoLevelsModifier implements  DamageBlockModifierHook, InventoryTickModifierHook , ToolDamageModifierHook , TooltipModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this,ModifierHooks.DAMAGE_BLOCK,ModifierHooks.INVENTORY_TICK,ModifierHooks.TOOL_DAMAGE,ModifierHooks.TOOLTIP);
    }
    private static final ResourceLocation StagnationWaitTime= solidarytinker.getResource("stagnation_wait_time");


    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount) {
        return source.getEntity() == null;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if(holder instanceof ServerPlayer serverPlayer && serverPlayer.tickCount%20==0){
            int stagnationCD=getHoshinoStagnationWaitTime(tool);
            if(stagnationCD>0){
                setHoshinoStagnationWaitTime(tool,stagnationCD-1);
            }
        }
    }
    public static int getHoshinoStagnationWaitTime(IToolStackView tool){
        return tool.getPersistentData().getInt(StagnationWaitTime);
    }
    public static void setHoshinoStagnationWaitTime(IToolStackView tool,int time){
        tool.getPersistentData().putInt(StagnationWaitTime,time);
    }
    public static int getHoshinoStagnationTime(Player player){
        return player.getPersistentData().getInt("hoshino_stagnation");
    }
    public static void setHoshinoStagnationTime(Player player,int time){
        player.getPersistentData().putInt("hoshino_stagnation",time);
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        return 0;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        int current=getHoshinoStagnationWaitTime(tool);
        tooltip.add(Component.literal("cd"+current));
    }
}
