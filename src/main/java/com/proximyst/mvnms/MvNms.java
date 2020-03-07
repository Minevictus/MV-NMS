package com.proximyst.mvnms;

import org.bukkit.plugin.java.JavaPlugin;

public class MvNms extends JavaPlugin {
  @Override
  public void onEnable() {
    // Set up the current version if it exists.
    BukkitVersion.getOptionalVersion()
        .ifPresent(BukkitVersion::setup);
  }
}
