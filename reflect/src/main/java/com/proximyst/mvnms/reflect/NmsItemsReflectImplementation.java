package com.proximyst.mvnms.reflect;

import com.proximyst.mvnms.common.INmsItems;
import com.proximyst.mvnms.common.exceptions.ItemStackUndeserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableNBTException;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsItemsReflectImplementation implements INmsItems {
  @Override
  public byte[] serializeItemStack(@NotNull ItemStack item)
      throws ItemStackUnserializableException, ItemStackUnserializableNBTException {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  @NotNull
  public ItemStack deserializeItemStack(byte[] serialized)
      throws ItemStackUndeserializableException {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  @NotNull
  public HoverEvent hoverItem(@NotNull ItemStack item) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  @NotNull
  public Item spawnItem(
      @NotNull Location location,
      @NotNull ItemStack itemStack
  ) {
    throw new UnsupportedOperationException("not yet implemented");
  }
}
