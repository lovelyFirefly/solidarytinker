package com.marth7th.solidarytinker.register;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrownTrident;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerEntity {
    public solidarytinkerEntity() {
    }

    public static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(MOD_ID);

    private static <T extends Entity> EntityType<T> register(String string, EntityType.Builder<T> p_20636_) {
        return Registry.register(Registry.ENTITY_TYPE, string, p_20636_.build(string));
    }

    public static final EntityType<ThrownTrident> TRIDENT = register("trident", EntityType.Builder.<ThrownTrident>of(ThrownTrident::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
}
