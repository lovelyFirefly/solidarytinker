package com.marth7th.solidarytinker.tools.tinkeritem;


import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

public class MekaTool extends ModifiableItem {
    public int ToolLevel = 0;

    public int getToolLevel() {
        return ToolLevel;
    }

    public void setToolLevel(int level) {
        ToolLevel = level;
    }
    public MekaTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity) {
        return true;
    }

    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        int Slow = SolidarytinkerConfig.MekaToolSlowSpeed.get();
        int Medium = SolidarytinkerConfig.MekaToolMediumSpeed.get();
        int High = SolidarytinkerConfig.MekaToolHighSpeed.get();
        int Extreme = SolidarytinkerConfig.MekaToolExtremeSpeed.get();
        if (ToolLevel == 0) {
            return Slow;
        } else if (ToolLevel == 1) {
            return Medium;
        } else if (ToolLevel == 2) {
            return High;
        } else if (ToolLevel == 3) {
            return Extreme;
        }
        return Slow;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }
}
