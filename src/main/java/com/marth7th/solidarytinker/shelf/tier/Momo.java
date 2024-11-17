package com.marth7th.solidarytinker.shelf.tier;


import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class Momo implements Tier {
    public static Tier instance = new Momo();
    @Override
    public int getUses() {
        return 12;
    }
    @Override
    public float getSpeed() {
        return 12;
    }
    @Override
    public float getAttackDamageBonus() {
        return 12;
    }
    @Override
    public int getLevel() {
        return 12;
    }
    @Override
    public int getEnchantmentValue() {
        return 12;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }

}
