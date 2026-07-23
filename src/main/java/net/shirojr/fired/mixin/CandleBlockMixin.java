package net.shirojr.fired.mixin;

import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.shirojr.fired.data.misc.GameruleCache;
import net.shirojr.fired.data.misc.SyncedBooleanGameruleEntry;
import net.shirojr.fired.data.pack.IgnitionTranslationDatapack;
import net.shirojr.fired.init.FiredGameRules;
import net.shirojr.fired.init.FiredTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CandleBlock.class)
public abstract class CandleBlockMixin extends AbstractCandleBlock implements Waterloggable {
    @Shadow
    @Final
    public static BooleanProperty LIT;

    private CandleBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void igniteCandle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                              BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (GameruleCache.getBooleanEntry(world, SyncedBooleanGameruleEntry.get(FiredGameRules.TORCHES_IGNITE))) {
            if (!state.get(LIT) && stack.isIn(FiredTags.ItemTags.IGNITERS)) {
                stack.decrement(1);
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.setBlockState(pos, state.with(LIT, true));
                    serverWorld.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 2f, 1f);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }
        if (GameruleCache.getBooleanEntry(world, SyncedBooleanGameruleEntry.get(FiredGameRules.IGNITE_TORCHES))) {
            if (state.get(LIT) && player instanceof ServerPlayerEntity serverPlayer) {
                if (IgnitionTranslationDatapack.canIgnite(stack)) {
                    if (world instanceof ServerWorld serverWorld) {
                        IgnitionTranslationDatapack.translateIgnition(serverPlayer, stack);
                        serverWorld.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 2f, 1f);
                    }
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}
