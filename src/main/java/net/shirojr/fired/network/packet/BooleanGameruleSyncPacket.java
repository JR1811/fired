package net.shirojr.fired.network.packet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.shirojr.fired.Fired;
import net.shirojr.fired.data.misc.SyncedBooleanGameruleEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record BooleanGameruleSyncPacket(Collection<SyncedBooleanGameruleEntry> entries, boolean value) implements FabricPacket {
    public static final PacketType<BooleanGameruleSyncPacket> TYPE = PacketType.create(Fired.id("boolean_sync"), BooleanGameruleSyncPacket::read);

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    private static BooleanGameruleSyncPacket read(PacketByteBuf buf) {
        List<SyncedBooleanGameruleEntry> entries = new ArrayList<>();
        buf.readList(entryBuf -> entries.add(SyncedBooleanGameruleEntry.get(entryBuf.readString())));
        return new BooleanGameruleSyncPacket(entries, buf.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeCollection(entries, (entryBuf, entry) -> entryBuf.writeString(entry.asString()));
        buf.writeBoolean(value);
    }

    public void send(Collection<ServerPlayerEntity> targets) {
        targets.forEach(player -> ServerPlayNetworking.send(player, this));
    }
}
