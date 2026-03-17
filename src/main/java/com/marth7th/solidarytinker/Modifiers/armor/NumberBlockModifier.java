package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class NumberBlockModifier extends Modifier {

    public static ResourceLocation BLOCK_NUMBER = solidarytinker.getResource("hoshino_block_number");

    public static int getTotalBlockNumber(Player player) {
        var armorList = player.getInventory().armor;
        int blockAmount = 0;
        for (ItemStack stack : armorList) {
            var view = ToolStack.from(stack);
            int eachAmount = getBlockNumber(view);
            blockAmount = blockAmount + eachAmount;
        }
        return blockAmount;
    }

    public static int getBlockNumber(IToolStackView view) {
        return view.getPersistentData().getInt(BLOCK_NUMBER);
    }

    public static void setBlockNumber(IToolStackView view, int amount) {
        view.getPersistentData().putInt(BLOCK_NUMBER, amount);
    }
}
