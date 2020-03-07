package com.proximyst.mvnms.common;

import org.bukkit.entity.Villager;

public interface INmsVillager {
  /**
   * Clears all gossips a given {@link Villager}.
   */
  void clearVillagerReputations(Villager villager);
}
