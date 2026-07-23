package net.shirojr.fired.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.shirojr.fired.data.misc.GameruleCache;
import net.shirojr.fired.data.misc.SyncedBooleanGameruleEntry;
import net.shirojr.fired.data.pack.IgnitionTranslationDatapack;
import net.shirojr.fired.network.packet.BooleanGameruleSyncPacket;
import net.shirojr.fired.network.packet.IgnitionTranslationSyncPacket;

import java.util.Collection;
import java.util.HashMap;

public class FiredS2CNetworking {
    static {
        ClientPlayNetworking.registerGlobalReceiver(BooleanGameruleSyncPacket.TYPE, FiredS2CNetworking::handleBooleanGameruleSync);
        ClientPlayNetworking.registerGlobalReceiver(IgnitionTranslationSyncPacket.TYPE, FiredS2CNetworking::handleIgnitionTranslationSync);
    }

    private static void handleBooleanGameruleSync(BooleanGameruleSyncPacket packet, ClientPlayerEntity player, PacketSender sender) {
        Collection<SyncedBooleanGameruleEntry> entries = packet.entries();
        boolean enabled = packet.value();

        MinecraftClient.getInstance().execute(() -> {
            if (enabled) {
                GameruleCache.CACHED_BOOLEAN_ENTRIES.addAll(entries);
            } else {
                entries.forEach(GameruleCache.CACHED_BOOLEAN_ENTRIES::remove);
            }
        });
    }

    private static void handleIgnitionTranslationSync(IgnitionTranslationSyncPacket packet, ClientPlayerEntity player, PacketSender sender) {
        HashMap<Item, Item> itemMap = packet.itemMap();
        HashMap<TagKey<Item>, Item> tagMap = packet.tagMap();
        MinecraftClient.getInstance().execute(() -> IgnitionTranslationDatapack.applyNewState(itemMap, tagMap));
    }

    public static void initialize() {
        // static initialisation
    }
}
