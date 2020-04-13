package com.proximyst.mvnms.common;

import com.proximyst.mvnms.common.exceptions.ItemStackUndeserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableNBTException;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface INmsItems {
  byte[] serializeItemStack(@NotNull ItemStack item) throws
      ItemStackUnserializableException,
      ItemStackUnserializableNBTException
      ;

  @NotNull
  ItemStack deserializeItemStack(byte[] serialized) throws
      ItemStackUndeserializableException
      ;

  @NotNull
  HoverEvent hoverItem(@NotNull ItemStack item);
}
