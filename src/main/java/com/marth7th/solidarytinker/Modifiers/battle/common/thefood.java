package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.cazsius.solcarrot.api.SOLCarrotAPI;
import com.cazsius.solcarrot.tracking.FoodList;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.ArrayList;
import java.util.List;

public class thefood extends BattleModifier {
    boolean fm = ModList.get().isLoaded("farmersdelight");
    boolean carrot = ModList.get().isLoaded("solcarrot");
    public static List<ModList> modLists = new ArrayList<>();

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if(attacker instanceof Player player) {
            if(carrot&&fm){
            int count = SOLCarrotAPI.getFoodCapability(player).getEatenFoodCount();
                if(count>30){
                    return damage + count * 2f;
                }
            }
        }
        return damage;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
          int cw = FoodList.get(player).getEatenFoods().size();
            list.add(Component.translatable("tooltip.solidarytinker.modifier.thefood"+cw));
        }
    }
}
