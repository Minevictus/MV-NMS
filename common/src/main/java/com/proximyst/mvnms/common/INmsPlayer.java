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
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Interface handling all that has to do with players and their properties.
 */
public interface INmsPlayer {
  /**
   * Update passengers to {@link Player player's} client. The client sometimes doesn't update {@link
   * Entity entities} riding them.
   *
   * @param player Player to be updated.
   */
  void updateClientPassengers(@NotNull Player player);

  /**
   * Destroy entity for {@link Player player} client.
   *
   * @param player Player to be updated.
   * @param entity Entity to destroy.
   */
  void destroyEntity(@NotNull Player player, @NotNull Entity entity);
}
