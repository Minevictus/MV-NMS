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

import com.proximyst.mvnms.common.INmsVillager;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.util.Map;
import org.bukkit.entity.Villager;

public class NmsVillagerReflectImplementation implements INmsVillager {
  private final MethodHandle reputationMap;
  private final MethodHandle villagerGetHandle;
  private final MethodHandle getReputation;

  public NmsVillagerReflectImplementation() {
    try {
      var reputationClass = NmsReflectCommon.getNmsThrows("Reputation");
      var craftVillagerClass = NmsReflectCommon.getCraftBukkitThrows("entity.CraftVillager");
      var entityVillagerClass = NmsReflectCommon.getNmsThrows("EntityVillager");
      var publicLookup = MethodHandles.publicLookup();

      {
        var lookup = MethodHandles.privateLookupIn(reputationClass, MethodHandles.lookup());
        reputationMap = lookup.findGetter(reputationClass, "a", Map.class);
      }

      villagerGetHandle = publicLookup.findVirtual(
          craftVillagerClass,
          "getHandle",
          MethodType.methodType(craftVillagerClass)
      );

      {
        MethodHandle handle = null;
        // Scan for a viable method first in case of method name changes.
        for (var method : entityVillagerClass.getMethods()) {
          if (reputationClass.isAssignableFrom(method.getReturnType())
              && method.getParameterCount() == 0
              && Modifier.isPublic(method.getModifiers())
              && !Modifier.isStatic(method.getModifiers())) {
            handle = publicLookup.findVirtual(
                entityVillagerClass,
                method.getName(),
                MethodType.methodType(reputationClass)
            );
            break;
          }
        }
        if (handle == null) {
          handle = publicLookup.findVirtual(
              entityVillagerClass,
              "eN",
              MethodType.methodType(reputationClass)
          );
        }
        getReputation = handle;
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void clearVillagerReputations(Villager villager) {
    try {
      var handle = villagerGetHandle.invokeExact(villager);
      var reputation = getReputation.invokeExact(handle);
      var map = (Map<?, ?>) reputationMap.invokeExact(reputation);
      map.clear();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}
