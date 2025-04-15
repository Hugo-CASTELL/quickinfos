package dev.quickinfos.infos;

import dev.quickinfos.exceptions.CannotRenderInfoException;
import dev.quickinfos.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class Ping extends Info {

    @Override
    public String getHumanReadableName() {
        return "Ping";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.getNetworkHandler() == null)
            return StaticUtils.NONE_INFO_CALCULATED;

        if (client.player == null)
            throw new CannotRenderInfoException(this);

        try {
            UUID uuid = client.player.getUuid();
            return String.format("%dms", Objects.requireNonNull(client.getNetworkHandler().getPlayerListEntry(uuid)).getLatency());
        }
        catch (Throwable e) {
            throw new CannotRenderInfoException(this);
        }
    }

}
