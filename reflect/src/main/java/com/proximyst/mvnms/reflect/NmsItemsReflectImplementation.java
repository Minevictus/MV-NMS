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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsItemsReflectImplementation implements INmsItems {
  private final MethodHandle getWorldHandle;
  private final MethodHandle worldAddEntityMethod;
  private final MethodHandle newEntityItem;
  private final MethodHandle setInvisibleMethod;
  private final MethodHandle setInvulnerableMethod;
  private final MethodHandle setItemStackMethod;
  private final MethodHandle getBukkitEntityMethod;

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
      var craftWorldClass = NmsReflectCommon.getCraftBukkitThrows("CraftWorld");
      var craftEntityClass = NmsReflectCommon.getCraftBukkitThrows("entity.CraftEntity");
      var nmsWorldClass = NmsReflectCommon.getNmsThrows("WorldServer");
      var nmsEntityClass = NmsReflectCommon.getNmsThrows("Entity");
      var nmsEntityItemClass = NmsReflectCommon.getNmsThrows("EntityItem");
      var publicLookup = MethodHandles.publicLookup();

      getWorldHandle = publicLookup.findVirtual(
          craftWorldClass,
          "getHandle",
          MethodType.methodType(nmsWorldClass)
      );
      worldAddEntityMethod = publicLookup.findVirtual(
          nmsWorldClass,
          "addEntity",
          MethodType.methodType(boolean.class, nmsEntityClass)
      );
      newEntityItem = publicLookup.findConstructor(
          nmsEntityItemClass,
          MethodType.methodType(
              nmsEntityClass,
              nmsWorldClass,
              double.class,
              double.class,
              double.class
          )
      );
      setInvisibleMethod = publicLookup.findVirtual(
          nmsEntityClass,
          "setInvisible",
          MethodType.methodType(void.class, boolean.class)
      );
      setInvulnerableMethod = publicLookup.findVirtual(
          nmsEntityClass,
          "setInvulnerable",
          MethodType.methodType(void.class, boolean.class)
      );
      setItemStackMethod = publicLookup.findVirtual(
          nmsEntityItemClass,
          "setItemStack",
          MethodType.methodType(void.class, nmsItemStackClass)
      );
      getBukkitEntityMethod = publicLookup.findVirtual(
          nmsEntityClass,
          "getBukkitEntity",
          MethodType.methodType(craftEntityClass)
      );

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

  @Override
  @NotNull
  public Item spawnItem(
      @NotNull Location location,
      @NotNull ItemStack itemStack
  ) {
    try {
      var nmsWorld = getWorldHandle.invokeExact(location.getWorld());
      var nmsItem = asNmsCopyMethod.invokeExact(itemStack);
      var entityItem = newEntityItem.invokeExact(nmsWorld, location.getX(), location.getY(), location.getZ());
      setItemStackMethod.invokeExact(entityItem, nmsItem);
      setInvulnerableMethod.invokeExact(entityItem, true);
      setInvisibleMethod.invokeExact(entityItem, true);
      worldAddEntityMethod.invokeExact(nmsWorld, entityItem);
      return (Item) getBukkitEntityMethod.invokeExact(entityItem);
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }
}
