package net.shirojr.fired.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.shirojr.fired.Fired;
import net.shirojr.fired.item.GloveItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public interface FiredItems {
    List<Item> ALL = new ArrayList<>();

    GloveItem GLOVE = register("glove", new GloveItem(new Item.Settings().maxCount(1)));

    @SuppressWarnings("SameParameterValue")
    private static <T extends Item> T register(String name, T entry) {
        T registeredEntry = Registry.register(Registries.ITEM, Fired.id(name), entry);
        ALL.add(registeredEntry);
        return registeredEntry;
    }

    static void initialize() {
        // static initialisation
    }
}
