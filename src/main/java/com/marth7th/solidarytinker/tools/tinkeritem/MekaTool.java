package com.marth7th.solidarytinker.tools.tinkeritem;


import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class MekaTool extends ModifiableItem {
    private static final ResourceLocation SPEED = solidarytinker.getResource("speedlevel");

    public int getToolLevel(ItemStack stack) {
        ModDataNBT nbt = ToolStack.from(stack).getPersistentData();
        return nbt.getInt(SPEED);
    }

    public void setToolLevel(int level, ItemStack stack) {
        ToolStack.from(stack).getPersistentData().putInt(SPEED, level);
    }
    public MekaTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity) {
        return true;
    }

    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        MekaToolSpeedLevel[] toolSpeedLevels = MekaToolSpeedLevel.values();
        ModDataNBT nbt = ToolStack.from(stack).getPersistentData();
        int toolLevel = nbt.getInt(SPEED);
        int truespeed = 0;
        for (int i = 0; i < toolLevel + 1; i++) {
            truespeed = toolSpeedLevels[i].getSpeed();
        }
        return truespeed;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }
}
