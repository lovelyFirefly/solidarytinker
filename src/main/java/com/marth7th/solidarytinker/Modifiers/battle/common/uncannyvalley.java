package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class uncannyvalley extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof Player player) {
            if (modifierlevel.Playerequiphaslevel(player, this.getId())) {
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();
                int a = modifier.getLevel();
                List<Mob> mobList = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 4 * a, y + 4 * a, z + 4 * a, x - 4 * a, y - 4 * a, z - 4 * a));
                List<Player> playerList = player.level.getEntitiesOfClass(Player.class, new AABB(x + 4 * a, y + 4 * a, z + 4 * a, x - 4 * a, y - 4 * a, z - 4 * a));
                for (Mob mob : mobList) {
                    if (mob != null) {
                        mob.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 10, true, true));
                    }
                    for (Player friends : playerList) {
                        if (friends != null) {
                            friends.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, false));
                        }
                    }
                }
            }
        }
    }
}
