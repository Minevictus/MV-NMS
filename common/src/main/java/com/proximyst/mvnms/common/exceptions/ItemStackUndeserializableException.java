package com.proximyst.mvnms.common.exceptions;

import org.jetbrains.annotations.Nullable;

/**
 * Thrown when an item cannot be deserialized due to its NBT being invalid.
 */
public class ItemStackUndeserializableException extends NmsException {
  public ItemStackUndeserializableException(@Nullable Throwable cause) {
    super("The ItemStack given is undeserializable (invalid NBT)", cause);
  }

  public ItemStackUndeserializableException() {
    this(null);
  }
}
