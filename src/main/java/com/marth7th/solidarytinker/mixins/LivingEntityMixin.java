package com.marth7th.solidarytinker.mixins;

import com.marth7th.solidarytinker.Modifiers.armor.AbsoluteJustice;
import com.marth7th.solidarytinker.extend.interfaces.LocateSoulgeKiller;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mixin(value = LivingEntity.class ,priority = 10086)
public abstract class LivingEntityMixin extends Entity implements LocateSoulgeKiller {
    @Shadow @Final private static EntityDataAccessor<Float> DATA_HEALTH_ID;

    @Shadow public abstract float getMaxHealth();

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
        LivingEntity living=(LivingEntity)(Object) this;
        if(living instanceof Player player){
            if(pHealth<=0){
                if (ModifierLevel.getTotalArmorModifierlevel(player, solidarytinkerModifiers.ABSOLUTEJUSTICE_STATIC_MODIFIER.getId()) > 0) {
                    int stagnationTime = AbsoluteJustice.getHoshinoStagnationTime(player);
                    if (stagnationTime > 0) return;
                    for (ItemStack armor : player.getInventory().armor) {
                        var view = ToolStack.from(armor);
                        int stagnationCD = AbsoluteJustice.getHoshinoStagnationWaitTime(view);
                        if (view.getModifierLevel(solidarytinkerModifiers.ABSOLUTEJUSTICE_STATIC_MODIFIER.getId()) == 0)
                            continue;
                        if (stagnationCD > 0) continue;
                        this.entityData.set(DATA_HEALTH_ID, Mth.clamp(1, 0.0F, this.getMaxHealth()));
                        AbsoluteJustice.setHoshinoStagnationWaitTime(view, 369);
                        AbsoluteJustice.setHoshinoStagnationTime(player, 20);
                        ci.cancel();
                        break;
                    }
                }
            }

            if(this.getPersistentData().getInt("hoshino_stagnation")>0){
                ci.cancel();
            }
        }


    }
    @Inject(method = "knockback",at =@At("HEAD"), cancellable = true)
    private void prevent(double pStrength, double pX, double pZ, CallbackInfo ci){
        if(solidarytinker$lastSoulgeHurtPlayer!=null){
            ci.cancel();
        }
    }

    @Unique
    @Override
    public Player solidarytinker$getLastSoulgeHurtPlayer() {
        return solidarytinker$lastSoulgeHurtPlayer;
    }

}
