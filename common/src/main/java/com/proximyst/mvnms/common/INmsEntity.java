package com.proximyst.mvnms.common;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface INmsEntity {
  /**
   * Rotate the entity without teleporting it
   * @param entity - entity
   * @param yaw - new yaw
   * @param pitch - new pitch
   */
  void rotate(@NotNull Entity entity, float yaw, float pitch);

  /**
   * Rotate a living entity
   * @param livingEntity - Living entity
   * @param yaw - new yaw
   * @param pitch - new pitch
   */
  void rotateLivingEntity(@NotNull LivingEntity livingEntity, float yaw, float pitch);

  /**
   * Bukkit does not let you make some entities invisible
   * @param entity - entity to apply effect
   * @param visibility - visibility value
   */
  void setInvisible(@NotNull Entity entity, boolean visibility);

  /**
   * Bukkit does not let you make some entities invulnerable
   * @param entity - entity to apply effect
   * @param invulnerability - invulnerability value
   */
  void setInvulnerable(@NotNull Entity entity, boolean invulnerability);
}
