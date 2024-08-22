package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.world.entity.player.Player;
import slimeknights.mantle.client.screen.book.element.TextComponentElement;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Random;

public class wenguanger extends BattleModifier {
    String[] array = {
            "用铁砧砸骑士金属块，下面基岩",
            "自己不会看jei？",
            "jei没有？物品信息没给你说？",
            "什么？你不会按U？",
            "anvil\n\n\nknightmetal\n\n\nbedrock",
    };
    Random random = new Random();
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int randomIndex = random.nextInt(array.length);
        if(context.getAttacker().getServer()!=null){
            if(context.getAttacker() instanceof Player player){
        for (var players : context.getAttacker().getServer().getPlayerList().getPlayers()){
            players.connection.send(new ClientboundSetTitleTextPacket((Component) new TextComponentElement(4, 2, 3, 5, (array[randomIndex]))));
        }
            }
        }
    }
}
