package com.marth7th.solidarytinker.register;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerBlock {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final RegistryObject<Block> dwarf_block = BLOCK.register("dwarf_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL)));
}
