package com.marth7th.solidarytinker.extend.superclass;

import com.marth7th.solidarytinker.extend.energy.FluxStorage;
import com.marth7th.solidarytinker.register.solidarytinkerModifierMekEtsh;
import com.marth7th.solidarytinker.register.solidarytinkerSlots;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class FluxArmorModifier extends ArmorModifier implements VolatileDataModifierHook, ValidateModifierHook, DurabilityDisplayModifierHook, ToolDamageModifierHook {
    public FluxArmorModifier() {
    }

    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY, ModifierHooks.TOOL_DAMAGE);
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
        volatileData.addSlots(solidarytinkerSlots.FLUX, 5);
        if (volatileData.contains(FluxStorage.MAX_ENERGY, 3)) {
            volatileData.putInt(FluxStorage.MAX_ENERGY, volatileData.getInt(FluxStorage.MAX_ENERGY) + this.getCapacity(context, modifier, volatileData) * modifier.getLevel());
        } else {
            volatileData.putInt(FluxStorage.MAX_ENERGY, this.getCapacity(context, modifier, volatileData) * modifier.getLevel());
        }

        if (!volatileData.contains(FluxStorage.ENERGY_OWNER, 8)) {
            volatileData.putString(FluxStorage.ENERGY_OWNER, this.getId().toString());
        }
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (tool instanceof ToolStack && this.isOwner(tool.getVolatileData())) {
            int energy_store = tool.getStats().getInt(solidarytinkerToolstats.ENERGY_STORE);
            if (energy_store > 0) {
                list.add(Component.translatable("modifier.solidarytinker.tooltip.storedenergy").append(String.valueOf(tool.getPersistentData().getInt(FluxStorage.STORED_ENERGY)) + "/" + String.valueOf(tool.getVolatileData().getInt(FluxStorage.MAX_ENERGY) + energy_store)).withStyle(this.getDisplayName().getStyle()));
            } else {
                list.add(Component.translatable("modifier.solidarytinker.tooltip.storedenergy").append(String.valueOf(tool.getPersistentData().getInt(FluxStorage.STORED_ENERGY)) + "/" + String.valueOf(tool.getVolatileData().getInt(FluxStorage.MAX_ENERGY))).withStyle(this.getDisplayName().getStyle()));
            }
        }
    }

    public int getCapacity(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        int add = context.getModifierLevel(solidarytinkerModifierMekEtsh.energyadd.getId());
        int mu = context.getModifierLevel(solidarytinkerModifierMekEtsh.energymultiple.getId());
        return (int) ((10000 + add * 30000) * (1 + mu * 0.4F));
    }

    public boolean isOwner(IModDataView volatileData) {
        return this.getId().toString().equals(volatileData.getString(FluxStorage.ENERGY_OWNER));
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
        return FluxStorage.getEnergyStored(tool) > 0 ? 0xf8cdff : -1;
    }

}
