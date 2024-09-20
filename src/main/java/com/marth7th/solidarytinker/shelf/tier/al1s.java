package com.marth7th.solidarytinker.shelf.tier;


import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class al1s implements Tier {
    public static Tier instance = new al1s();
    @Override
    public int getUses() {
        return 2147483647;
    }
    @Override
    public float getSpeed() {
        return 2147483647;
    }
    @Override
    public float getAttackDamageBonus() {
        return 2147483647;
    }
    @Override
    public int getLevel() {
        return 2147483647;
    }
    @Override
    public int getEnchantmentValue() {
        return 2147483647;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }

}
