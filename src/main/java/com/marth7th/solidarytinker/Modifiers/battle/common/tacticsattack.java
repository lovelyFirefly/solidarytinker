package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import com.xiaoyue.tinkers_ingenuity.register.TIModifiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public class tacticsattack extends BattleModifier {
    boolean ti = ModList.get().isLoaded("tinkers_ingenuity");

    @Override
    public void addVolatileData(IToolContext iToolContext, ModifierEntry modifierEntry, ModDataNBT modDataNBT) {
        modDataNBT.addSlots(SlotType.ABILITY,modifierEntry.getLevel());
        modDataNBT.addSlots(SlotType.UPGRADE, modifierEntry.getLevel());
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        return (int) (amount * 0.3f);
    }

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (attacker instanceof Player player) {
            if (ti) {
                if (modifierlevel.getMainhandModifierlevel(player, TIModifiers.SEA_DREAM.getId()) > 0) {
                    return damage * 1.3f;
                }
            }
        }
        return damage;
    }
}
