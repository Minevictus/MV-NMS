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

import com.proximyst.mvnms.common.INmsVillager;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import net.minecraft.server.v1_15_R1.Reputation;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class NmsVillagerV1_15_R1Implementation implements INmsVillager {
  private final MethodHandle reputationMap;

  public NmsVillagerV1_15_R1Implementation() {
    try {
      var lookup = MethodHandles.privateLookupIn(Reputation.class, MethodHandles.lookup());
      reputationMap = lookup.findGetter(Reputation.class, "a", Map.class);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void clearVillagerReputations(Villager villager) {
    var craftVillager = (CraftVillager) villager;
    var reputation = craftVillager.getHandle().eN();
    try {
      var map = (Map<?, ?>) reputationMap.invokeExact(reputation);
      map.clear();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}
