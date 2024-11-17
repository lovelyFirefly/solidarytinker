package com.marth7th.solidarytinker.Modifiers.curios;

import com.marth7th.solidarytinker.solidarytinker;
import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils.Curios;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.LootingModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.LootingContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class bha extends XICModifier implements ModifierRemovalHook, LootingModifierHook {
    public static ResourceLocation TIME = solidarytinker.getResource("time");

    {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
    }

    private void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            List<ItemStack> curio = Curios.getStacks(player);
            Level level = player.getLevel();
            BlockPos posA = player.getOnPos();
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0) {
                    int ModifierLevel = ModifierUtil.getModifierLevel(curios, this.getId());
                    int time = player.getPersistentData().getInt("ammobha");
                    if (time < 7) {
                        player.getPersistentData().putInt("ammobha", time + 1);
                    } else if (time == 7) {
                        level.playSound(null, posA, SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.PLAYERS, 1F, 1F);
                        player.getPersistentData().putInt("ammobha", time + 1);
                    } else if (time == 8) {
                        event.setAmount(event.getAmount() * 4 * ModifierLevel);
                        level.playSound(null, posA, SoundEvents.ENDER_DRAGON_HURT, SoundSource.PLAYERS, 1F, 1F);
                        player.getPersistentData().putInt("ammobha", 0);
                    }
                }
            }
        }
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(ModifierHooks.REMOVE).addHook(ModifierHooks.WEAPON_LOOTING);
    }

    public Component onRemoved(IToolStackView iToolStackView, @NotNull Modifier modifier) {
        iToolStackView.getPersistentData().remove(TIME);
        return null;
    }

    @Override
    public int updateLooting(IToolStackView iToolStackView, ModifierEntry modifierEntry, LootingContext lootingContext, int i) {
        return i + modifierEntry.getLevel() * 2;
    }
}
