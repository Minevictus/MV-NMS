package com.proximyst.mvnms.common;

import org.bukkit.entity.Villager;

/**
 * Interface handling all that has to do with villagers and their properties.
 */
public interface INmsVillager {
  /**
   * Clears all gossips a given {@link Villager}.
   */
  // TODO(Proximyst): Move this to the new villager API PR'd into Paper
  void clearVillagerReputations(Villager villager);
}
