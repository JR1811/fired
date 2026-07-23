package net.shirojr.fired.data.misc;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.GameRules;
import net.shirojr.fired.init.FiredGameRules;

import java.util.Locale;
import java.util.NoSuchElementException;

public enum SyncedBooleanGameruleEntry implements StringIdentifiable {
    IGNITE_TORCHES(FiredGameRules.IGNITE_TORCHES),
    TORCHES_IGNITE(FiredGameRules.TORCHES_IGNITE),
    BURNING_ENTITY_CAN_IGNITE(FiredGameRules.BURNING_ENTITY_CAN_IGNITE),
    HOT_ITEMS_IGNITE(FiredGameRules.HOT_ITEMS_IGNITE);

    private final GameRules.Key<GameRules.BooleanRule> linkedGamerule;

    SyncedBooleanGameruleEntry(GameRules.Key<GameRules.BooleanRule> linkedGamerule) {
        this.linkedGamerule = linkedGamerule;
    }

    public GameRules.Key<GameRules.BooleanRule> getLinkedGamerule() {
        return linkedGamerule;
    }

    @Override
    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static SyncedBooleanGameruleEntry get(String name) {
        for (SyncedBooleanGameruleEntry entry : SyncedBooleanGameruleEntry.values()) {
            if (entry.asString().equals(name)) return entry;
        }
        throw new NoSuchElementException();
    }

    public static SyncedBooleanGameruleEntry get(GameRules.Key<GameRules.BooleanRule> gamerule) {
        for (SyncedBooleanGameruleEntry entry : SyncedBooleanGameruleEntry.values()) {
            if (entry.getLinkedGamerule().equals(gamerule)) return entry;
        }
        throw new NoSuchElementException();
    }
}
