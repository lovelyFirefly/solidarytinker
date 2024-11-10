package com.marth7th.solidarytinker.Modifiers.curios;

import com.xiaoyue.tinkers_ingenuity.content.library.context.CurioAttributeContext;
import com.xiaoyue.tinkers_ingenuity.generic.XICModifier;
import com.xiaoyue.tinkers_ingenuity.utils.item.ToolUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class natived extends XICModifier {
    @Override
    public int onCurioGetFortune(IToolStackView curio, SlotContext slotContext, LootContext lootContext, ItemStack stack, int fortune, int level) {
        return fortune + level * 2;
    }

    @Override
    public void addCurioAttribute(IToolStackView curio, SlotContext context, LivingEntity entity, int level, ItemStack stack, CurioAttributeContext attr) {
        attr.map().put(Attributes.LUCK, new AttributeModifier(UUID.fromString("3367bbc2-547b-4b6b-9229-6cc3ff6f4bc6"), Attributes.LUCK.getDescriptionId(), 2 * level, AttributeModifier.Operation.ADDITION));
        attr.map().put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3367bbc2-542d-4b6b-9229-6cc3ff6f4bc6"), Attributes.ATTACK_SPEED.getDescriptionId(), 0.266 * level, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurtEvent);
    }

    private void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> curio = ToolUtils.Curios.getStacks(player);
            for (ItemStack curios : curio) {
                if (ModifierUtil.getModifierLevel(curios, this.getId()) > 0) {
                    if (event.getAmount() > player.getMaxHealth() * 0.26f) {
                        event.setAmount(player.getMaxHealth() * 0.26F);
                        player.invulnerableTime = 100;
                    }
                }
            }
        }
    }
}
