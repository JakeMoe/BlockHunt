package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

class Config {

  private Main plugin;
  private FileConfiguration fileConfig;

  Config(Main plugin) {
    this.plugin = plugin;
  }

  void loadConfig() {

    boolean booChanged = false;

    fileConfig = plugin.getConfig();

    if (!fileConfig.contains("BlockHunt.settings.endMessage")) {
      fileConfig.set("BlockHunt.settings.endMessage", "The Hunt has ended!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.huntDuration")) {
      fileConfig.set("BlockHunt.settings.huntDuration", "30");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.huntRegion")) {
      fileConfig.set("BlockHunt.settings.huntRegion", "BlockHunt");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.huntWorld")) {
      fileConfig.set("BlockHunt.settings.huntWorld", "Survival");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.materialToFind")) {
      fileConfig.set("BlockHunt.settings.materialToFind", "PUMPKIN");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.scoreboardTitle")) {
      fileConfig.set("BlockHunt.settings.scoreboardTitle", "TOP HUNTERS");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.startMessage")) {
      fileConfig.set("BlockHunt.settings.startMessage", "The Hunt has begun!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.stopMessage")) {
      fileConfig.set("BlockHunt.settings.stopMessage", "The Hunt has been stopped!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.useNicky")) {
      if (plugin.getNickyPlugin() == null) {
        fileConfig.set("BlockHunt.settings.useNicky", "false");
      } else {
        fileConfig.set("BlockHunt.settings.useNicky", "true");
      }
      booChanged = true;
    }

    if (booChanged) {
      this.plugin.saveConfig();
    }

  }

  void saveConfig() {
    plugin.saveConfig();
  }

  String getEndMessage() {
    return fileConfig.getString("BlockHunt.settings.endMessage");
  }

  void setEndMessage(String message) {
    fileConfig.set("BlockHunt.settings.endMessage", message);
    plugin.saveConfig();
  }

  int getHuntDuration() {
    return Integer.valueOf(fileConfig.getString("BlockHunt.settings.huntDuration"));
  }

  void setHuntDuration(String seconds) {
    fileConfig.set("BlockHunt.settings.huntDuration", seconds);
    plugin.saveConfig();
  }

  ProtectedRegion getHuntRegion() {
    return plugin.getWorldGuardPlugin().getRegionManager(getHuntWorld()).getRegion(fileConfig.getString("BlockHunt.settings.huntRegion"));
  }

  void setHuntRegion(String region) {
    fileConfig.set("BlockHunt.settings.huntRegion", region);
    plugin.saveConfig();
  }

  World getHuntWorld() {
    return org.bukkit.Bukkit.getWorld(fileConfig.getString("BlockHunt.settings.huntWorld"));
  }

  void setHuntWorld(String world) {
    fileConfig.set("BlockHunt.settings.huntWorld", world);
    plugin.saveConfig();
  }

  Material getMaterial() {
    return Material.valueOf(fileConfig.getString("BlockHunt.settings.materialToFind"));
  }

  void setMaterial(String material) {
    fileConfig.set("BlockHunt.settings.materialToFind", material);
    plugin.saveConfig();
  }

  String getScoreboardTitle() {
    return fileConfig.getString("BlockHunt.settings.scoreboardTitle");
  }

  void setScoreboardTitle(String scoreboardTitle) {
    fileConfig.set("BlockHunt.settings.scoreboardTitle", scoreboardTitle);
    plugin.saveConfig();
  }

  String getStartMessage() {
    return fileConfig.getString("BlockHunt.settings.startMessage");
  }

  void setStartMessage(String message) {
    fileConfig.set("BlockHunt.settings.startMessage", message);
    plugin.saveConfig();
  }

  String getStopMessage() {
    return fileConfig.getString("BlockHunt.settings.stopMessage");
  }

  void setStopMessage(String message) {
    fileConfig.set("BlockHunt.settings.stopMessage", message);
    plugin.saveConfig();
  }

  boolean isNickyEnabled() {
    return fileConfig.getBoolean("BlockHunt.settings.useNicky");
  }

  void setNickyEnabled(boolean enabled) {
    fileConfig.set("BlockHunt.settings.useNicky", enabled);
    plugin.saveConfig();
  }

}