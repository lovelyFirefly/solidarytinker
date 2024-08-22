package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

public class tacticsprotect extends ArmorModifier {
    public tacticsprotect() {
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtEvent);
    }

    public void livingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity enemy = (LivingEntity) event.getSource().getEntity();
        if (enemy != null && entity != null) {
            if (entity instanceof Player player) {
                var armor = new ItemStack[]{player.getItemBySlot(EquipmentSlot.HEAD), player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS), player.getItemBySlot(EquipmentSlot.FEET)};
                int modifierslevel = modifierlevel.getsinglearmorlevel(player, this.getId());
                if (modifierslevel > 0) {
                    for (ItemStack itemStack1 : armor) {
                        if (!player.getCooldowns().isOnCooldown(itemStack1.getItem())) {
                            if (player.getHealth() <= player.getMaxHealth() * 0.2f) {
                                event.setCanceled(true);
                                double x = player.getX();
                                double y = player.getY();
                                double z = player.getZ();
                                List<Mob> MobList = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 8 * modifierslevel, y + 8 * modifierslevel, z + 8 * modifierslevel, x - 8 * modifierslevel, y - 8 * modifierslevel, z - 8 * modifierslevel));
                                for (Mob enemys : MobList) {
                                    if (enemys != null) {
                                        enemys.hurt(DamageSource.playerAttack((Player) entity), entity.getAbsorptionAmount() + entity.getMaxHealth() * 0.3f);
                                    }
                                }
                                player.setAbsorptionAmount(entity.getMaxHealth() * 0.3f + player.getAbsorptionAmount());
                                player.getCooldowns().addCooldown(itemStack1.getItem(), 400);
                                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, Math.min(3, modifierslevel)));
                                if (player.hasEffect(solidarytinkerEffects.bloodanger.get())) {
                                    int effectlevel = entity.getEffect(solidarytinkerEffects.bloodanger.get()).getAmplifier();
                                    player.addEffect(new MobEffectInstance(solidarytinkerEffects.bloodanger.get(), 200, Math.min(modifierslevel, 1 + effectlevel)));
                                }
                                else if(!player.hasEffect(solidarytinkerEffects.bloodanger.get())){
                                    player.addEffect(new MobEffectInstance(solidarytinkerEffects.bloodanger.get(), 200, 0));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addVolatileData(IToolContext iToolContext, ModifierEntry modifierEntry, ModDataNBT modDataNBT) {
        int level = iToolContext.getModifierLevel(solidarytinkerModifiers.TACTICSPROTECT_STATIC_MODIFIER.getId());
        modDataNBT.addSlots(SlotType.ABILITY, level);
        modDataNBT.addSlots(SlotType.DEFENSE, level);
        modDataNBT.addSlots(SlotType.UPGRADE, level);
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        return (int) (amount * 0.3);
    }
}
