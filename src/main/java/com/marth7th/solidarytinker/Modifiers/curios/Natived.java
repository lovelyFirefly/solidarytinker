package com.marth7th.solidarytinker.Modifiers.curios;

import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import com.xiaoyue.tinkers_ingenuity.content.library.context.CurioAttributeContext;
import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.ToolUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class Natived extends XICModifier {
    private static final ResourceLocation DEATH = solidarytinker.getResource("death");
    {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::LivingDamageEvent);
    }

    private int getInv(ItemStack stack) {
        return ToolStack.from(stack).getPersistentData().getInt(DEATH);
    }

    @Override
    public int onCurioGetFortune(IToolStackView curio, SlotContext slotContext, LootContext lootContext, ItemStack stack, int fortune, int level) {
        return fortune + level * 2;
    }

    @Override
    public void addCurioAttribute(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack, CurioAttributeContext attr) {
        attr.map().put(Attributes.LUCK, new AttributeModifier(UUID.fromString("3367bbc2-547b-4b6b-9229-6cc3ff6f4bc6"), Attributes.LUCK.getDescriptionId(), 2 * level, AttributeModifier.Operation.ADDITION));
        attr.map().put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3367bbc2-542d-4b6b-9229-6cc3ff6f4bc6"), Attributes.ATTACK_SPEED.getDescriptionId(), 0.266 * level, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    private void setInv(int invTime, ItemStack stack) {
        ToolStack.from(stack).getPersistentData().putInt(DEATH, invTime);
    }

    private void LivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0) {
                    if (event.getAmount() > player.getMaxHealth() * 0.26f) {
                        event.setAmount(player.getMaxHealth() * 0.26F);
                        if (ModifierLevel.getCurioModifierInstanceList(player, this.getId()).get(0) instanceof Natived natived) {
                            if (natived.getInv(curios) == 80) {
                                natived.setInv(0, curios);
                                player.setInvulnerable(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onCurioTick(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack) {
        if (curio.getModifier(this).getModifier() instanceof Natived natived) {
            if (natived.getInv(stack) < 80) {
                natived.setInv(natived.getInv(stack) + 1, stack);
            } else if (natived.getInv(stack) == 80) {
                entity.setInvulnerable(false);
            }
        }
    }
}
