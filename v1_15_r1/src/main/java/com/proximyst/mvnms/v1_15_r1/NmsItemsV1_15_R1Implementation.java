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
package com.proximyst.mvnms.v1_15_r1;

import com.proximyst.mvnms.common.INmsItems;
import com.proximyst.mvnms.common.exceptions.ItemStackUndeserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableException;
import com.proximyst.mvnms.common.exceptions.ItemStackUnserializableNBTException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsItemsV1_15_R1Implementation implements INmsItems {
  @Override
  public byte[] serializeItemStack(@NotNull ItemStack item) throws
      ItemStackUnserializableException, ItemStackUnserializableNBTException {
    //noinspection ConstantConditions - sorry mate, but users are retarded
    if (item == null) {
      throw new ItemStackUnserializableException();
    } else if (item.getType() == Material.AIR) {
      throw new ItemStackUnserializableException(item.getType());
    }

    var stream = new ByteArrayOutputStream();
    var compound = new NBTTagCompound();
    var nmsItem = CraftItemStack.asNMSCopy(item);

    try {
      NBTCompressedStreamTools.a(nmsItem.save(compound), stream);
    } catch (IOException e) {
      throw new ItemStackUnserializableNBTException(e);
    }

    return stream.toByteArray();
  }

  @NotNull
  @Override
  public ItemStack deserializeItemStack(byte[] serialized) throws
      ItemStackUndeserializableException {
    if (serialized.length == 0) {
      return new ItemStack(Material.AIR);
    }

    NBTTagCompound compound;
    try (InputStream stream = new ByteArrayInputStream(serialized)) {
      compound = NBTCompressedStreamTools.a(stream);
    } catch (IOException e) {
      throw new ItemStackUndeserializableException(e);
    }

    return CraftItemStack.asBukkitCopy(
        net.minecraft.server.v1_15_R1.ItemStack.a(compound)
    );
  }

  @NotNull
  @Override
  public HoverEvent hoverItem(@NotNull ItemStack item) {
    var nmsItem = CraftItemStack.asNMSCopy(item);

    var compound = new NBTTagCompound();

    return new HoverEvent(
        HoverEvent.Action.SHOW_ITEM,
        new BaseComponent[]{
            new TextComponent(
                nmsItem.save(compound).toString()
            )
        }
    );
  }
}
