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
