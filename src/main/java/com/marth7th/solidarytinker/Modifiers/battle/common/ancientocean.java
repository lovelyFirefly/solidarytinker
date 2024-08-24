package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ancientocean extends BattleModifier {
    @Override
    public boolean havenolevel() {
        return true;
    }

    public ancientocean() {
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtEvent);
    }

    private void livingHurtEvent(LivingHurtEvent event) {
        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
        if (modifierlevel.handshavelevel(attacker, this.getId())) {
            if (attacker != null && event.getEntity() != null) {
                if (attacker.getHealth() > attacker.getMaxHealth() * 0.8f) {
                    event.getSource().bypassMagic().bypassArmor().bypassEnchantments().bypassInvul();
                }
            }
        }
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        return 0;
    }
}
