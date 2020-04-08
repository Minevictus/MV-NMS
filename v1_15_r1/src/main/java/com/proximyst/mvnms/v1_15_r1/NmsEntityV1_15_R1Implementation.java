package com.proximyst.mvnms.v1_15_r1;

import com.proximyst.mvnms.common.INmsEntity;
import net.minecraft.server.v1_15_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NmsEntityV1_15_R1Implementation implements INmsEntity {
  @Override
  public void rotate(@NotNull Entity entity, float yaw, float pitch) {
    net.minecraft.server.v1_15_R1.Entity handle = ((CraftEntity) entity).getHandle();
    handle.yaw = yaw;
    handle.pitch = pitch;
  }

  @Override
  public void rotateClamped(@NotNull Entity entity, float yaw, float pitch) {
    while (yaw < -180f) {
      yaw += 360f;
    }

    while (yaw >= 180.0F) {
      yaw -= 360.0F;
    }

    rotate(entity, yaw, pitch);
  }

  @Override
  public void rotateLivingEntity(@NotNull LivingEntity livingEntity, float yaw, float pitch) {
    rotateClamped(livingEntity, yaw, pitch);
    EntityLiving handle = ((CraftLivingEntity) livingEntity).getHandle();

    handle.aK = handle.yaw;
    handle.aL = handle.yaw;
  }

  @Override
  public void setInvisible(@NotNull Entity entity, boolean visibility) {
    net.minecraft.server.v1_15_R1.Entity handle = ((CraftEntity) entity).getHandle();
    handle.setInvisible(visibility);
  }

  @Override
  public void setInvulnerable(@NotNull Entity entity, boolean invulnerability) {
    net.minecraft.server.v1_15_R1.Entity handle = ((CraftEntity) entity).getHandle();
    handle.setInvulnerable(true);
  }
}
