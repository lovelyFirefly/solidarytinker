package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.tools.tinkeritem.ElectricBatons;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SandStrom extends NoLevelsModifier implements MeleeDamageModifierHook , MeleeHitModifierHook {
    public static ResourceLocation EXTRA_HIT_NUMBER = solidarytinker.getResource("hoshino_extra_hit");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE,ModifierHooks.MELEE_HIT);
    }
    private int getExtraLa(IToolStackView view){
        return view.getPersistentData().getInt(EXTRA_HIT_NUMBER);
    }
    private void setExtraLa(IToolStackView view,int number){
        view.getPersistentData().putInt(EXTRA_HIT_NUMBER,number);
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        var player=context.getPlayerAttacker();
        var target=context.getLivingTarget();
        if(player==null||target==null)return damage;
        int number=getExtraLa(tool);
        if(number==2){
            player.level.playSound(null,player.getOnPos(), SoundEvents.PUFFER_FISH_BLOW_UP, SoundSource.NEUTRAL,1,1);
        }
        if(number<3){
            setExtraLa(tool,number+1);
        }else {
            setExtraLa(tool,0);
            var stack=player.getEnderChestInventory().getItem(0);
            if(stack.isEmpty())return damage;
            if(ModifierUtil.getModifierLevel(stack,this.getId())>0)return damage;

            if(stack.getItem() instanceof ElectricBatons electricBatons){
                electricBatons.interactLivingEntity(stack,player,target, InteractionHand.MAIN_HAND);
            }
            else ToolAttackUtil.attackEntity(stack,player,target);
        }
        return damage;
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        return 0;
    }
}
