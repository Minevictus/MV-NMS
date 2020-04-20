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
          MethodType.genericMethodType(0)
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
                MethodType.genericMethodType(0)
            );
            break;
          }
        }
        if (handle == null) {
          handle = publicLookup.findVirtual(
              entityVillagerClass,
              "eN",
              MethodType.genericMethodType(0)
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
