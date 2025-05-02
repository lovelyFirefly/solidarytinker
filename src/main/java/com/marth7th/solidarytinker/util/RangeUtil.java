package com.marth7th.solidarytinker.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RangeUtil {
    public static List<Mob> mobList(double range, LivingEntity lv){
        double x=lv.getX();
        double y=lv.getY();
        double z=lv.getZ();
        return lv.level.getEntitiesOfClass(Mob.class, new AABB(x + range, y + range, z + range, x - range, y - range, z - range));
    }
    public static List<Player> playerList(double range, LivingEntity lv){
        double x=lv.getX();
        double y=lv.getY();
        double z=lv.getZ();
        return lv.level.getEntitiesOfClass(Player.class, new AABB(x + range, y + range, z + range, x - range, y - range, z - range));
    }
}
