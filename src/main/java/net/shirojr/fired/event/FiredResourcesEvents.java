package net.shirojr.fired.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.server.MinecraftServer;
import net.shirojr.fired.data.pack.IgnitionTranslationDatapack;

public class FiredResourcesEvents implements ServerLifecycleEvents.EndDataPackReload {
    @Override
    public void endDataPackReload(MinecraftServer server, LifecycledResourceManager resourceManager, boolean success) {
        if (!success) return;
        IgnitionTranslationDatapack.sync(PlayerLookup.all(server));
    }
}
