package com.marth7th.solidarytinker.Modifiers.Tool;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierfluxed;
import com.marth7th.solidarytinker.register.solidarytinkerModifierMekEtsh;
import com.marth7th.solidarytinker.register.solidarytinkerSlots;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import com.marth7th.solidarytinker.shelf.energy.FluxStorage;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ElectricBatonsCore extends Modifier implements ValidateModifierHook , VolatileDataModifierHook , ModifierRemovalHook , DurabilityDisplayModifierHook , ToolDamageModifierHook , InventoryTickModifierHook {

    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.VALIDATE, ModifierHooks.VOLATILE_DATA,ModifierHooks.DURABILITY_DISPLAY,ModifierHooks.INVENTORY_TICK);
    }

    public Component validate(IToolStackView tool, ModifierEntry modifier) {
        int max = tool.getVolatileData().getInt(FluxStorage.MAX_ENERGY);
        if (tool.getPersistentData().getInt(FluxStorage.STORED_ENERGY) > max) {
            tool.getPersistentData().putInt(FluxStorage.STORED_ENERGY, max);
        }
        return null;
    }

    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        if (tool.getVolatileData().getInt(FluxStorage.MAX_ENERGY) == 0) {
            tool.getPersistentData().remove(FluxStorage.STORED_ENERGY);
        }
        return null;
    }

    public void addVolatileData(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        volatileData.addSlots(solidarytinkerSlots.FLUX, 3);
        if (volatileData.contains(FluxStorage.MAX_ENERGY, 3)) {
            volatileData.putInt(FluxStorage.MAX_ENERGY, volatileData.getInt(FluxStorage.MAX_ENERGY) + this.getCapacity(context, modifier) * modifier.getLevel());
        } else {
            volatileData.putInt(FluxStorage.MAX_ENERGY, this.getCapacity(context, modifier) * modifier.getLevel());
        }
        if (!volatileData.contains(FluxStorage.ENERGY_OWNER, 8)) {
            volatileData.putString(FluxStorage.ENERGY_OWNER, this.getId().toString());
        }
    }

    public int getCapacity(IToolContext context, ModifierEntry modifier) {
        int add = context.getModifierLevel(solidarytinkerModifierMekEtsh.energyadd.getId());
        int mu = context.getModifierLevel(solidarytinkerModifierMekEtsh.energymultiple.getId());
        return (int) ((100000 * modifier.getLevel() + add * 40000) * (1 + mu * 0.6F));
    }

    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        if (FluxStorage.getEnergyStored(tool) > 0) {
            return true;
        }
        return tool.getDamage() > 0;
    }

    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        int amount = tool.getCurrentDurability();
        if (FluxStorage.getEnergyStored(tool) > 0 && FluxStorage.getMaxEnergyStored(tool) > 0) {
            return Math.min((int) (13 * ((float) FluxStorage.getEnergyStored(tool) / FluxStorage.getMaxEnergyStored(tool))) + 1, 13);
        }
        return amount >= max ? 13 : 1 + 13 * (amount - 1) / max;
    }

    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        return FluxStorage.getEnergyStored(tool) > 0 ? 0xece4fd : -1;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if(amount==0) return amount;
        if (FluxStorage.getEnergyStored(tool) > 200 * amount) {
            return 0;
        }
        return amount;
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        int etshEnergy = etshmodifierfluxed.getEnergyStored(tool);
        if (etshEnergy > 0 && notFull(tool)) {
            int trans = FluxStorage.checkTransport(tool);
            int currentStored = getEnergyStorage(tool);
            int maxStored = getMaxEnergyStorage(tool);
            int need = maxStored - currentStored;
            int maxTransfer = Math.min(need, trans);
            int actualTransfer = Math.min(maxTransfer, etshEnergy);
            if (actualTransfer > 0) {
                etshmodifierfluxed.removeEnergy(tool, actualTransfer, false, true);
                FluxStorage.receiveEnergy(tool, actualTransfer, false);
            }
        }
    }
    private boolean notFull(IToolStackView view){
        return getEnergyStorage(view)<getMaxEnergyStorage(view);
    }
    private int getEnergyStorage(IToolStackView view) {
        return view.getPersistentData().getInt(FluxStorage.STORED_ENERGY);
    }
    private int getMaxEnergyStorage(IToolStackView view) {
        int add = view.getStats().getInt(solidarytinkerToolstats.ENERGY_CAPACITY);
        int base = view.getVolatileData().getInt(FluxStorage.MAX_ENERGY);
        return add + base;
    }
}
