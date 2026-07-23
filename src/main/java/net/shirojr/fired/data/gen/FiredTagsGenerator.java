package net.shirojr.fired.data.gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.shirojr.fired.init.FiredItems;
import net.shirojr.fired.init.FiredTags;

import java.util.concurrent.CompletableFuture;

public class FiredTagsGenerator {
    public static void registerAll(FabricDataGenerator.Pack pack) {
        pack.addProvider(ItemTags::new);
        pack.addProvider(BlockTags::new);
    }

    public static class ItemTags extends FabricTagProvider.ItemTagProvider {
        public ItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(FiredTags.ItemTags.GLOVES).add(FiredItems.GLOVE);
            getOrCreateTagBuilder(FiredTags.ItemTags.IGNITERS).add(Items.TORCH, Items.SOUL_TORCH);
            getOrCreateTagBuilder(FiredTags.ItemTags.HOT_IN_INVENTORY).add(Items.BLAZE_ROD);
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {
        public BlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(FiredTags.BlockTags.IGNITERS).add(Blocks.CAMPFIRE, Blocks.MAGMA_BLOCK, Blocks.LAVA, Blocks.LAVA_CAULDRON);
        }
    }
}
