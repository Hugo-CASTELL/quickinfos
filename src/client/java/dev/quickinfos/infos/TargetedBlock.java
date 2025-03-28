package dev.quickinfos.infos;

import dev.quickinfos.exceptions.CannotRenderInfoException;
import dev.quickinfos.utils.PlayerUtils;
import dev.quickinfos.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.util.hit.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class TargetedBlock extends Info {

    @Override
    public String getHumanReadableName() {
        return "Targeted Block";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null || client.world == null)
            throw new CannotRenderInfoException(this);

        BlockHitResult targetedBlock = PlayerUtils.getTargetedBlockInRange(client.player, false);
        return targetedBlock != null ? String.format("Target %s", Registries.BLOCK.getId(client.world.getBlockState(targetedBlock.getBlockPos()).getBlock())) :
                                       StaticUtils.NONE_INFO_CALCULATED;
    }

}
