package net.shirojr.fired.data.pack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.shirojr.fired.Fired;
import net.shirojr.fired.network.packet.IgnitionTranslationSyncPacket;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class IgnitionTranslationDatapack implements SimpleSynchronousResourceReloadListener {
    private static final HashMap<Item, Item> ITEM_IGNITER_TRANSLATIONS = new HashMap<>();
    private static final HashMap<TagKey<Item>, Item> TAG_IGNITER_TRANSLATIONS = new HashMap<>();
    private static final String DIRECTORY = "ignition";

    @Nullable
    private static Item getIgnited(ItemStack toBeIgnited) {
        if (toBeIgnited.isEmpty()) return null;
        Item ignited = ITEM_IGNITER_TRANSLATIONS.get(toBeIgnited.getItem());
        if (ignited == null) {
            for (var tagEntry : TAG_IGNITER_TRANSLATIONS.entrySet()) {
                if (toBeIgnited.isIn(tagEntry.getKey())) {
                    ignited = tagEntry.getValue();
                    break;
                }
            }
        }
        return ignited;
    }

    @Nullable
    public static ItemStack translateIgnition(PlayerEntity player, ItemStack toBeIgnited) {
        Item ignited = getIgnited(toBeIgnited);
        if (ignited == null) return null;
        toBeIgnited.decrement(1);
        ItemStack ignitedStack = ignited.getDefaultStack().copyWithCount(1);
        player.getInventory().offerOrDrop(ignitedStack);
        return ignitedStack;
    }

    public static boolean canIgnite(ItemStack stack) {
        return getIgnited(stack) != null;
    }

    public static void sync(Collection<ServerPlayerEntity> targets) {
        new IgnitionTranslationSyncPacket(
                new HashMap<>(ITEM_IGNITER_TRANSLATIONS),
                new HashMap<>(TAG_IGNITER_TRANSLATIONS)
        ).send(targets);
    }

    public static void applyNewState(HashMap<Item, Item> itemMap, HashMap<TagKey<Item>, Item> tagMap) {
        clear();
        ITEM_IGNITER_TRANSLATIONS.putAll(itemMap);
        TAG_IGNITER_TRANSLATIONS.putAll(tagMap);
    }

    public static void clear() {
        ITEM_IGNITER_TRANSLATIONS.clear();
        TAG_IGNITER_TRANSLATIONS.clear();
    }

    @Override
    public Identifier getFabricId() {
        return Fired.id(DIRECTORY);
    }

    @Override
    public void reload(ResourceManager manager) {
        clear();
        var files = manager.findResources(DIRECTORY, filePath -> filePath.getPath().endsWith(".json") && filePath.getPath().contains(DIRECTORY));
        for (var fileEntry : files.entrySet()) {
            Identifier fileId = fileEntry.getKey();
            if (fileId.equals(Fired.id("ignition/examples.json"))) {
                continue;
            }
            try {
                InputStream inputStream = fileEntry.getValue().getInputStream();
                JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                for (var jsonEntry : json.entrySet()) {
                    Identifier ignitedId = Identifier.tryParse(jsonEntry.getValue().getAsString());
                    if (!Registries.ITEM.containsId(ignitedId)) {
                        throw new NoSuchElementException("No such ignited Item Entry in Registry: " + ignitedId);
                    }
                    Item ignitedItem = Registries.ITEM.get(ignitedId);

                    String key = jsonEntry.getKey();
                    if (key.startsWith("#")) {
                        TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, Identifier.tryParse(key.substring(1)));
                        TAG_IGNITER_TRANSLATIONS.put(tag, ignitedItem);
                    } else {
                        Identifier ignitionId = Identifier.tryParse(key);
                        if (!Registries.ITEM.containsId(ignitionId)) {
                            throw new NoSuchElementException("No such ignition Item Entry in Registry: " + ignitionId);
                        }
                        Item ignitionItem = Registries.ITEM.get(ignitionId);
                        ITEM_IGNITER_TRANSLATIONS.put(ignitionItem, ignitedItem);
                    }
                }

            } catch (Exception e) {
                Fired.LOGGER.error("Issue found in {} file and was not loaded", fileId, e);
            }
        }
    }
}
