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
