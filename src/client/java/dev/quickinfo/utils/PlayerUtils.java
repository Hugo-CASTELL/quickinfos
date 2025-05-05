package dev.quickinfo.utils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.BlockHitResult;

public class PlayerUtils {
    public static HitResult getTargetedObjectInRange(ClientPlayerEntity player, boolean includeFluids){
        return player.raycast(player.getBlockInteractionRange(), 1, includeFluids);
    }

    public static BlockHitResult getTargetedBlockInRange(ClientPlayerEntity player, boolean includeFluids){
        HitResult hitResult = getTargetedObjectInRange(player, includeFluids);
        return hitResult.getType() == HitResult.Type.BLOCK ? (BlockHitResult) hitResult : null;
    }

    public static EntityHitResult getTargetedEntityInRange(ClientPlayerEntity player){
        HitResult hitResult = getTargetedObjectInRange(player, false);
        return hitResult.getType() == HitResult.Type.ENTITY ? (EntityHitResult) hitResult : null;
    }
}
