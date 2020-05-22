/**
 * MV-NMS
 * Copyright (C) 2020 Mariell Hoversholm, Nahuel Dolores
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
