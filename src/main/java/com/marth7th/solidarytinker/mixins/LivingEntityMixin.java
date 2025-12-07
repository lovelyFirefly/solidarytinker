package com.marth7th.solidarytinker.mixins;

import com.marth7th.solidarytinker.extend.interfaces.LocateSoulgeKiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class ,priority = 10086)
public abstract class LivingEntityMixin extends Entity implements LocateSoulgeKiller {
    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Unique
    private Player solidarytinker$lastSoulgeHurtPlayer;

    @Inject(method = "hurt",at = @At(value = "HEAD"))
    private void setSoulgeKiller(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir){
        var attacker=pSource.getEntity();
        if(attacker instanceof Player player){
            solidarytinker$lastSoulgeHurtPlayer =player;
        }
    }
    @Inject(method = "setHealth",at = @At("HEAD"), cancellable = true)
    private void setHealth(float pHealth, CallbackInfo ci){
        if(this.getPersistentData().getInt("hoshino_stagnation")>0){
            ci.cancel();
        }
    }

    @Unique
    @Override
    public Player solidarytinker$getLastSoulgeHurtPlayer() {
        return solidarytinker$lastSoulgeHurtPlayer;
    }

}
