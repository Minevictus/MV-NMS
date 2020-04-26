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
