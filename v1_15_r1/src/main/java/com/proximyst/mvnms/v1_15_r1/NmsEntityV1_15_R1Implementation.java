package com.proximyst.mvnms.v1_15_r1;

import com.proximyst.mvnms.common.INmsEntity;
import net.minecraft.server.v1_15_R1.ChatComponentNBT;
import net.minecraft.server.v1_15_R1.EntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsEntityV1_15_R1Implementation implements INmsEntity {
  @Override
  public void rotate(@NotNull Entity entity, float yaw, float pitch) {
    net.minecraft.server.v1_15_R1.Entity handle = ((CraftEntity) entity).getHandle();
    handle.yaw = yaw;
    handle.pitch = pitch;
  }

  /**
   * This should only be needed on *some* living entities like the Ender Dragon
   */
  @Override
  public void rotateLivingEntity(@NotNull LivingEntity livingEntity, float yaw, float pitch) {
    EntityLiving handle = (EntityLiving) ((CraftEntity) livingEntity).getHandle();
    handle.yaw = yaw;

    while (yaw < -180f) {
      yaw += 360f;
    }

    while (yaw >= 180.0F) {
      yaw -= 360.0F;
    }

    handle.aK = yaw;
    handle.aL = yaw;

    handle.pitch = pitch;
  }
}
