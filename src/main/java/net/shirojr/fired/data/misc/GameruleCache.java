package net.shirojr.fired.data.misc;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.EnumSet;

public class GameruleCache {
    public static final EnumSet<SyncedBooleanGameruleEntry> CACHED_BOOLEAN_ENTRIES = EnumSet.noneOf(SyncedBooleanGameruleEntry.class);

    public static boolean getBooleanEntry(World world, SyncedBooleanGameruleEntry entry) {
        if (world instanceof ServerWorld serverWorld) {
            return serverWorld.getGameRules().getBoolean(entry.getLinkedGamerule());
        }
        return CACHED_BOOLEAN_ENTRIES.contains(entry);
    }
}
