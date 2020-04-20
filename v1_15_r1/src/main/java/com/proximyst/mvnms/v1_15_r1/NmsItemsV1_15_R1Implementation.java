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
import net.minecraft.server.v1_15_R1.EntityItem;
import net.minecraft.server.v1_15_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Item;
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

  @NotNull
  @Override
  public Item spawnItem(@NotNull Location location, @NotNull ItemStack itemStack) {
    var worldServer = ((CraftWorld) location.getWorld()).getHandle();

    var nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

    var entityItem = new EntityItem(worldServer, location.getX(), location.getY(), location.getZ());
    entityItem.setItemStack(nmsItemStack);

    entityItem.setInvisible(true);
    entityItem.setInvulnerable(true);

    worldServer.addEntity(entityItem);
    return (Item) entityItem.getBukkitEntity();
  }
}
