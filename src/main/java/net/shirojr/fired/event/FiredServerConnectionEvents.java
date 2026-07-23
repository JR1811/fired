package net.shirojr.fired.event;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.shirojr.fired.data.misc.SyncedBooleanGameruleEntry;
import net.shirojr.fired.data.pack.IgnitionTranslationDatapack;
import net.shirojr.fired.network.packet.BooleanGameruleSyncPacket;

import java.util.EnumSet;
import java.util.Set;

public class FiredServerConnectionEvents implements ServerPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player = handler.player;
        ServerWorld serverWorld = player.getServerWorld();
        EnumSet<SyncedBooleanGameruleEntry> enabledEntries = EnumSet.noneOf(SyncedBooleanGameruleEntry.class);
        for (SyncedBooleanGameruleEntry entry : SyncedBooleanGameruleEntry.values()) {
            if (serverWorld.getGameRules().getBoolean(entry.getLinkedGamerule())) {
                enabledEntries.add(entry);
            }
        }
        new BooleanGameruleSyncPacket(enabledEntries, true).send(Set.of(player));

        IgnitionTranslationDatapack.sync(Set.of(player));
    }
}
