package net.shirojr.fired;

import net.fabricmc.api.ClientModInitializer;
import net.shirojr.fired.init.FiredEventRegistration;
import net.shirojr.fired.network.FiredS2CNetworking;

public class FiredClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FiredS2CNetworking.initialize();
        FiredEventRegistration.registerClient();
    }
}
