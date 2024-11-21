package com.marth7th.solidarytinker.extend.energy;

import com.marth7th.solidarytinker.register.solidarytinkerModifierMekEtsh;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.function.Supplier;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;


public class FluxStorage implements IEnergyStorage, ToolCapabilityProvider.IToolCapabilityProvider {
    public static final ResourceLocation MAX_ENERGY = new ResourceLocation(MOD_ID, "max_energy");
    public static final ResourceLocation STORED_ENERGY = new ResourceLocation(MOD_ID, "stored_energy");
    public static final ResourceLocation ENERGY_OWNER = new ResourceLocation(MOD_ID, "energy_owner");
    public final Supplier<? extends IToolStackView> tool;
    public final LazyOptional<IEnergyStorage> capOptional;

    public FluxStorage(ItemStack stack, Supplier<? extends IToolStackView> toolStack) {
        this.tool = toolStack;
        this.capOptional = LazyOptional.of(() -> this);
    }
    public static int receiveEnergy(IToolStackView tool, int maxReceive, boolean simulate) {
        int energyStored = getEnergyStored(tool);
        int level = tool.getModifierLevel(solidarytinkerModifierMekEtsh.energytransport.getId());
        int tran = 1000 + level * 2000;
        int energyReceived = Math.min(getMaxEnergyStored(tool) - energyStored, Math.min(tran, maxReceive));
        int sss = Math.min(getMaxEnergyStored(tool) - energyStored, energyReceived);
        if (!simulate) {
            ModDataNBT persistentData = tool.getPersistentData();
            persistentData.putInt(STORED_ENERGY, energyStored + sss);
        }
        return sss;
    }
    public static void removeEnergy(IToolStackView tool, int energyRemoved, boolean simulate, boolean drain) {
        int energyStored = getEnergyStored(tool);
        ModDataNBT persistentData;
        if (energyStored < energyRemoved) {
            if (drain && !simulate) {
                persistentData = tool.getPersistentData();
                persistentData.putInt(STORED_ENERGY, 0);
            }
        } else {
            if (!simulate) {
                persistentData = tool.getPersistentData();
                persistentData.putInt(STORED_ENERGY, energyStored - energyRemoved);
            }
        }
    }

    public static int getEnergyStored(IToolStackView tool) {
        ModDataNBT persistentData = tool.getPersistentData();
        return persistentData.contains(STORED_ENERGY, 3) ? persistentData.getInt(STORED_ENERGY) : 0;
    }

    public static int getMaxEnergyStored(IToolStackView tool) {
        IModDataView volatileData = tool.getVolatileData();
        if (volatileData.contains(MAX_ENERGY, 3)) {
            int energy_store = tool.getStats().getInt(solidarytinkerToolstats.ENERGY_STORE);
            return energy_store > 0 ? volatileData.getInt(MAX_ENERGY) + energy_store : volatileData.getInt(MAX_ENERGY);
        } else {
            return 0;
        }
    }

    public <T> @NotNull LazyOptional<T> getCapability(IToolStackView tool, @NotNull Capability<T> cap) {
        return tool.getVolatileData().getInt(MAX_ENERGY) > 0 && cap == ForgeCapabilities.ENERGY ? ForgeCapabilities.ENERGY.orEmpty(cap, this.capOptional) : LazyOptional.empty();
    }

    public int receiveEnergy(int maxReceive, boolean simulate) {
        return receiveEnergy(this.tool.get(), maxReceive, simulate);
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    public int getEnergyStored() {
        return getEnergyStored(this.tool.get());
    }

    public int getMaxEnergyStored() {
        return getMaxEnergyStored(this.tool.get());
    }

    public boolean canExtract() {
        return false;
    }

    public boolean canReceive() {
        return true;
    }
}
