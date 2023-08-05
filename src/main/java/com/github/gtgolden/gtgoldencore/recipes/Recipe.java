package com.github.gtgolden.gtgoldencore.recipes;

import com.github.gtgolden.gtgoldencore.materials.api.MaterialUtil;
import net.minecraft.item.ItemInstance;

// TODO add time, cost, etc.
public class Recipe {
    private final ItemInstance[] input;
    private final ItemInstance[] output;

    public Recipe(ItemInstance input, ItemInstance output) {
        this(new ItemInstance[]{input}, new ItemInstance[]{output});
    }

    public Recipe(ItemInstance[] input, ItemInstance[] output) {
        this.input = input;
        this.output = output;
    }

    public ItemInstance[] getInput() {
        return input;
    }

    public ItemInstance getInput(int index) {
        return index < input.length - 1 ? input[index] : null;
    }

    public ItemInstance[] getOutput() {
        return output;
    }

    public ItemInstance getOutput(int index) {
        return index < output.length - 1 ? output[index] : null;
    }

    public boolean checkRecipe(ItemInstance[] inputItems) {
        for (ItemInstance recipeInputItem : input) {
            for (ItemInstance inputItem : inputItems) {
                if (MaterialUtil.compare(recipeInputItem, inputItem)) break;
            }
            return false;
        }
        return true;
    }
}
