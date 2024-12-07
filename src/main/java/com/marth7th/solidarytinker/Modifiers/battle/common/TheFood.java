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
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class TheFood extends BattleModifier {
    boolean fm = ModList.get().isLoaded("farmersdelight");
    boolean carrot = ModList.get().isLoaded("solcarrot");

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (attacker instanceof Player player) {
            if (carrot && fm) {
                int count = SOLCarrotAPI.getFoodCapability(player).getEatenFoodCount();
                if (count > 30) {
                    return damage + count * 1.5f * level;
                }
            }
        }
        return damage;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            if (carrot && fm) {
                int cw = FoodList.get(player).getEatenFoods().size();
                TooltipModifierHook.addFlatBoost(modifier.getModifier(), Component.translatable("tooltip.solidarytinker.thefood.eated"), cw, list);
            }
        }
    }
}
