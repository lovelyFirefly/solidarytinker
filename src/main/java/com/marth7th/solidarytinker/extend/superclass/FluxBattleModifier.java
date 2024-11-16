package com.marth7th.solidarytinker.extend.superclass;

import com.marth7th.solidarytinker.extend.energy.FluxStorage;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
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

@Deprecated
public class FluxBattleModifier extends BattleModifier implements VolatileDataModifierHook, ValidateModifierHook, DurabilityDisplayModifierHook, ToolDamageModifierHook {
    public FluxBattleModifier() {
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
            String var10002 = String.valueOf(tool.getPersistentData().getInt(FluxStorage.STORED_ENERGY));
            MutableComponent var10001 = Component.translatable("modifier.solidarytinker.tooltip.storedenergy");
            if (energy_store > 0) {
                list.add(var10001.append(var10002 + "/" + (tool.getVolatileData().getInt(FluxStorage.MAX_ENERGY) + energy_store)).append(this.getDisplayName().copy()));
            } else {
                list.add(var10001.append(var10002 + "/" + tool.getVolatileData().getInt(FluxStorage.MAX_ENERGY)).append(this.getDisplayName().copy()));
            }
        }

    }

    public int getCapacity(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        return 25000;
    }

    public boolean isOwner(IModDataView volatileData) {
        return this.getId().toString().equals(volatileData.getString(FluxStorage.ENERGY_OWNER));
    }

    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY, ModifierHooks.TOOL_DAMAGE);
    }

    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        return FluxStorage.getEnergyStored(tool) > 0 || tool.getDamage() > 0;
    }

    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        int amount = tool.getCurrentDurability();
        if (FluxStorage.getEnergyStored(tool) > 0 && FluxStorage.getMaxEnergyStored(tool) > 0) {
            return Math.min((int) (13.0F * ((float) FluxStorage.getEnergyStored(tool) / (float) FluxStorage.getMaxEnergyStored(tool))) + 1, 13);
        } else {
            return amount >= max ? 13 : 1 + 13 * (amount - 1) / max;
        }
    }

    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        return FluxStorage.getEnergyStored(tool) > 0 ? 16305663 : -1;
    }

    public int onDamageTool(IToolStackView tool, ModifierEntry modifierEntry, int amount, @Nullable LivingEntity livingEntity) {
        if (FluxStorage.getEnergyStored(tool) > 200 * amount) {
            FluxStorage.removeEnergy(tool, 200 * amount, false, false);
            return 0;
        } else {
            return amount;
        }
    }
}
