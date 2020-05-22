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
package com.proximyst.mvnms.v1_15_r1;

import com.proximyst.mvnms.common.INmsEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NmsEntityV1_15_R1Implementation implements INmsEntity {
  @Override
  public void rotate(@NotNull Entity entity, float yaw, float pitch) {
    var handle = getEntityHandle(entity);
    handle.yaw = yaw;
    handle.pitch = pitch;
  }

  @Override
  public void rotateLivingEntity(@NotNull LivingEntity livingEntity, float yaw, float pitch) {
    rotateClamped(livingEntity, yaw, pitch);
    var handle = ((CraftLivingEntity) livingEntity).getHandle();

    handle.aK = handle.yaw;
    handle.aL = handle.yaw;
  }

  @Override
  public void setInvisible(@NotNull Entity entity, boolean visibility) {
    var handle = getEntityHandle(entity);
    handle.setInvisible(visibility);
  }

  @Override
  public void setInvulnerable(@NotNull Entity entity, boolean invulnerability) {
    var handle = getEntityHandle(entity);
    handle.setInvulnerable(invulnerability);
  }

  private net.minecraft.server.v1_15_R1.Entity getEntityHandle(Entity entity) {
    return ((CraftEntity) entity).getHandle();
  }
}
