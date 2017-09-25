package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

  private BlockHuntScoreboard blockHuntScoreboard;
  private boolean timerRunning;
  private BukkitTask timer;
  Config config;
  TreeMap<UUID, Integer> score;

  @Override
  public void onDisable() {
    config.saveConfig();
  }

  @Override
  public void onLoad() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Loading version 0.6");
  }

  @Override
  public void onEnable() {
    if (getWorldGuardPlugin() == null) {

      return;
    }

    config = new Config(this);
    config.loadConfig();

    blockHuntScoreboard = new BlockHuntScoreboard(this);
    getCommand("bh").setExecutor(new BlockHuntCommand(this));
    getServer().getPluginManager().registerEvents(new BlockListener(this), this);
  }

  BlockHuntScoreboard getBlockHuntScoreboard() {
    return blockHuntScoreboard;
  }

  BukkitTask getTimer() {
    return timer;
  }

  WorldGuardPlugin getWorldGuardPlugin() {
    Plugin worldGuardPlugin = getServer().getPluginManager().getPlugin("WorldGuard");
    if(worldGuardPlugin ==null||!(worldGuardPlugin instanceof WorldGuardPlugin)) {
      return null;
    }
    return (WorldGuardPlugin) worldGuardPlugin;
  }

  boolean isTimerRunning() {
    return timerRunning;
  }

  void setTimerRunning(boolean timerRunning) {
    this.timerRunning = timerRunning;
  }

  void startTimer(BukkitRunnable bukkitRunnable) {
    timer = bukkitRunnable.runTaskLater(this, this.config.getHuntDuration() * 20);
  }

}
