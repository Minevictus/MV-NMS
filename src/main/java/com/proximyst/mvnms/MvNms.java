package com.proximyst.mvnms;

import org.bukkit.plugin.java.JavaPlugin;

public class MvNms extends JavaPlugin {
  @Override
  public void onEnable() {
    // Ensure the version is determined and throw an exception if this plugin cannot enable.
    BukkitVersion.getCurrentVersion().setup();
  }
}
