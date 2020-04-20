package com.proximyst.mvnms.common;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
