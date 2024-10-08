package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ancientocean extends BattleModifier {
    @Override
    public boolean havenolevel() {
        return true;
    }

    public void LivingAttackEvent(LivingAttackEvent event) {
        Entity entity = event.getSource().getEntity();
        if(entity instanceof LivingEntity attacker){
            if (attacker.getHealth() > attacker.getMaxHealth() * 0.8f && modifierlevel.HandsHaveModifierlevel(attacker, this.getId())) {
                event.getSource().bypassArmor().bypassMagic().bypassInvul().bypassEnchantments();
            }
        }
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        return 0;
    }
}
