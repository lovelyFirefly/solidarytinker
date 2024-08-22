package com.marth7th.solidarytinker.Items.ingot;

import com.marth7th.solidarytinker.register.solidarytinkerTab;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class bloodmeat_ingot extends Item {
    public bloodmeat_ingot(Properties properties) {
        super(properties.tab(solidarytinkerTab.MATERIALS).stacksTo(64));
    }
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.bloodmeat_ingot.desc1").withStyle(ChatFormatting.RED));
        tooltip.add(Component.translatable("tooltip.bloodmeat_ingot.desc2").withStyle(ChatFormatting.OBFUSCATED,ChatFormatting.RED));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
