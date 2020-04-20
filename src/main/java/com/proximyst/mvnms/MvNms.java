package com.proximyst.mvnms;

import org.bukkit.plugin.java.JavaPlugin;

public class MvNms extends JavaPlugin {
  @Override
  public void onEnable() {
    if (BukkitVersion.isUnknownVersion()) {
      getLogger()
          .severe("Found version " + BukkitVersion.rawBukkitVersion + ", which is not supported.");
      getLogger()
          .severe("The plugin will not function and any dependents might not work correctly.");
      getLogger().warning("Disabling plugin...");
      setEnabled(false);
      return;
    }

    // Set up the current version if it exists.
    BukkitVersion.getOptionalVersion()
        .ifPresent(BukkitVersion::setup);
  }
}
