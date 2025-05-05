package dev.quickinfo.infos;

import dev.quickinfo.exceptions.CannotRenderInfoException;
import dev.quickinfo.utils.StaticUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CurrentBiome extends Info {
    @Override
    public String getHumanReadableName() {
        return "Current biome";
    }

    @Override
    public String render(@NotNull MinecraftClient client) {
        if (client.player == null || client.world == null)
            throw new CannotRenderInfoException(this);

        BlockPos playerPos = client.player.getBlockPos();
        Optional<RegistryKey<Biome>> biome = client.world.getBiome(playerPos).getKey();
        return biome.map(biomeRegistryKey -> biomeRegistryKey.getValue().toString())
                .orElse(StaticUtils.NONE_INFO_CALCULATED);
    }
}
