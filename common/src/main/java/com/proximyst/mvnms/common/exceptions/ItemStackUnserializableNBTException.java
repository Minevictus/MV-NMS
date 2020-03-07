package com.proximyst.mvnms.common.exceptions;

import org.jetbrains.annotations.Nullable;

public class ItemStackUnserializableNBTException extends NmsException {
  public ItemStackUnserializableNBTException(@Nullable Throwable cause) {
    super("The ItemStack given is unserializable (invalid NBT)", cause);
  }

  public ItemStackUnserializableNBTException() {
    this(null);
  }
}
