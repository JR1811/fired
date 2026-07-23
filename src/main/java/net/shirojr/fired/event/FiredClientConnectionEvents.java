package net.shirojr.fired.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.shirojr.fired.data.misc.GameruleCache;
import net.shirojr.fired.data.pack.IgnitionTranslationDatapack;

public class FiredClientConnectionEvents implements ClientPlayConnectionEvents.Disconnect {
    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        GameruleCache.CACHED_BOOLEAN_ENTRIES.clear();
        IgnitionTranslationDatapack.clear();
    }
}
