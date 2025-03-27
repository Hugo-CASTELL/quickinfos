package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class TargetedBlockCoordinates extends Info {

    @Override
    public String getHumanReadableName() {
        return "Targeted Block's Coordinates";
    }

    @Override
    public String toHUDScreen(@NotNull MinecraftClient client) {
        if (client.player == null || client.world == null) {
            return "Unknown targeted entity";
        }

        double range = client.player.getBlockInteractionRange();
        HitResult hitResult = client.player.raycast(range, 1, false);
        if(hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
            return String.format("Target at %d / %d / %d", pos.getX(), pos.getY(), pos.getZ());
        }
        else {
            return "";
        }
    }

}
