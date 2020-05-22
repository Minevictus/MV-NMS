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
