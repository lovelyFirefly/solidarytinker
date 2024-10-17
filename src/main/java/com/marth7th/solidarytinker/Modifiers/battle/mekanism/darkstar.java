package com.marth7th.solidarytinker.Modifiers.battle.mekanism;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.List;

public class darkstar extends BattleModifier {
    public int a =0;
    public void LivingHurtEvent(LivingHurtEvent event) {
        if(event.getEntity()!=null){
            LivingEntity entity=event.getEntity();
            if(entity instanceof Player player){
                if(event.getAmount()>player.getHealth()&& modifierlevel.getMainhandModifierlevel(player,this.getId())>0){
                    a=a+1;
                }
            }
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView iToolStackView, ModifierEntry modifierEntry, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean b) {
        if (abstractArrow != null) {
            abstractArrow.setBaseDamage(abstractArrow.getBaseDamage() + a * 0.5f);
        }
    }

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if(livingTarget.isInWater()){
            return damage + a * 0.33f * attacker.getMaxHealth() * level * 1.5f;
        }
        else return damage + a * 0.33f * attacker.getMaxHealth() * level;
    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            list.add(Component.translatable("已提升伤害："+a * player.getMaxHealth() * 0.33).withStyle(ChatFormatting.GRAY));
        }
    }
}
