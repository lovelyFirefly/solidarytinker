package com.marth7th.solidarytinker.Modifiers.armor;

import com.marth7th.solidarytinker.extend.superclass.ArmorModifier;
import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.util.compound.icefantasy;
import com.marth7th.solidarytinker.util.method.modifierlevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class deepoceanprotect extends ArmorModifier {
    public static ResourceLocation WAIT = solidarytinker.getResource("wait");

    @Nullable
    @Override
    public Component onRemoved(IToolStackView iToolStackView, Modifier modifier) {
        iToolStackView.getPersistentData().remove(WAIT);
        return null;
    }


    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            int a = modifierlevel.getTotalArmorModifierlevel(player, solidarytinkerModifiers.DEEPOCEANPROTECT_STATIC_MODIFIER.getId());
            if (a > 0) {
                float value = player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f * a;
                float cost=Math.max(1 - value * 0.001f, 0.1f);
                event.setAmount(event.getAmount()*cost);
            }
        }
    }

    @Override
    public float TrueDamageamount(IToolStackView armor, int level, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean isDirectDamage, LivingEntity entity, LivingEntity enemy) {
        if (entity instanceof Player player) {
            if (modifierlevel.EachaArmorHasModifierlevel(player, this.getId())) {
                ModDataNBT ToolData = ToolStack.from(player.getItemBySlot(EquipmentSlot.CHEST)).getPersistentData();
                int a = ToolData.getInt(WAIT);
                if (a == 0) {
                    if (player.getAbsorptionAmount() < player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f * level / 10) {
                        player.setAbsorptionAmount(player.getAbsorptionAmount() + player.getMaxHealth() * 0.5f * level);
                        player.setAbsorptionAmount(player.getAbsorptionAmount() + 5);
                        ToolData.putInt(WAIT, 1);
                    }
                }
                if (a == 1) {
                    TimerTask t = new TimerTask() {
                        @Override
                        public void run() {
                            ToolData.putInt(WAIT, 0);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(t, 20000);
                }
            }
        }
        return amount;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null) {
            float a = Math.round(player.totalExperience * 0.0001f + player.getMaxHealth() * 0.2f + player.getArmorValue() * 0.6f)*modifierlevel.getTotalArmorModifierlevel(player,this.getId());
            list.add(applyStyle(Component.literal(icefantasy.GetColor("当前回声点数")).append(icefantasy.GetColor(a + ""))));
            list.add(applyStyle(Component.literal(icefantasy.GetColor("当前伤害减免")).append(icefantasy.GetColor((1 - Math.max(1 - a * 0.001f, 0.1f)) * 100 + "%"))));
        }
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(WAIT)== 0) {
            return Component.translatable(this.getDisplayName().getString() + "  ").withStyle(this.getDisplayName().getStyle()).append(Component.literal("已冷却完毕").withStyle(this.getDisplayName().getStyle()));
        }
        else return Component.translatable(this.getDisplayName().getString() + "  ").withStyle(this.getDisplayName().getStyle()).append(Component.literal("未冷却完毕").withStyle(this.getDisplayName().getStyle()));
    }
}