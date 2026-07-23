package net.shirojr.fired.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public interface FiredGameRules {
    GameRules.Key<GameRules.BooleanRule> IGNITE_TORCHES = GameRuleRegistry.register("igniteTorches",
            GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    GameRules.Key<GameRules.BooleanRule> TORCHES_IGNITE = GameRuleRegistry.register("torchesIgnite",
            GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    GameRules.Key<GameRules.BooleanRule> BURNING_ENTITY_CAN_IGNITE = GameRuleRegistry.register("burningEntityCanIgnite",
            GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    GameRules.Key<GameRules.BooleanRule> HOT_ITEMS_IGNITE = GameRuleRegistry.register("hotItemsIgniteEntities",
            GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    GameRules.Key<GameRules.IntRule> HOT_ITEM_HEALTH_SAFETY = GameRuleRegistry.register("hotItemBurnHealthSafety",
            GameRules.Category.MISC, GameRuleFactory.createIntRule(0, 0));

    static void initialize() {
        // static initialisation
    }
}
