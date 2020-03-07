package com.proximyst.mvnms;

public enum MinecraftVersion {
  /**
   * The 1.15 version.
   */
  V1_15,
  ;

  @Override
  public String toString() {
    switch (this) {
      case V1_15:
        return "1.15";
      default:
        throw new IllegalStateException();
    }
  }
}
