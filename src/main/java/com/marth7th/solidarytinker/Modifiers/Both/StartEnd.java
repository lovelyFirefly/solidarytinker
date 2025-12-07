package com.marth7th.solidarytinker.Modifiers.Both;

import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;
import java.util.List;

public class StartEnd extends Modifier implements BlockBreakModifierHook , MeleeHitModifierHook, MeleeDamageModifierHook , TooltipModifierHook  {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK,ModifierHooks.MELEE_HIT,ModifierHooks.MELEE_DAMAGE,ModifierHooks.TOOLTIP);
    }
    public static final ResourceLocation LZ_ENERGY = solidarytinker.getResource("lz_energy");
    public static final ResourceLocation LZXT = solidarytinker.getResource("lzxt");

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        var data=tool.getPersistentData();
        int energy=data.getInt(LZ_ENERGY);
        if(data.getInt(LZXT)>0)return;
        data.putInt(LZ_ENERGY,Math.min(energy + 1,100));
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        var data=tool.getPersistentData();
        int energy=data.getInt(LZ_ENERGY);
        if(data.getInt(LZXT)>0)return;
        data.putInt(LZ_ENERGY,Math.min(energy + 1,100));
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        var data=tool.getPersistentData();
        int LZattack=data.getInt(LZXT);
        int energy=data.getInt(LZ_ENERGY);
        var player=context.getPlayerAttacker();
        if(player==null)return damage;
        //触发
        if(context.isCritical()&&energy>=50&&LZattack==0){
            data.putInt(LZ_ENERGY,energy -50);
            data.putInt(LZXT,65);
            return damage *(1+(0.5f * modifier.getLevel()));
        }
        //结束
        if(LZattack==1){
            data.remove(LZXT);
            player.heal(player.getMaxHealth());
            Collection<MobEffectInstance> currenteffectinstances = player.getActiveEffects();
            for (int i = 0; i < currenteffectinstances.size(); i++) {
                MobEffectInstance effect = currenteffectinstances.stream().toList().get(i);
                MobEffect mobEffect = effect.getEffect();
                if (mobEffect.getCategory() != MobEffectCategory.BENEFICIAL) {
                    player.removeEffect(mobEffect);
                }
            }
            return damage *(1+(0.5f * modifier.getLevel()));
        }
        if(LZattack>0){
            data.putInt(LZXT,LZattack - 1);
            return damage *(1+(0.5f * modifier.getLevel()));
        }
        return damage;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        var data=tool.getPersistentData();
        int LZattack=data.getInt(LZXT);
        int energy=data.getInt(LZ_ENERGY);
        tooltip.add(Component.literal("律者能量:"+energy).withStyle(style -> style.withColor(0xeabeff)));
        if(LZattack>0){
            tooltip.add(Component.literal("剩余律者攻击次数:"+LZattack).withStyle(style -> style.withColor(0xeabeff)));
        }
    }
}
