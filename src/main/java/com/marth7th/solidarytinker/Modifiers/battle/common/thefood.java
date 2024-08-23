package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.ArrayList;
import java.util.List;

import static com.cazsius.solcarrot.api.SOLCarrotAPI.getFoodCapability;

public class thefood extends BattleModifier {
    boolean fm = ModList.get().isLoaded("farmersdelight");
    boolean carrot = ModList.get().isLoaded("solcarrot");
    public static List<ModList> modLists = new ArrayList<>();

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if(attacker instanceof Player player) {
            if(carrot){
            int count = getFoodCapability(player).getEatenFoodCount();
                if(count>30){
                }
            }
        }
        return baseDamage;
    }
}
