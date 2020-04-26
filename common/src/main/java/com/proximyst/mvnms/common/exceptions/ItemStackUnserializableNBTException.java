package com.proximyst.mvnms.common.exceptions;

import org.jetbrains.annotations.Nullable;

/**
 * Thrown when an item cannot be serialized due to its NBT being invalid.
 */
public class ItemStackUnserializableNBTException extends NmsException {
  public ItemStackUnserializableNBTException(@Nullable Throwable cause) {
    super("The ItemStack given is unserializable (invalid NBT)", cause);
  }

  public ItemStackUnserializableNBTException() {
    this(null);
  }
}
