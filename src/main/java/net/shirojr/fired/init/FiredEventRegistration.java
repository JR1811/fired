package net.shirojr.fired.init;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.shirojr.fired.event.FiredClientConnectionEvents;
import net.shirojr.fired.event.FiredResourcesEvents;
import net.shirojr.fired.event.FiredServerConnectionEvents;

public class FiredEventRegistration {
    private static final FiredServerConnectionEvents CONNECTION_EVENTS = new FiredServerConnectionEvents();
    private static final FiredResourcesEvents RESOURCES_EVENTS = new FiredResourcesEvents();


    public static void registerCommon() {
        ServerPlayConnectionEvents.JOIN.register(CONNECTION_EVENTS);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(RESOURCES_EVENTS);
    }

    public static void registerClient() {
        FiredClientConnectionEvents clientConnectionEvents = new FiredClientConnectionEvents();

        ClientPlayConnectionEvents.DISCONNECT.register(clientConnectionEvents);
    }
}
