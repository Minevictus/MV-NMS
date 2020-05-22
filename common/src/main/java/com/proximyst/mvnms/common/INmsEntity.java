/**
 * MV-NMS
 * Copyright (C) 2020 Mariell Hoversholm, Nahuel Dolores
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.proximyst.mvnms.common;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Interface handling all that has to do with entities and their properties.
 */
public interface INmsEntity {
  /**
   * Rotate an entity without teleporting it.
   *
   * @param entity The entity to rotate.
   * @param yaw    Their new yaw.
   * @param pitch  Their new pitch.
   */
  void rotate(@NotNull Entity entity, float yaw, float pitch);

  /**
   * Rotate a living entity with clamping in place.
   *
   * @param livingEntity The {@link LivingEntity} to rotate.
   * @param yaw          Their new yaw.
   * @param pitch        Their new pitch.
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
   * @param yaw    Their new yaw.
   * @param pitch  Their new pitch.
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
   * @param entity     The entity to change the visibility of.
   * @param visibility Their new visibility.
   */
  void setInvisible(@NotNull Entity entity, boolean visibility);

  /**
   * Set the invulnerability of an entity.
   *
   * @param entity          The entity to change the invulnerability of.
   * @param invulnerability Their new state of invulnerability.
   */
  void setInvulnerable(@NotNull Entity entity, boolean invulnerability);
}
