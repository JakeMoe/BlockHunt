package me.JohnMoe.BlockHunt;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Timer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

  protected BlockHuntCommand blockHuntCommand;
  protected boolean timerRunning;
  protected Config config;
  protected Scoreboard scoreboard;
  protected Timer timer;
  protected TreeMap<UUID, Integer> score;

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

    blockHuntCommand = new BlockHuntCommand(this);
    getCommand("bh").setExecutor(blockHuntCommand);
    getServer().getPluginManager().registerEvents(new BlockListener(this), this);
  }

}
