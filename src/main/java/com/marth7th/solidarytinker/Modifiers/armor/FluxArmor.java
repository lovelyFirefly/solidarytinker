package com.marth7th.solidarytinker.Modifiers.armor;


import com.marth7th.solidarytinker.extend.energy.FluxStorage;
import com.marth7th.solidarytinker.extend.superclass.FluxArmorModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class FluxArmor extends FluxArmorModifier implements DurabilityDisplayModifierHook, ToolDamageModifierHook {
    @Override
    public void LivingAttackEvent(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (modifierlevel.EachaArmorHasModifierlevel(player, this.getId())) {
                List<ItemStack> armors = player.getInventory().armor;
                for (ItemStack armor : armors) {
                    IToolStackView toolstack = ToolStack.from(armor);
                    int amount = (int) event.getAmount();
                    if (FluxStorage.getEnergyStored(toolstack) > 200) {
                        event.getEntity().invulnerableTime = 80;
                        FluxStorage.removeEnergy(toolstack, 200 * amount, false, false);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    public int getCapacity(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        return 1000000;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY, ModifierHooks.TOOL_DAMAGE);
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

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifierEntry, int amount, @Nullable LivingEntity livingEntity) {
        if (FluxStorage.getEnergyStored(tool) > 1000 * amount) {
            FluxStorage.removeEnergy(tool, 1000 * amount, false, false);
            return 0;
        }
        return amount;
    }
}
