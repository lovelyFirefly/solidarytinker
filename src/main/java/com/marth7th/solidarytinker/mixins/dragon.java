package com.marth7th.solidarytinker.mixins;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragon.class)
public abstract class dragon {
    @Inject(method = {"canRide"}, at=@At("HEAD"), cancellable = true)
    public void canRide(Entity p_219462_, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }
    @Inject(method = {"getHurtSound"}, at=@At("HEAD"), cancellable = true)
    public void wolfsound(DamageSource p_31154_, CallbackInfoReturnable<SoundEvent> cir){
        cir.setReturnValue(SoundEvents.CAT_HURT);
    }
    @Inject(method = {"getAmbientSound"}, at=@At("RETURN"), cancellable = true)
    public void catsound(CallbackInfoReturnable<SoundEvent> cir){
        cir.setReturnValue(SoundEvents.CAT_AMBIENT);
    }
}
