package com.proximyst.mvnms.v1_15_r1;

import com.proximyst.mvnms.common.INmsPlayer;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutMount;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsPlayerV1_15_R1Implementation implements INmsPlayer {
    @Override
    public void updateClientPassengers(@NotNull Player player) {
        EntityPlayer handle = getPlayerHandle(player);

        PacketPlayOutMount playOutMount = new PacketPlayOutMount(handle);
        handle.playerConnection.sendPacket(playOutMount);
    }

    private EntityPlayer getPlayerHandle(Player player) {
        return ((CraftPlayer) player).getHandle();
    }
}
