package com.proximyst.mvnms.common.exceptions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemStackUndeserializableException extends NmsException {
  public ItemStackUndeserializableException(@Nullable Throwable cause) {
    super("The ItemStack given is undeserializable (invalid NBT)", cause);
  }

  public ItemStackUndeserializableException() {
    this(null);
  }
}
