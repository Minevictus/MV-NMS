package com.proximyst.mvnms.v1_15_r1;

import com.proximyst.mvnms.common.INmsVillager;
import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.v1_15_R1.Reputation;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class NmsVillagerV1_15_R1Implementation implements INmsVillager {
  private final Field reputationMap;

  public NmsVillagerV1_15_R1Implementation() {
    try {
      reputationMap = Reputation.class.getDeclaredField("a");
      reputationMap.setAccessible(true);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void clearVillagerReputations(Villager villager) {
    CraftVillager craftVillager = (CraftVillager) villager;
    Reputation reputation = craftVillager.getHandle().eN();
    try {
      Map<?, ?> map = (Map<?, ?>) reputationMap.get(reputation);
      map.clear();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
