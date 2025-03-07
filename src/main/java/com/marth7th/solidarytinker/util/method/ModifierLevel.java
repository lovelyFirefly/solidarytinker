package com.marth7th.solidarytinker.util.method;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.List;


public class ModifierLevel {
    public static int getMainhandModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.MAINHAND));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.MAINHAND), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getOffhandModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.OFFHAND));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.OFFHAND), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getEachHandsTotalModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.MAINHAND));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.MAINHAND), modifierId) + ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.OFFHAND), modifierId);
                }
            }
        }
        return 0;
    }

    public static boolean EachHandsHaveModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getMainhandModifierlevel(entity, modifierId) > 0 && ModifierLevel.getOffhandModifierlevel(entity, modifierId) > 0;
    }

    public static boolean HandsHaveModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getEachHandsTotalModifierlevel(entity, modifierId) > 0;
    }

    public static int getHeadModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.HEAD));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.HEAD), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getChestModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.CHEST));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.CHEST), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getLegsModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.LEGS));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.LEGS), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getFeetsModifierlevel(LivingEntity entity, ModifierId modifierId) {
        if (entity != null) {
            if (entity instanceof Player player) {
                ToolStack toolStack = ToolStack.from(entity.getItemBySlot(EquipmentSlot.FEET));
                if (!toolStack.isBroken()) {
                    return ModifierUtil.getModifierLevel(entity.getItemBySlot(EquipmentSlot.FEET), modifierId);
                }
            }
        }
        return 0;
    }

    public static int getTotalArmorModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getHeadModifierlevel(entity, modifierId) + ModifierLevel.getChestModifierlevel(entity, modifierId) + ModifierLevel.getLegsModifierlevel(entity, modifierId) + ModifierLevel.getFeetsModifierlevel(entity, modifierId);
    }

    public static int getAllSlotModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return getTotalArmorModifierlevel(entity, modifierId) + getEachHandsTotalModifierlevel(entity, modifierId);
    }

    public static boolean EachaArmorHasModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getHeadModifierlevel(entity, modifierId) > 0 && ModifierLevel.getChestModifierlevel(entity, modifierId) > 0 && ModifierLevel.getLegsModifierlevel(entity, modifierId) > 0 && ModifierLevel.getFeetsModifierlevel(entity, modifierId) > 0;
    }

    public static boolean EquipHasModifierlevel(LivingEntity entity, ModifierId modifierId) {
        return ModifierLevel.getTotalArmorModifierlevel(entity, modifierId) > 0 || ModifierLevel.HandsHaveModifierlevel(entity, modifierId);
    }

    public static List<Modifier> getCurioModifierInstanceList(Player player, ModifierId modifier) {
        List<ItemStack> list = new ArrayList<>();
        LazyOptional<ICuriosItemHandler> handler = CuriosApi.getCuriosHelper().getCuriosHandler(player);
        if (handler.resolve().isPresent()) {
            for (ICurioStacksHandler curios : handler.resolve().get().getCurios().values()) {
                for (int i = 0; i < curios.getSlots(); ++i) {
                    ItemStack stack = curios.getStacks().getStackInSlot(i);
                    if (!stack.isEmpty() && stack.is(TinkerTags.Items.MODIFIABLE)) {
                        list.add(stack);
                    }
                }
            }
        }
        List<Modifier> modifierList = new ArrayList<>();
        for (ItemStack curio : list) {
            IToolStackView view = ToolStack.from(curio);
            Modifier ModifierInstance = view.getModifier(modifier).getModifier();
            modifierList.add(ModifierInstance);
        }
        return modifierList;
    }

}
