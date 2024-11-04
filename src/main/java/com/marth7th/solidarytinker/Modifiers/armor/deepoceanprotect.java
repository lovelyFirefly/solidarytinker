package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.compound.icefantasy;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class deepoceanprotect extends ArmorModifier {
    public static ResourceLocation WAIT = solidarytinker.getResource("wait");

    @Override
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(WAIT);
        return null;
    }


    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && event.getEntity() != null) {
            int a = modifierlevel.getTotalArmorModifierlevel(player, solidarytinkerModifiers.DEEPOCEANPROTECT_STATIC_MODIFIER.getId());
            if (a > 0) {
                float value = player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f * a;
                float cost = Math.max(1 - value * 0.001f, 0.1f);
                event.setAmount(event.getAmount() * cost);
                List<ItemStack> armor = player.getInventory().armor;
                for (ItemStack armors : armor) {
                    if (armors.getItem() instanceof ModifiableArmorItem) {
                        ToolStack tool = ToolStack.from(armors);
                        if (tool.getPersistentData().getInt(WAIT) == 3 && player.getAbsorptionAmount() < player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f * a / 10) {
                            player.setAbsorptionAmount(player.getAbsorptionAmount() + player.getMaxHealth() * 0.5f * a);
                            tool.getPersistentData().putInt(WAIT, 1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (modifier.getLevel() > 0 && entity instanceof Player player) {
            if (tool.getPersistentData().getInt(WAIT) == 1) {
                tool.getPersistentData().putInt(WAIT, 2);
                TimerTask t = new TimerTask() {
                    @Override
                    public void run() {
                        tool.getPersistentData().putInt(WAIT, 3);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(t, 20000);
            }
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            float a = Math.round(player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f) * modifierlevel.getTotalArmorModifierlevel(player, this.getId());
            list.add(applyStyle(Component.literal(icefantasy.GetColor("当前回声点数")).append(icefantasy.GetColor(a + ""))));
            list.add(applyStyle(Component.literal(icefantasy.GetColor("当前伤害减免")).append(icefantasy.GetColor((1 - Math.max(1 - a * 0.001f, 0.1f)) * 100 + "%"))));
        }
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData = tool.getPersistentData();
        if (toolData.getInt(WAIT) == 0) {
            return Component.translatable(this.getDisplayName().getString() + "  ").withStyle(this.getDisplayName().getStyle()).append(Component.literal("已冷却完毕").withStyle(this.getDisplayName().getStyle()));
        } else
            return Component.translatable(this.getDisplayName().getString() + "  ").withStyle(this.getDisplayName().getStyle()).append(Component.literal("未冷却完毕").withStyle(this.getDisplayName().getStyle()));
    }
}