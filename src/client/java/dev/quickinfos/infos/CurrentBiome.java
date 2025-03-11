package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CurrentBiome implements Info {
    @Override
    public String toHUDScreen(@NotNull MinecraftClient client) {
        if (client.player == null || client.world == null) {
            return "Unknown biome";
        }

        BlockPos playerPos = client.player.getBlockPos();
        Optional<RegistryKey<Biome>> biome = client.world.getBiome(playerPos).getKey();

        return biome.map(biomeRegistryKey -> biomeRegistryKey.getValue().toString())
                .orElse("Unknown biome");
    }
}
