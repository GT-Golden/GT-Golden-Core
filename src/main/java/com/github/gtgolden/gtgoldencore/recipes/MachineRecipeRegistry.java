package com.github.gtgolden.gtgoldencore.recipes;

import net.minecraft.item.ItemInstance;

import java.util.ArrayList;
import java.util.List;

abstract public class MachineRecipeRegistry {
    public final List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(ItemInstance input, ItemInstance output) {
        recipes.add(new Recipe(input, output));
    }

    public void addRecipe(ItemInstance[] input, ItemInstance[] output) {
        recipes.add(new Recipe(input, output));
    }

    public ItemInstance[] getOutput(ItemInstance[] inputItems) {
        for (Recipe recipe : recipes) {
            if (recipe.checkRecipe(inputItems)) return recipe.getOutput();
        }

        return null;
    }
}
