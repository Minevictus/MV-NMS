package com.proximyst.mvnms.common;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface INmsEntity {
  /**
   * Rotate an entity without teleporting it.
   *
   * @param entity The entity to rotate.
   * @param yaw Their new yaw.
   * @param pitch Their new pitch.
   */
  void rotate(@NotNull Entity entity, float yaw, float pitch);

  /**
   * Rotate a living entity with clamping in place.
   *
   * @param livingEntity The {@link LivingEntity} to rotate.
   * @param yaw Their new yaw.
   * @param pitch Their new pitch.
   * @see #rotateClamped
   */
  void rotateLivingEntity(@NotNull LivingEntity livingEntity, float yaw, float pitch);

  /**
   * Rotate an {@link Entity} with clamps for yaw and pitch in place.
   * <p>
   * This should only be needed on <i>some</i> living entities like the {@link
   * org.bukkit.entity.EnderDragon Ender Dragon}.
   *
   * @param entity The entity to rotate.
   * @param yaw Their new yaw.
   * @param pitch Their new pitch.
   * @see #rotate
   */
  default void rotateClamped(@NotNull Entity entity, float yaw, float pitch) {
    while (yaw < -180f) {
      yaw += 360f;
    }

    while (yaw >= 180f) {
      yaw -= 360.0F;
    }

    rotate(entity, yaw, pitch);
  }

  /**
   * Set the visibility of an entity.
   *
   * @param entity The entity to change the visibility of.
   * @param visibility Their new visibility.
   */
  void setInvisible(@NotNull Entity entity, boolean visibility);

  /**
   * Set the invulnerability of an entity.
   *
   * @param entity The entity to change the invulnerability of.
   * @param invulnerability Their new state of invulnerability.
   */
  void setInvulnerable(@NotNull Entity entity, boolean invulnerability);
}
