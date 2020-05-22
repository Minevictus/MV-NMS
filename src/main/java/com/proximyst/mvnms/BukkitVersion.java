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
package com.proximyst.mvnms;

import com.proximyst.mvnms.common.INmsEntity;
import com.proximyst.mvnms.common.INmsItems;
import com.proximyst.mvnms.common.INmsPlayer;
import com.proximyst.mvnms.common.INmsVillager;
import com.proximyst.mvnms.reflect.NmsEntityReflectImplementation;
import com.proximyst.mvnms.reflect.NmsItemsReflectImplementation;
import com.proximyst.mvnms.reflect.NmsPlayerReflectImplementation;
import com.proximyst.mvnms.reflect.NmsVillagerReflectImplementation;
import com.proximyst.mvnms.v1_15_r1.NmsEntityV1_15_R1Implementation;
import com.proximyst.mvnms.v1_15_r1.NmsItemsV1_15_R1Implementation;
import com.proximyst.mvnms.v1_15_r1.NmsPlayerV1_15_R1Implementation;
import com.proximyst.mvnms.v1_15_r1.NmsVillagerV1_15_R1Implementation;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public enum BukkitVersion {
  /**
   * Version 1.15.0 to 1.15.2 (per 2019-03-07).
   */
  V1_15_R1(
      "v1_15_R1",
      MinecraftVersion.V1_15,
      NmsVillagerV1_15_R1Implementation::new,
      NmsItemsV1_15_R1Implementation::new,
      NmsEntityV1_15_R1Implementation::new,
      NmsPlayerV1_15_R1Implementation::new
  ),

  /**
   * Any version not already mapped. This is merely used as a fallback.
   *
   * @since 0.2.0
   */
  REFLECT(
      "reflect",
      MinecraftVersion.UNKNOWN,
      NmsVillagerReflectImplementation::new,
      NmsItemsReflectImplementation::new,
      NmsEntityReflectImplementation::new,
      NmsPlayerReflectImplementation::new
  ),
  ;

  /**
   * The raw version in the package name. This is the Bukkit version, not Minecraft's.
   */
  public static final String rawBukkitVersion = Bukkit.getServer()
      .getClass()
      .getPackage()
      .getName()
      .split("\\.")[3];

  private final String packageName;
  private final MinecraftVersion minecraftVersion;

  private final LazyNonNullValue<INmsVillager> iNmsVillager;
  private final LazyNonNullValue<INmsItems> iNmsItems;
  private final LazyNonNullValue<INmsEntity> iNmsEntity;
  private final LazyNonNullValue<INmsPlayer> iNmsPlayer;

  BukkitVersion(
      @NotNull final String packageName,
      @NotNull final MinecraftVersion minecraftVersion,

      @NotNull final Supplier<INmsVillager> iNmsVillagerSupplier,
      @NotNull final Supplier<INmsItems> iNmsItemsSupplier,
      @NotNull final Supplier<INmsEntity> iNmsEntitySupplier,
      @NotNull final Supplier<INmsPlayer> iNmsPlayerSupplier
  ) {
    this.packageName = packageName;
    this.minecraftVersion = minecraftVersion;

    this.iNmsVillager = new LazyNonNullValue<>(iNmsVillagerSupplier);
    this.iNmsItems = new LazyNonNullValue<>(iNmsItemsSupplier);
    this.iNmsEntity = new LazyNonNullValue<>(iNmsEntitySupplier);
    this.iNmsPlayer = new LazyNonNullValue<>(iNmsPlayerSupplier);
  }

  /**
   * Safe function for deciding whether there are NMS interfaces available.
   * <p>
   * Unlike {@link #getCurrentVersion()}, this does not throw an {@link IllegalStateException} upon
   * an unknown version being found.
   *
   * @return Whether the current version is unknown.
   */
  public static boolean isUnknownVersion() {
    return !getOptionalVersion().isPresent();
  }

  /**
   * Gets the current Bukkit version.
   * <p>
   * This parses on first run, which <b>should</b> be done by {@link MvNms#onEnable()}.
   *
   * @return The current version.
   * @throws IllegalStateException If the version found is unsupported, an exception will be
   *                               thrown.
   */
  @NotNull
  public static BukkitVersion getCurrentVersion() {
    return getOptionalVersion().orElseThrow(
        () -> new IllegalStateException(rawBukkitVersion + " is not a supported version")
    );
  }

  /**
   * Gets the current Bukkit version.
   * <p>
   * This parses on first run, which <b>should</b> be done by {@link MvNms#onEnable()}.
   *
   * @return The current version or an empty {@link Optional}.
   */
  @NotNull
  public static Optional<BukkitVersion> getOptionalVersion() {
    return Internal.INSTANCE.current;
  }

  /**
   * The package name found in the Bukkit package.
   */
  @NotNull
  public String getPackageName() {
    return packageName;
  }

  void setup() {
    iNmsEntity.eager();
    iNmsItems.eager();
    iNmsVillager.eager();
    iNmsPlayer.eager();
  }

  /**
   * The Minecraft version represented by this version of Bukkit.
   */
  @NotNull
  public MinecraftVersion getMinecraftVersion() {
    return minecraftVersion;
  }

  @NotNull
  public INmsVillager getNmsVillager() {
    return iNmsVillager.getValue();
  }

  @NotNull
  public INmsItems getNmsItems() {
    return iNmsItems.getValue();
  }

  @NotNull
  public INmsEntity getNmsEntity() {
    return iNmsEntity.getValue();
  }

  @NotNull
  public INmsPlayer getNmsPlayer() {
    return iNmsPlayer.getValue();
  }

  /**
   * An internal enum for synchronisation use.
   * <p>
   * This parses and sets the current Bukkit version.
   */
  private enum Internal {
    INSTANCE;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    final Optional<BukkitVersion> current;

    Internal() {
      current = Arrays.stream(BukkitVersion.values())
          .filter(it -> it.packageName.equals(rawBukkitVersion))
          .findFirst()
          .or(() -> {
            try {
              REFLECT.setup();
              return Optional.of(REFLECT);
            } catch (Throwable ex) {
              // Reflect does not work!
              return Optional.empty();
            }
          });
    }
  }
}
