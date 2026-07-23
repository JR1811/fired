package net.shirojr.fired.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.shirojr.fired.data.misc.GameruleCache;
import net.shirojr.fired.data.misc.SyncedBooleanGameruleEntry;
import net.shirojr.fired.data.pack.IgnitionTranslationDatapack;
import net.shirojr.fired.init.FiredGameRules;
import net.shirojr.fired.init.FiredTags;
import net.shirojr.fired.mixin.access.BucketItemAccess;
import net.shirojr.fired.util.HotItemHelper;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements FabricItemStack {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void igniteFromUser(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!GameruleCache.getBooleanEntry(world, SyncedBooleanGameruleEntry.get(FiredGameRules.BURNING_ENTITY_CAN_IGNITE)))
            return;
        if (!user.isOnFire()) return;
        ItemStack stack = user.getStackInHand(hand);
        if (!IgnitionTranslationDatapack.canIgnite(stack)) return;
        ItemStack ignitedStack = IgnitionTranslationDatapack.translateIgnition(user, stack);
        if (ignitedStack == null) return;
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 2f, 1f);
        }
        cir.setReturnValue(TypedActionResult.success(ignitedStack, world.isClient()));
    }

    @Debug(export = true)
    @Inject(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;"
            ),
            cancellable = true
    )
    private void igniteFromBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, @Local CachedBlockPosition cachedBlockPosition) {
        BlockState blockState = cachedBlockPosition.getBlockState();
        if (!blockState.isIn(FiredTags.BlockTags.IGNITERS)) return;
        if (blockState.contains(Properties.LIT) && !blockState.get(Properties.LIT)) return;
        PlayerEntity player = context.getPlayer();
        if (player == null) return;
        ItemStack stack = player.getStackInHand(context.getHand());
        if (!IgnitionTranslationDatapack.canIgnite(stack)) return;
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            IgnitionTranslationDatapack.translateIgnition(player, stack);
            serverWorld.playSound(null, context.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 2f, 1f);
        }
        cir.setReturnValue(ActionResult.SUCCESS);
    }

    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void igniteFromEntity(PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!GameruleCache.getBooleanEntry(user.getWorld(), SyncedBooleanGameruleEntry.get(FiredGameRules.BURNING_ENTITY_CAN_IGNITE)))
            return;
        ItemStack stack = user.getStackInHand(hand);
        if (!IgnitionTranslationDatapack.canIgnite(stack)) return;
        if (user.getWorld() instanceof ServerWorld serverWorld) {
            IgnitionTranslationDatapack.translateIgnition(user, stack);
            serverWorld.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 2f, 1f);
        }
        cir.setReturnValue(ActionResult.SUCCESS);
    }

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void igniteHotItemHolder(World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (!(world instanceof ServerWorld)) return;
        if (!GameruleCache.getBooleanEntry(world, SyncedBooleanGameruleEntry.get(FiredGameRules.HOT_ITEMS_IGNITE)))
            return;
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (HotItemHelper.hasProtection(livingEntity)) return;
        ItemStack stack = (ItemStack) (Object) this;
        boolean isHot = stack.isIn(FiredTags.ItemTags.HOT_IN_INVENTORY) ||
                (stack.getItem() instanceof BucketItemAccess bucketAccess && bucketAccess.getFluid().equals(Fluids.LAVA));
        if (isHot) {
            livingEntity.setOnFireFor(5);
        }
    }
}
