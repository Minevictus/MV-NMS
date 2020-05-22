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
package com.proximyst.mvnms.reflect;

import com.proximyst.mvnms.common.INmsItems;
import com.proximyst.mvnms.common.exceptions.ItemStackUndeserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableNBTException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsItemsReflectImplementation implements INmsItems {
  private final MethodHandle asNmsCopyMethod;
  private final MethodHandle asBukkitCopyMethod;
  private final MethodHandle newCompound;
  private final MethodHandle compressCompoundOfItem;
  private final MethodHandle decompressCompoundOfItem;
  private final MethodHandle saveToCompound;
  private final MethodHandle loadFromCompound;

  public NmsItemsReflectImplementation() {
    try {
      var nmsNbtCompoundClass = NmsReflectCommon.getNmsThrows("NBTTagCompound");
      var nmsItemStackClass = NmsReflectCommon.getNmsThrows("ItemStack");
      var craftItemStackClass = NmsReflectCommon.getCraftBukkitThrows("inventory.CraftItemStack");
      var nmsNBTCompressedStreamToolsClass = NmsReflectCommon
          .getNmsThrows("NBTCompressedStreamTools");
      var publicLookup = MethodHandles.publicLookup();

      newCompound = publicLookup.findConstructor(
          nmsNbtCompoundClass,
          MethodType.methodType(nmsNbtCompoundClass)
      );
      asNmsCopyMethod = publicLookup.findStatic(
          craftItemStackClass,
          "asNMSCopy",
          MethodType.methodType(nmsItemStackClass, ItemStack.class)
      );
      asBukkitCopyMethod = publicLookup.findStatic(
          craftItemStackClass,
          "asBukkitCopy",
          MethodType.methodType(ItemStack.class, nmsItemStackClass)
      );
      loadFromCompound = publicLookup.findStatic(
          nmsItemStackClass,
          "a",
          MethodType.methodType(nmsItemStackClass, nmsNbtCompoundClass)
      );
      compressCompoundOfItem = publicLookup.findStatic(
          nmsNBTCompressedStreamToolsClass,
          "a",
          MethodType.methodType(void.class, nmsNbtCompoundClass, OutputStream.class)
      );
      decompressCompoundOfItem = publicLookup.findStatic(
          nmsNBTCompressedStreamToolsClass,
          "a",
          MethodType.methodType(nmsNbtCompoundClass, InputStream.class)
      );
      saveToCompound = publicLookup.findVirtual(
          nmsItemStackClass,
          "save",
          MethodType.methodType(nmsNbtCompoundClass, nmsNbtCompoundClass)
      );
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public byte[] serializeItemStack(@NotNull ItemStack item)
      throws ItemStackUnserializableException, ItemStackUnserializableNBTException {
    //noinspection ConstantConditions - sorry mate, but users are retarded
    if (item == null) {
      throw new ItemStackUnserializableException();
    } else if (item.getType() == Material.AIR) {
      throw new ItemStackUnserializableException(item.getType());
    }

    var stream = new ByteArrayOutputStream();
    try {
      var compound = newCompound.invokeExact();
      var nmsItem = asNmsCopyMethod.invokeExact(item);

      try {
        compressCompoundOfItem.invokeExact(
            saveToCompound.invokeExact(nmsItem, compound),
            (OutputStream) stream
        );
      } catch (IOException ex) {
        throw new ItemStackUnserializableNBTException(ex);
      }
    } catch (ItemStackUnserializableNBTException nmsException) {
      throw nmsException;
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }

    return stream.toByteArray();
  }

  @Override
  @NotNull
  public ItemStack deserializeItemStack(byte[] serialized)
      throws ItemStackUndeserializableException {
    if (serialized.length == 0) {
      return new ItemStack(Material.AIR);
    }

    Object compound;
    try {
      try (InputStream stream = new ByteArrayInputStream(serialized)) {
        compound = decompressCompoundOfItem.invokeExact(stream);
      } catch (IOException ex) {
        throw new ItemStackUnserializableNBTException(ex);
      }

      return (ItemStack) asBukkitCopyMethod.invokeExact(
          loadFromCompound.invokeExact(
              compound
          )
      );
    } catch (ItemStackUndeserializableException nmsException) {
      throw nmsException;
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  @NotNull
  public HoverEvent hoverItem(@NotNull ItemStack item) {
    try {
      var compound = newCompound.invokeExact();
      var nmsItem = asNmsCopyMethod.invokeExact(item);

      return new HoverEvent(
          HoverEvent.Action.SHOW_ITEM,
          new BaseComponent[]{
              new TextComponent(
                  saveToCompound.invokeExact(nmsItem, compound).toString()
              )
          }
      );
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }
}
