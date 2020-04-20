package com.proximyst.mvnms.reflect;

import java.util.Optional;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class NmsReflectCommon {
  private NmsReflectCommon() throws IllegalAccessException {
    throw new IllegalAccessException(getClass().getSimpleName() + " cannot be instantiated");
  }

  public static final String PACKAGE_NMS = "net.minecraft.server";
  public static final String PACKAGE_CRAFTBUKKIT = "org.bukkit.craftbukkit";
  public static final String VERSION_PACKAGE = Bukkit.getServer()
      .getClass()
      .getPackage()
      .getName()
      .split("\\.")[3];

  @NotNull
  public static Optional<Class<?>> getClassOptional(@NotNull String path) {
    try {
      return Optional.of(Class.forName(path));
    } catch (ClassNotFoundException ex) {
      return Optional.empty();
    }
  }

  @NotNull
  public static Optional<Class<?>> getNms(@NotNull String path) {
    return getClassOptional(PACKAGE_NMS + "." + VERSION_PACKAGE + "." + path);
  }

  @NotNull
  public static Class<?> getNmsThrows(@NotNull String path) {
    return getNms(path)
        .orElseThrow(
            () -> new IllegalStateException("no class found: " + PACKAGE_NMS + "." + VERSION_PACKAGE + "." + path)
        );
  }

  @NotNull
  public static Optional<Class<?>> getCraftBukkit(@NotNull String path) {
    return getClassOptional(PACKAGE_CRAFTBUKKIT + "." + VERSION_PACKAGE + "." + path);
  }

  @NotNull
  public static Class<?> getCraftBukkitThrows(@NotNull String path) {
    return getCraftBukkit(path)
        .orElseThrow(
            () -> new IllegalStateException("no class found: " + PACKAGE_CRAFTBUKKIT + "." + VERSION_PACKAGE + "." + path)
        );
  }
}
