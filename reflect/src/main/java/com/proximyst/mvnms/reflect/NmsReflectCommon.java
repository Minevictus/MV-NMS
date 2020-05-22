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

import java.util.Optional;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class NmsReflectCommon {
  public static final String PACKAGE_NMS = "net.minecraft.server";
  public static final String PACKAGE_CRAFTBUKKIT = "org.bukkit.craftbukkit";
  public static final String VERSION_PACKAGE = Bukkit.getServer()
      .getClass()
      .getPackage()
      .getName()
      .split("\\.")[3];

  private NmsReflectCommon() throws IllegalAccessException {
    throw new IllegalAccessException(getClass().getSimpleName() + " cannot be instantiated");
  }

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
            () -> new IllegalStateException(
                "no class found: " + PACKAGE_NMS + "." + VERSION_PACKAGE + "." + path)
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
            () -> new IllegalStateException(
                "no class found: " + PACKAGE_CRAFTBUKKIT + "." + VERSION_PACKAGE + "." + path)
        );
  }
}
