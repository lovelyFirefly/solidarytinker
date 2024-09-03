package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class inheart extends BattleModifier {
    @Override
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
        int ran = RANDOM.nextInt(1000);
        if(ran == 1 )
        {
            list.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
        }
        else if(ran == 2 )
        {
            list.add(new ItemStack(Items.DRAGON_HEAD));
        }
        else if(ran <= 20 )
        {
            list.add(new ItemStack(Items.GOLDEN_APPLE));
        }
        else if(ran <= 40 )
        {
            list.add(new ItemStack(Items.SCUTE));
        }
        else if(ran <= 220)
        {
            list.add(new ItemStack(Items.APPLE));
        }
    }
}
