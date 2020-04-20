package com.proximyst.mvnms.reflect;

import com.proximyst.mvnms.common.INmsPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsPlayerReflectImplementation implements INmsPlayer {
  @Override
  public void updateClientPassengers(@NotNull Player player) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public void destroyEntity(
      @NotNull Player player,
      @NotNull Entity entity
  ) {
    throw new UnsupportedOperationException("not yet implemented");
  }
}
