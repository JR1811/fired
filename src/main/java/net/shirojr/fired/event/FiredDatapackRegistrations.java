package net.shirojr.fired.event;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.shirojr.fired.data.pack.IgnitionTranslationDatapack;

public class FiredDatapackRegistrations {
    public static void registerServer() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new IgnitionTranslationDatapack());
    }
}
