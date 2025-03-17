package dev.quickinfos.infos;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.NotNull;

public class TargetedBlock implements Info {

    @Override
    public String getHumanReadableName() {
        return "Targeted Block";
    }

    @Override
    public String toHUDScreen(@NotNull MinecraftClient client) {
        if (client.player == null || client.world == null) {
            return "Unknown targeted entity";
        }

        double range = client.player.getBlockInteractionRange();
        HitResult hitResult = client.player.raycast(range, 1, false);
        if(hitResult.getType() == HitResult.Type.BLOCK) {
            String blockName = Registries.BLOCK.getId(client.world.getBlockState(((BlockHitResult) hitResult).getBlockPos()).getBlock()).toString();
            return String.format("Target %s", blockName);
        }
        else {
            return "";
        }
    }

}
