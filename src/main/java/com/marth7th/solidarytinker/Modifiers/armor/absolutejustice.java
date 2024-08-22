package com.marth7th.solidarytinker.Modifiers.armor;

import com.gjhi.tinkersinnovation.register.TinkersInnovationItems;
import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;

public class absolutejustice extends ArmorModifier {
    boolean TN = ModList.get().isLoaded("tinkersinnovation");
    public absolutejustice() {
        MinecraftForge.EVENT_BUS.addListener(this::livingAttackEvent);
    }
    {
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtEvent);
    }

    private void livingHurtEvent(LivingHurtEvent event) {
        if (modifierlevel.getsinglearmorlevel(event.getEntity(), solidarytinkerModifiers.ABSOLUTEJUSTICE_STATIC_MODIFIER.getId()) > 0) {
            if(event.getSource().getEntity()==null){
                event.setCanceled(true);
            }
        }
    }

    private void livingAttackEvent(LivingAttackEvent event) {
        if (modifierlevel.getsinglearmorlevel(event.getEntity(), solidarytinkerModifiers.ABSOLUTEJUSTICE_STATIC_MODIFIER.getId()) > 0) {
            if (event.getSource().getEntity() instanceof Player || event.getSource().getEntity() instanceof Mob) {
                int a = 0;
            } else if(TN){
                if(event.getEntity() instanceof Player player){
                if (event.getSource().getEntity() == null&&player.getItemBySlot(EquipmentSlot.OFFHAND).is(TinkersInnovationItems.heavy_shield.get())) {
                event.getEntity().invulnerableTime=1;
                event.setCanceled(true);
                }
                }
            }
        }
    }

}
