package com.marth7th.solidarytinker.Modifiers.battle.biomancy;


import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class Corrode extends BattleModifier {


    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if(context.getLivingTarget()!=null){
            addCorrode(context.getLivingTarget(),modifier.getLevel());
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if(target!=null){
            addCorrode(target,level);
        }
    }
    private void addCorrode(LivingEntity entity,int amount){
        var nbt=entity.getPersistentData();
        int current=nbt.getInt("corrode_amount");
        if(current<200){
            nbt.putInt("corrode_amount",current+amount);
        }
    }
}
