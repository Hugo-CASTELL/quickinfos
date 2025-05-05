package dev.quickinfo.infos;

import dev.quickinfo.exceptions.CannotRenderInfoException;
import dev.quickinfo.utils.PlayerUtils;
import dev.quickinfo.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class TargetedBlockCoordinates extends Info {

    @Override
    public String getHumanReadableName() {
        return "Targeted Block's Coordinates";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null || client.world == null)
            throw new CannotRenderInfoException(this);

        BlockHitResult targetedBlock = PlayerUtils.getTargetedBlockInRange(client.player, false);

        if(targetedBlock == null)
            return StaticUtils.NONE_INFO_CALCULATED;

        BlockPos pos = targetedBlock.getBlockPos();
        return String.format("Target at %d / %d / %d", pos.getX(), pos.getY(), pos.getZ());
    }

}
