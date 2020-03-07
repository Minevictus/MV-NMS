package com.proximyst.mvnms;

import com.proximyst.mvnms.common.INmsItems;
import com.proximyst.mvnms.common.INmsVillager;
import com.proximyst.mvnms.v1_15_r1.NmsItemsV1_15_R1Implementation;
import com.proximyst.mvnms.v1_15_r1.NmsVillagerV1_15_R1Implementation;
import java.util.Arrays;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public enum BukkitVersion {
  /**
   * Version 1.15.0 to 1.15.2 (per 2019-03-07).
   */
  V1_15_R1("v1_15_R1") {
    @Override
    void setup() {
      minecraftVersion = MinecraftVersion.V1_15;
      iNmsVillager = new NmsVillagerV1_15_R1Implementation();
      iNmsItems = new NmsItemsV1_15_R1Implementation();
    }
  },
  ;

  /**
   * The raw version in the package name. This is the Bukkit version, not Minecraft's.
   */
  public static final String rawBukkitVersion = Bukkit.getServer()
      .getClass().getPackage().getName().split("\\.")[3];

  private final String packageName;

  protected MinecraftVersion minecraftVersion = null;
  protected INmsVillager iNmsVillager = null;
  protected INmsItems iNmsItems = null;

  BukkitVersion(final String packageName) {
    this.packageName = packageName;
  }

  /**
   * Safe function for deciding whether there are NMS interfaces available.
   * <p>
   * Unlike {@link #getCurrentVersion()}, this does not throw an {@link IllegalStateException} upon an unknown version
   * being found.
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
   * @throws IllegalStateException If the version found is unsupported, an exception will be thrown.
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

  abstract void setup();

  /**
   * The Minecraft version represented by this version of Bukkit.
   */
  @NotNull
  public MinecraftVersion getMinecraftVersion() {
    return minecraftVersion;
  }

  @NotNull
  public INmsVillager getNmsVillager() {
    return iNmsVillager;
  }

  @NotNull
  public INmsItems getNmsItems() {
    return iNmsItems;
  }

  /**
   * An internal enum for synchronisation use.
   * <p>
   * This parses and sets the current Bukkit version.
   */
  private enum Internal {
    INSTANCE;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") final Optional<BukkitVersion> current;

    Internal() {
      current = Arrays.stream(BukkitVersion.values())
          .filter(it -> it.packageName.equals(rawBukkitVersion))
          .findFirst();
    }
  }
}
