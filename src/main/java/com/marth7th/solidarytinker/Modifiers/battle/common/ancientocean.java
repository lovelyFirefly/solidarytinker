package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.List;

public class ancientocean extends BattleModifier {
    @Override
    public boolean havenolevel() {
        return true;
    }

    public void LivingAttackEvent(LivingAttackEvent event) {
        if(event.getSource().getEntity() instanceof Player player){
            List<ItemStack> stack = (List<ItemStack>) player.getAllSlots();
            for (ItemStack stack1 : stack) {
                if (ModifierUtil.getModifierLevel(stack1, this.getId()) > 0) {
                    event.getEntity().invulnerableTime = 0;
                    event.getSource().bypassArmor().bypassMagic().bypassEnchantments().bypassInvul();
                }
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        arrow.discard();
    }

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if(modifierlevel.getMainhandModifierlevel(player,this.getId())>0){
                event.getEntity().invulnerableTime = 0;
                event.getSource().bypassInvul();
            }
        }
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        return 0;
    }
}
