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

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

/**
 * Thrown when an item cannot be serialized.
 */
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
