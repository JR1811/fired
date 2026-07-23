package net.shirojr.fired.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.shirojr.fired.init.FiredGameRules;
import net.shirojr.fired.init.FiredTags;

public class HotItemHelper {
    private HotItemHelper() {
    }

    public static boolean hasProtection(LivingEntity entity) {
        if (entity.isFireImmune()) return true;
        if (entity instanceof PlayerEntity player && (player.isCreative() || player.isSpectator())) return true;
        if (entity.getWorld() instanceof ServerWorld serverWorld) {
            int safetyHp = serverWorld.getGameRules().getInt(FiredGameRules.HOT_ITEM_HEALTH_SAFETY);
            if (entity.getHealth() <= safetyHp) return true;
        }
        if (entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) return true;
        return entity.getMainHandStack().isIn(FiredTags.ItemTags.GLOVES) || entity.getOffHandStack().isIn(FiredTags.ItemTags.GLOVES);
    }
}
