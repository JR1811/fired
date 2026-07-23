package net.shirojr.fired.data.gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.shirojr.fired.init.FiredItems;

import java.util.function.Consumer;

public class FiredRecipeGenerator extends FabricRecipeProvider {
    public FiredRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, FiredItems.GLOVE)
                .pattern("lll")
                .pattern("lwl")
                .pattern("lsl")
                .input('l', Items.LEATHER)
                .input('w', ItemTags.WOOL)
                .input('s', Items.STRING)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion("hasWool", conditionsFromTag(ItemTags.WOOL))
                .offerTo(exporter);
    }
}
