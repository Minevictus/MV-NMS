package com.proximyst.mvnms;

import org.jetbrains.annotations.NotNull;

public enum MinecraftVersion {
  /**
   * The 1.15 version.
   */
  V1_15("1.15.x"),

  /**
   * The 1.16 version.
   */
  V1_16("1.16.x"),

  /**
   * An unknown Minecraft version.
   *
   * @since 0.2.0
   */
  UNKNOWN("Unknown"),
  ;

  @NotNull
  private final String humanReadableName;

  MinecraftVersion(@NotNull String humanReadableName) {
    this.humanReadableName = humanReadableName;
  }

  @Override
  public String toString() {
    return getHumanReadableName();
  }

  @NotNull
  public String getHumanReadableName() {
    return humanReadableName;
  }
}
