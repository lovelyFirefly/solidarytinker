package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ancientocean extends BattleModifier {
    @Override
    public boolean havenolevel() {
        return true;
    }

    public void LivingAttackEvent(LivingAttackEvent event) {
        if(event.getSource().getEntity() instanceof Player player){
            if(modifierlevel.getMainhandModifierlevel(player,this.getId())>0){
                event.getSource().bypassArmor().bypassInvul().bypassMagic().bypassEnchantments();
            }
        }
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        int MaxDurability = tool.getStats().getInt(ToolStats.DURABILITY);
        int Durability = tool.getCurrentDurability();
        return 0;
    }
}
