package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class sandstrom extends ArmorModifier {
    public sandstrom() {
        MinecraftForge.EVENT_BUS.addListener(this::livingHurtEvent);
    }
    public boolean havenolevel() {
        return true;
    }


    private void livingHurtEvent(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity enemy = (LivingEntity) event.getSource().getEntity();
        if (modifierlevel.getsinglearmorlevel(entity, solidarytinkerModifiers.SANDSTROM_STATIC_MODIFIER.getId()) > 0) {
            if (entity.getLevel().getBiome(entity.blockPosition()).is(Biomes.DESERT) || entity.getLevel().getBiome(entity.blockPosition()).is(Biomes.BADLANDS)) {
                entity.clearFire();
                entity.setInvisible(true);
            } else if (!entity.getLevel().getBiome(entity.blockPosition()).is(Biomes.DESERT) || !entity.getLevel().getBiome(entity.blockPosition()).is(Biomes.BADLANDS)) {
                entity.setInvisible(false);
                if (entity instanceof Player player) {
                    var armor = new ItemStack[]{player.getItemBySlot(EquipmentSlot.HEAD), player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS), player.getItemBySlot(EquipmentSlot.FEET)};
                    for (ItemStack itemStack1 : armor) {
                        if (!player.getCooldowns().isOnCooldown(itemStack1.getItem())) {
                            if (enemy instanceof LivingEntity) {
                                event.setAmount(event.getAmount() * 0.5f);
                                }
                            }
                        }
                    }
                }
            }
        }
    @Override
    public void onInventoryTick(IToolStackView iToolStackView, ModifierEntry modifierEntry, Level level, LivingEntity livingEntity, int i, boolean b, boolean b1, ItemStack itemStack) {
        if(livingEntity.getLevel().getBiome(livingEntity.blockPosition()).is(Biomes.DESERT)||livingEntity.getLevel().getBiome(livingEntity.blockPosition()).is(Biomes.BADLANDS)){
            if(livingEntity instanceof Player player){
                if(!level.isClientSide && player.tickCount % 20 == 0){
                    if(RANDOM.nextInt(1000)>990){
                    ItemStack gold = new ItemStack(Items.RAW_GOLD);
                    player.getInventory().add(gold);
                    }
                }
            }
        }
    }
}
