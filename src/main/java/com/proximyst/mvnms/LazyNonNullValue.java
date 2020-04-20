package com.proximyst.mvnms;

import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LazyNonNullValue<T> {
  private final Object synchronizedLock = new Object();

  @NotNull
  private final Supplier<T> createValue;
  @Nullable
  private T value = null;

  LazyNonNullValue(@NotNull Supplier<T> createValue) {
    Objects.requireNonNull(createValue, "createValue cannot be null");

    this.createValue = createValue;
  }

  void eager() {
    if (value == null) {
      synchronized (synchronizedLock) {
        if (value == null) {
          value = createValue.get();
        }
      }
    }
  }

  @NotNull
  T getValue() {
    if (value == null) {
      synchronized (synchronizedLock) {
        if (value == null) {
          return value = createValue.get();
        }
      }
    }

    return value;
  }
}
