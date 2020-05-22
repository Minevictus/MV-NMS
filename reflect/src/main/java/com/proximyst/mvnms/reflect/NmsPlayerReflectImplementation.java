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

import com.proximyst.mvnms.common.INmsPlayer;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsPlayerReflectImplementation implements INmsPlayer {
  private final MethodHandle getEntityHandle;
  private final MethodHandle getPlayerConnection;
  private final MethodHandle sendPacketMethod;
  private final MethodHandle newPacketPlayOutMount;
  private final MethodHandle newPacketPlayOutEntityDestroy;

  public NmsPlayerReflectImplementation() {
    try {
      var craftEntityClass = NmsReflectCommon.getCraftBukkitThrows("entity.CraftEntity");
      var entityPlayerClass = NmsReflectCommon.getNmsThrows("EntityPlayer");
      var playerConnectionClass = NmsReflectCommon.getNmsThrows("PlayerConnection");
      var packetPlayOutMountClass = NmsReflectCommon.getNmsThrows("PacketPlayOutMount");
      var packetPlayOutEntityDestroyClass = NmsReflectCommon
          .getNmsThrows("PacketPlayOutEntityDestroy");
      var publicLookup = MethodHandles.publicLookup();

      getEntityHandle = publicLookup.findVirtual(
          craftEntityClass,
          "getHandle",
          MethodType.genericMethodType(0)
      );
      getPlayerConnection = publicLookup.findGetter(
          entityPlayerClass,
          "playerConnection",
          playerConnectionClass
      );
      sendPacketMethod = publicLookup.findVirtual(
          playerConnectionClass,
          "sendPacket",
          MethodType.genericMethodType(1)
      );
      newPacketPlayOutMount = publicLookup.findConstructor(
          packetPlayOutMountClass,
          MethodType.genericMethodType(1)
      );
      newPacketPlayOutEntityDestroy = publicLookup.findConstructor(
          packetPlayOutEntityDestroyClass,
          MethodType.genericMethodType(1)
      );
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void updateClientPassengers(@NotNull Player player) {
    try {
      var nmsPlayer = getEntityHandle.invokeExact(player);
      var packet = newPacketPlayOutMount.invokeExact(nmsPlayer);
      var connection = getPlayerConnection.invokeExact(nmsPlayer);
      sendPacketMethod.invokeExact(connection, packet);
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void destroyEntity(
      @NotNull Player player,
      @NotNull Entity entity
  ) {
    try {
      var nmsPlayer = getEntityHandle.invokeExact(player);
      var nmsEntity = getEntityHandle.invokeExact(entity);
      var packet = newPacketPlayOutEntityDestroy.invokeExact(nmsEntity);
      var connection = getPlayerConnection.invokeExact(nmsPlayer);
      sendPacketMethod.invokeExact(connection, packet);
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }
}
