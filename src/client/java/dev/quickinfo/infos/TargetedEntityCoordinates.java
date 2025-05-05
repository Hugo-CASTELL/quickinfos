package dev.quickinfo.infos;

import dev.quickinfo.exceptions.CannotRenderInfoException;
import dev.quickinfo.utils.PlayerUtils;
import dev.quickinfo.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class TargetedEntityCoordinates extends Info {

    @Override
    public String getHumanReadableName() {
        return "Targeted Entity's Coordinates";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null)
            throw new CannotRenderInfoException(this);

        EntityHitResult targetedEntity = PlayerUtils.getTargetedEntityInRange(client.player);

        if(targetedEntity == null)
            return StaticUtils.NONE_INFO_CALCULATED;

        Vec3d pos = targetedEntity.getPos();
        return String.format("Target at %f / %f / %f", pos.getX(), pos.getY(), pos.getZ());
    }

}
