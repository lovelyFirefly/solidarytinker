package com.marth7th.solidarytinker.Modifiers.battle.mekanism;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import mekanism.api.Chunk3D;
import mekanism.api.MekanismAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;

public class raditionprotect extends BattleModifier {
    public static boolean enabled = ModList.get().isLoaded("mekanism");

    public @NotNull InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource == InteractionSource.RIGHT_CLICK) {
            GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    Random random = new Random();
    String[] array = {
            "清除干净了喵",
            "下次不要这么不小心了喵",
            "完成了喵，主人",
            "每次清除都会消耗我500耐久喵",
            "注意不要再乱挖管道了喵",
    };

    @Override
    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (enabled && tool.getCurrentDurability() > 500 && entity instanceof Player player) {
            if (MekanismAPI.getRadiationManager().getRadiationLevel(entity) > 0) {
                int randomIndex = random.nextInt(array.length);
                Chunk3D save = new Chunk3D(player.level.dimension(), player.chunkPosition());
                MekanismAPI.getRadiationManager().removeRadiationSources(save);
                ToolDamageUtil.damageAnimated(tool, 500, entity);
                player.sendSystemMessage(Component.translatable(array[randomIndex]));
            }
        }
    }

    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 10;
    }

}
