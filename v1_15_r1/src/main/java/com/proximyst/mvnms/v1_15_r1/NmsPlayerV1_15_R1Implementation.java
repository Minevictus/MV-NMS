package com.proximyst.mvnms.v1_15_r1;

import com.proximyst.mvnms.common.INmsPlayer;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_15_R1.PacketPlayOutMount;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsPlayerV1_15_R1Implementation implements INmsPlayer {
    @Override
    public void updateClientPassengers(@NotNull Player player) {
        var handle = getPlayerHandle(player);

        var playOutMount = new PacketPlayOutMount(handle);
        handle.playerConnection.sendPacket(playOutMount);
    }

    @Override
    public void destroyEntity(@NotNull Player player, @NotNull Entity entity) {
        var entityHandle = ((CraftEntity) entity).getHandle();

        var entityDestroy = new PacketPlayOutEntityDestroy(entityHandle.getId());
        getPlayerHandle(player).playerConnection.sendPacket(entityDestroy);
    }

    private EntityPlayer getPlayerHandle(Player player) {
        return ((CraftPlayer) player).getHandle();
    }
}
