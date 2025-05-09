package dev.quickinfos.infos;

import dev.quickinfos.exceptions.CannotRenderInfoException;
import dev.quickinfos.utils.PlayerUtils;
import dev.quickinfos.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class TargetedEntity extends Info {

    @Override
    public String getHumanReadableName() {
        return "Targeted Entity";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null || client.world == null)
            throw new CannotRenderInfoException(this);

        EntityHitResult targetedEntity = PlayerUtils.getTargetedEntityInRange(client.player);
        return targetedEntity != null ? String.format("Target %s", targetedEntity.getEntity().getName()) :
                                        StaticUtils.NONE_INFO_CALCULATED;
    }

}
