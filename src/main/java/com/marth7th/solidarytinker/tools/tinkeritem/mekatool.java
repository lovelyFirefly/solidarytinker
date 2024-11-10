package com.marth7th.solidarytinker.tools.tinkeritem;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

public class mekatool extends ModifiableItem {
    public mekatool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }
    @Override
    public boolean mineBlock(ItemStack p_41416_, Level p_41417_, BlockState p_41418_, BlockPos p_41419_, LivingEntity p_41420_) {
        return true;
    }
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        return 15f;
    }
    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }
}
