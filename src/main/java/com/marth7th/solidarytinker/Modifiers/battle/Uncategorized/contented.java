package com.marth7th.solidarytinker.Modifiers.battle.Uncategorized;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class contented extends BattleModifier {
    private static final ResourceLocation Coin = solidarytinker.getResource("coin");

    @Override
    public @Nullable Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(Coin);
        return null;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity instanceof Player player) {
            if (player.walkDist >= 100) {
                player.walkDist = 0;
                int count = tool.getPersistentData().getInt(Coin);
                tool.getPersistentData().putInt(Coin, Math.min(modifier.getLevel() * 100, 10 + count));
            }
        }
    }

    @Override
    public void LivingAttackEvent(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ModifierLevel.getMainhandModifierlevel(player, this.getId()) > 0) {
                if (player.getMainHandItem().getItem() instanceof ModifiableItem modifiableItem) {
                    IToolStackView toolStackView = ToolStack.from(player.getMainHandItem());
                    int a = toolStackView.getPersistentData().getInt(Coin);
                    if (a > 0) {
                        event.getSource().bypassArmor();
                        toolStackView.getPersistentData().putInt(Coin, a - 1);
                    }
                }
            }
        }
    }
}
