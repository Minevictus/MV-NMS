package com.proximyst.mvnms.reflect;

import com.proximyst.mvnms.common.INmsEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NmsEntityReflectImplementation implements INmsEntity {
  @Override
  public void rotate(
      @NotNull Entity entity,
      float yaw,
      float pitch
  ) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public void rotateLivingEntity(
      @NotNull LivingEntity livingEntity,
      float yaw,
      float pitch
  ) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public void rotateClamped(@NotNull Entity entity, float yaw, float pitch) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public void setInvisible(@NotNull Entity entity, boolean visibility) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public void setInvulnerable(@NotNull Entity entity, boolean invulnerability) {
    throw new UnsupportedOperationException("not yet implemented");
  }
}
