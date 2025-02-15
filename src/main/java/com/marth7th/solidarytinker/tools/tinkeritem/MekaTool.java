package com.marth7th.solidarytinker.tools.tinkeritem;


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
        if (ToolLevel == 0) {
            return 5;
        } else if (ToolLevel == 1) {
            return 15;
        } else if (ToolLevel == 2) {
            return 40;
        } else if (ToolLevel == 3) {
            return 80;
        }
        return 5;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }
}
