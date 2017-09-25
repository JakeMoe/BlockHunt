package me.JohnMoe.BlockHunt;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

  BlockHuntScoreboard blockHuntScoreboard;
  boolean timerRunning;
  BukkitTask timer;
  Config config;
  TreeMap<UUID, Integer> score;

  @Override
  public void onDisable() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Disabling plugin");
    config.saveConfig();
  }

  @Override
  public void onLoad() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Loading version 0.1");
  }

  @Override
  public void onEnable() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Enabling plugin");

    config = new Config(this);
    config.loadConfig();

    blockHuntScoreboard = new BlockHuntScoreboard(this);
    getCommand("bh").setExecutor(new BlockHuntCommand(this));
    getServer().getPluginManager().registerEvents(new BlockListener(this), this);
  }

}
