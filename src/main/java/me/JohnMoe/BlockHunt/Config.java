package me.JohnMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

  private Main plugin;
  private FileConfiguration fileConfig;

  public Config(Main plugin) {
    this.plugin = plugin;
  }

  protected void loadConfig() {

    boolean booChanged = false;

    fileConfig = plugin.getConfig();

    if (!fileConfig.contains("BlockHunt.settings.endMessage")) {
      fileConfig.set("BlockHunt.settings.endMessage", "The Hunt has ended!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.materialToFind")) {
      fileConfig.set("BlockHunt.settings.materialToFind", "PUMPKIN");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.startMessage")) {
      fileConfig.set("BlockHunt.settings.startMessage", "The Hunt has begun!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.strStopMessage")) {
      fileConfig.set("BlockHunt.settings.stopMessage", "The Hunt has been stopped!");
      booChanged = true;
    }

    if (booChanged) {
      this.plugin.saveConfig();
    }

  }

  protected void saveConfig() {
    plugin.saveConfig();
  }

  protected Material getMaterial() {
    return Material.valueOf(fileConfig.getString("BlockHunt.settings.materialToFind"));
  }

  protected void setMaterial(String material) {
    fileConfig.set("BlockHunt.settings.materialToFind", material);
    plugin.saveConfig();
  }

  protected String getStartMessage() {
    return fileConfig.getString("BlockHunt.settings.startMessage");
  }

  protected void setStartMessage(String message) {
    fileConfig.set("BlockHunt.settings.startMessage", message);
    plugin.saveConfig();
  }

  protected String getStopMessage() {
    return fileConfig.getString("BlockHunt.settings.stopMessage");
  }

  protected void setStopMessage(String message) {
    fileConfig.set("BlockHunt.settings.stopMessage", message);
    plugin.saveConfig();
  }

  protected String getEndMessage() {
    return fileConfig.getString("BlockHunt.settings.endMessage");
  }

  protected void setEndMessage(String message) {
    fileConfig.set("BlockHunt.settings.endMessage", message);
    plugin.saveConfig();
  }

}