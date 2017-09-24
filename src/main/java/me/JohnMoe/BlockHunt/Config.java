package me.JohnMoe.BlockHunt;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

  Main plugin;
  FileConfiguration fileConfig;

  public Config(Main plugin) {
    this.plugin = plugin;
  }

  public Config loadConfig() {
    this.fileConfig = plugin.getConfig();

    if (!this.fileConfig.contains("BlockHunt.settings.blockId")) {
      this.fileConfig.set("BlockHunt.settings.blockId", 86);
    }
  }

  public void saveConfig() {
    plugin.saveConfig();
  }

  public int getBlockId() {
    return this.fileConfig.getInt("BlockHunt.settings.blockId");
  }

  public void setBlockId(int blockId) {
    this.fileConfig.set("BlockHunt.settings.blockId", blockId);
    this.plugin.saveConfig();
  }

}