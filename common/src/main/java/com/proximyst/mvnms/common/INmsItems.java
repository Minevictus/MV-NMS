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
package com.proximyst.mvnms.common;

import com.proximyst.mvnms.common.exceptions.ItemStackUndeserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableNBTException;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Interface handling all that has to do with items and their properties.
 */
public interface INmsItems {
  /**
   * Serialize the given {@link ItemStack} to a byte array.
   *
   * @param item The item to serialize.
   * @return The serialized item.
   * @throws ItemStackUnserializableException    Thrown if the item cannot be serialized.
   * @throws ItemStackUnserializableNBTException Thrown if the item cannot be serialized due to its
   *                                             NBT being invalid.
   */
  byte[] serializeItemStack(@NotNull ItemStack item) throws
      ItemStackUnserializableException,
      ItemStackUnserializableNBTException
  ;

  /**
   * Deserialize the given byte array to an {@link ItemStack}.
   *
   * @param serialized The serialized byte array to deserialize.
   * @return The deserialized item.
   * @throws ItemStackUndeserializableException Thrown if the item cannot be deserialized due to its
   *                                            NBT being invalid.
   */
  @NotNull
  ItemStack deserializeItemStack(byte[] serialized) throws
      ItemStackUndeserializableException
  ;

  /**
   * Creates a {@link HoverEvent} showing the {@link ItemStack} given.
   *
   * @param item The item to show when hovering over text.
   * @return A {@link HoverEvent} showing off the {@link ItemStack} given.
   */
  @NotNull
  HoverEvent hoverItem(@NotNull ItemStack item);
}
