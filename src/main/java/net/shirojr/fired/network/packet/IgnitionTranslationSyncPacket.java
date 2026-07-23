package net.shirojr.fired.network.packet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.shirojr.fired.Fired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public record IgnitionTranslationSyncPacket(HashMap<Item, Item> itemMap,
                                            HashMap<TagKey<Item>, Item> tagMap) implements FabricPacket {
    public static final PacketType<IgnitionTranslationSyncPacket> TYPE = PacketType.create(Fired.id("ignition_translation_sync"), IgnitionTranslationSyncPacket::read);

    public static IgnitionTranslationSyncPacket getClearPacket() {
        return new IgnitionTranslationSyncPacket(new HashMap<>(), new HashMap<>());
    }

    private static IgnitionTranslationSyncPacket read(PacketByteBuf buf) {
        HashMap<Item, Item> itemMap = new HashMap<>();
        int itemMapSize = buf.readVarInt();
        for (int i = 0; i < itemMapSize; i++) {
            itemMap.put(Registries.ITEM.get(buf.readIdentifier()), Registries.ITEM.get(buf.readIdentifier()));
        }

        HashMap<TagKey<Item>, Item> tagMap = new HashMap<>();
        int tagMapSize = buf.readVarInt();
        for (int i = 0; i < tagMapSize; i++) {
            tagMap.put(TagKey.of(RegistryKeys.ITEM, buf.readIdentifier()), Registries.ITEM.get(buf.readIdentifier()));
        }

        return new IgnitionTranslationSyncPacket(itemMap, tagMap);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(itemMap.size());
        for (Map.Entry<Item, Item> entry : itemMap.entrySet()) {
            buf.writeIdentifier(Registries.ITEM.getId(entry.getKey()));
            buf.writeIdentifier(Registries.ITEM.getId(entry.getValue()));
        }

        buf.writeVarInt(tagMap.size());
        for (Map.Entry<TagKey<Item>, Item> entry : tagMap.entrySet()) {
            buf.writeIdentifier(entry.getKey().id());
            buf.writeIdentifier(Registries.ITEM.getId(entry.getValue()));
        }
    }

    public void send(Collection<ServerPlayerEntity> targets) {
        targets.forEach(player -> ServerPlayNetworking.send(player, this));
    }
}
