package me.JohnMoe.BlockHunt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

  BlockHunt bh;
  Config config;
  Integer blockId;

  @Override
  public void onDisable() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Disabling plugin");
    this.saveConfig();
  }

  @Override
  public void onLoad() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Loading version 0.1");
  }

  @Override
  public void onEnable() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Enabling plugin");

    this.config = new Config();
    config.loadConfig();

    bh = new BlockHunt(this);
    this.getCommand("bh").setExecutor(bh);
  }

}
