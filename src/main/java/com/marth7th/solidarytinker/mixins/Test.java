package com.marth7th.solidarytinker.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class Test{
    @Inject(method = {"canRide"}, at=@At("HEAD"), cancellable = true)
    public void canRide(Entity p_219462_, CallbackInfoReturnable<Boolean> cir){
    cir.setReturnValue(true);
    }
}
