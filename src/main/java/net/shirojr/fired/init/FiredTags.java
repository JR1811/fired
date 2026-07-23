package net.shirojr.fired.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.shirojr.fired.Fired;

import java.util.ArrayList;
import java.util.List;

public interface FiredTags {
    static void initialize() {
        // static initialisation
        ItemTags.initialize();
        BlockTags.initialize();
    }

    List<TagKey<?>> ALL = new ArrayList<>();

    interface ItemTags {
        List<TagKey<Item>> ALL_ITEM_TAGS = new ArrayList<>();

        TagKey<Item> GLOVES = createTag("gloves");
        TagKey<Item> IGNITERS = createTag("igniters");
        TagKey<Item> HOT_IN_INVENTORY = createTag("hot_in_inventory");

        private static TagKey<Item> createTag(String name) {
            TagKey<Item> tagKey = TagKey.of(RegistryKeys.ITEM, Fired.id(name));
            ALL_ITEM_TAGS.add(tagKey);
            ALL.add(tagKey);
            return tagKey;
        }

        static void initialize() {
            // static initialisation
        }
    }

    interface BlockTags {
        List<TagKey<Block>> ALL_BLOCK_TAGS = new ArrayList<>();

        TagKey<Block> IGNITERS = createTag("igniters");

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Block> createTag(String name) {
            TagKey<Block> tagKey = TagKey.of(RegistryKeys.BLOCK, Fired.id(name));
            ALL_BLOCK_TAGS.add(tagKey);
            ALL.add(tagKey);
            return tagKey;
        }

        static void initialize() {
            // static initialisation
        }
    }
}
