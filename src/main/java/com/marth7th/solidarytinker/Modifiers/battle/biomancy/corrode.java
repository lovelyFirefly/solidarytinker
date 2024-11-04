package com.marth7th.solidarytinker.Modifiers.battle.biomancy;


import com.github.elenterius.biomancy.init.ModMobEffects;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class corrode extends BattleModifier {
    public static boolean enabled = ModList.get().isLoaded("biomancy");

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if(enabled&context.getLivingTarget()!=null){
            context.getLivingTarget().addEffect(new MobEffectInstance(ModMobEffects.CORROSIVE.get(),100,modifier.getLevel()));
            context.getLivingTarget().addEffect(new MobEffectInstance(ModMobEffects.ARMOR_SHRED.get(),100,modifier.getLevel()));
            context.getLivingTarget().addEffect(new MobEffectInstance(ModMobEffects.BLEED.get(),100,modifier.getLevel()));
        }
    }
    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if(enabled&target!=null){
            target.addEffect(new MobEffectInstance(ModMobEffects.CORROSIVE.get(),100,level));
            target.addEffect(new MobEffectInstance(ModMobEffects.ARMOR_SHRED.get(),100,level));
            target.addEffect(new MobEffectInstance(ModMobEffects.BLEED.get(),100,level));
            target.invulnerableTime=0;
        }
    }
}
