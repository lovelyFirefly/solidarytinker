package com.marth7th.solidarytinker.shelf.tier;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class overlord implements Tier {
    public static Tier instance = new overlord();
    @Override
    public int getUses() {
        return 34;
    }

    @Override
    public float getSpeed() {
        return 34;
    }

    @Override
    public float getAttackDamageBonus() {
        return 34;
    }

    @Override
    public int getLevel() {
        return 34;
    }

    @Override
    public int getEnchantmentValue() {
        return 34;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }
}
