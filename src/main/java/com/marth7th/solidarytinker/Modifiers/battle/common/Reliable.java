package com.marth7th.solidarytinker.Modifiers.battle.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


public class Reliable extends NoLevelsModifier implements MeleeDamageModifierHook {
    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        var player=context.getPlayerAttacker();
        var target=context.getLivingTarget();
        if(player==null||target==null)return damage;

        var box=target.getBoundingBox().inflate(5);
        var mobLists=target.level.getEntitiesOfClass(Mob.class,box);
        for(int i=0;i<20;i++){
            mobLists.get(i).hurt(DamageSource.playerAttack(player).bypassMagic().bypassArmor(),damage * 1.88f);
        }
        return damage;
    }
}
