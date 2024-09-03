package com.marth7th.solidarytinker.Modifiers.battle.hidden;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;

public class wenguanger extends BattleModifier {
    String[] array = {};
    Random random = new Random();
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int randomIndex = random.nextInt(array.length);
        if(context.getAttacker().getServer()!=null){
            if(context.getAttacker() instanceof Player player){
                player.sendSystemMessage(Component.translatable(array[randomIndex]));
            }
        }
    }
}
