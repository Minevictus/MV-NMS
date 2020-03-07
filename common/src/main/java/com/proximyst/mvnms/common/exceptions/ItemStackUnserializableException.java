package com.proximyst.mvnms.common.exceptions;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

public class ItemStackUnserializableException extends NmsException {
  public ItemStackUnserializableException(@Nullable Material material) {
    super(
        "The ItemStack given is unserializable (type is "
            + (material == null ? "null" : material.name())
            + ")"
    );
  }

  public ItemStackUnserializableException() {
    super("The ItemStack given is unserializable");
  }
}
