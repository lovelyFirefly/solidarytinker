package com.marth7th.solidarytinker.mixins;

import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import com.marth7th.solidarytinker.tools.tinkeritem.SoulGe;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal implements NeutralMob {
    protected WolfMixin(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/TamableAnimal;tick()V", shift = At.Shift.AFTER))
    private void tick(CallbackInfo ci) {
        var master = this.getOwner();
        if (master == null) return;
        if (master instanceof Player player) {
            var dogMainHandItem = this.getMainHandItem();
            if (dogMainHandItem.getItem() instanceof SoulGe soulGe) {
                if (ModifierUtil.getModifierLevel(dogMainHandItem, solidarytinkerModifiers.CRAZY_DOG_STATIC_MODIFIER.getId()) > 0) {
                    var view= ToolStack.from(dogMainHandItem);
                    int dist=Math.round(view.getStats().get(solidarytinkerToolstats.DETECTION_RANGE));
                    soulGe.attackMultipleTargets(player,view,dist,false);

                    var x = player.getX();
                    var y = player.getY();
                    var z = player.getZ();

                    List<Mob> targetedMob = new ArrayList<>();
                    List<Mob> mobList = player.level.getEntitiesOfClass(Mob.class, new AABB(x + dist, y + dist, z + dist, x - dist, y - dist, z - dist));
                    for (Mob mob : mobList) {
                        if (targetedMob.size() < 11&&mob.getPersistentData().contains("targeted")) {
                            targetedMob.add(mob);
                        }
                    }
                    for(Mob mob:targetedMob){
                        solidarytinker$drawParticleBeam(this,mob);
                    }
                }
            }
        }
    }
    @Unique
    private void solidarytinker$drawParticleBeam(LivingEntity player, LivingEntity target) {
        double d0 = target.getX() - player.getX();
        double d1 = target.getY() + (double) (target.getBbHeight() * 0.5F)
                - (player.getY() + (double) player.getEyeHeight() * 0.5D);
        double d2 = target.getZ() - player.getZ();
        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 = d0 / d3;
        d1 = d1 / d3;
        d2 = d2 / d3;
        double d4 = this.random.nextDouble();
        while (d4 < d3) {
            d4 += 1.0D;
            player.level.addParticle(ParticleTypes.SOUL, player.getX() + d0 * d4, player.getY() + d1 * d4 + (double) player.getEyeHeight() * 0.5D, player.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
        }
    }
}
