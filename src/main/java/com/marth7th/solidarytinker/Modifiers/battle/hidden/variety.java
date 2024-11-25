package com.marth7th.solidarytinker.Modifiers.battle.hidden;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public class variety extends BattleModifier {

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (context.getAttacker() instanceof Player player) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(player.level);
            if (lightningBolt != null) {
                player.level.addFreshEntity(lightningBolt);
                lightningBolt.moveTo(player.position());
                ItemStack diamond = new ItemStack(Items.DIAMOND);
                ItemStack dandlion = new ItemStack(Items.DANDELION);
                ItemStack waterbucket = new ItemStack(Items.WATER_BUCKET);
                player.addItem(dandlion);
                player.addItem(diamond);
                player.addItem(waterbucket);
            }
        }
        return damage;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        Collection<MobEffectInstance> harmeffect;
        harmeffect = entity.getActiveEffects();
        for (int i = 0; i < harmeffect.size(); i++) {
            MobEffectInstance effect = harmeffect.stream().toList().get(i);
            MobEffect effects = effect.getEffect();
            MobEffect harm = effect.getEffect();
            MobEffect neut = effect.getEffect();
            if (harm.getCategory() == MobEffectCategory.HARMFUL) {
                entity.removeEffect(harm);
            }
            if (neut.getCategory() == MobEffectCategory.NEUTRAL) {
                entity.removeEffect(neut);
            }
        }
    }
}
