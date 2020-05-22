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

import com.proximyst.mvnms.common.INmsEntity;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NmsEntityReflectImplementation implements INmsEntity {
  private final MethodHandle getEntityHandle;
  private final MethodHandle setYawMethod;
  private final MethodHandle setPitchMethod;
  private final MethodHandle getYawMethod;
  private final MethodHandle getPitchMethod;
  private final MethodHandle setLivingYawMethod;
  private final MethodHandle setLivingPitchMethod;
  private final MethodHandle setInvisibleMethod;
  private final MethodHandle setInvulnerableMethod;

  public NmsEntityReflectImplementation() {
    try {
      var craftEntityClass = NmsReflectCommon.getCraftBukkitThrows("entity.CraftEntity");
      var nmsEntityClass = NmsReflectCommon.getNmsThrows("Entity");
      var nmsLivingEntityClass = NmsReflectCommon.getNmsThrows("EntityLiving");
      var publicLookup = MethodHandles.publicLookup();

      getEntityHandle = publicLookup.findVirtual(
          craftEntityClass,
          "getHandle",
          MethodType.genericMethodType(0) // Varies by class, so let's not enforce it
      );
      setYawMethod = publicLookup.findSetter(
          nmsEntityClass,
          "yaw",
          float.class
      );
      setPitchMethod = publicLookup.findSetter(
          nmsEntityClass,
          "pitch",
          float.class
      );
      getYawMethod = publicLookup.findGetter(
          nmsEntityClass,
          "yaw",
          float.class
      );
      getPitchMethod = publicLookup.findGetter(
          nmsEntityClass,
          "pitch",
          float.class
      );
      setLivingYawMethod = publicLookup.findSetter(
          nmsLivingEntityClass,
          "aK",
          float.class
      );
      setLivingPitchMethod = publicLookup.findSetter(
          nmsLivingEntityClass,
          "aL",
          float.class
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
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void rotate(
      @NotNull Entity entity,
      float yaw,
      float pitch
  ) {
    try {
      var nmsEntity = getEntityHandle.invokeExact(entity);
      setYawMethod.invokeExact(nmsEntity, yaw);
      setPitchMethod.invokeExact(nmsEntity, pitch);
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void rotateLivingEntity(
      @NotNull LivingEntity livingEntity,
      float yaw,
      float pitch
  ) {
    rotateClamped(livingEntity, yaw, pitch);
    try {
      var nmsEntity = getEntityHandle.invokeExact(livingEntity);
      setLivingYawMethod.invokeExact(nmsEntity, (float) getYawMethod.invokeExact(nmsEntity));
      setLivingPitchMethod.invokeExact(nmsEntity, (float) getPitchMethod.invokeExact(nmsEntity));
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void setInvisible(@NotNull Entity entity, boolean visibility) {
    try {
      var nmsEntity = getEntityHandle.invokeExact(entity);
      setInvisibleMethod.invokeExact(nmsEntity, visibility);
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void setInvulnerable(@NotNull Entity entity, boolean invulnerability) {
    try {
      var nmsEntity = getEntityHandle.invokeExact(entity);
      setInvulnerableMethod.invokeExact(nmsEntity, invulnerability);
    } catch (Throwable ex) {
      throw new RuntimeException(ex);
    }
  }
}
