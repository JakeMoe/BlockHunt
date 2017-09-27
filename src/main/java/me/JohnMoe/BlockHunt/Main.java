package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import io.loyloy.nicky.Nicky;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

  private BlockHuntRegion blockHuntRegion;
  private BlockHuntScoreboard blockHuntScoreboard;
  private boolean timerRunning;
  private BukkitTask timer;
  Config config;
  private Nicky nickyPlugin;
  TreeMap<UUID, Integer> score;
  private WorldGuardPlugin worldGuardPlugin;

  @Override
  public void onDisable() {
    config.saveConfig();
  }

  @Override
  public void onLoad() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Loading version 0.8");
  }

  @Override
  public void onEnable() {
    if (getWorldGuardPlugin() == null) {
      getServer().getLogger().log(Level.INFO, "[BlockHunt] WorldGuard plugin not found");
      return;
    } else {
      getServer().getLogger().log(Level.INFO, "[BlockHunt] WorldGuard plugin found");
    }

    if (getNickyPlugin() == null) {
      getServer().getLogger().log(Level.INFO, "[BlockHunt] Nicky plugin not found");
    } else {
      getServer().getLogger().log(Level.INFO, "[BlockHunt] Nicky plugin found");
    }

    config = new Config(this);
    config.loadConfig();

    blockHuntRegion = new BlockHuntRegion(this);
    blockHuntScoreboard = new BlockHuntScoreboard(this);
    getCommand("bh").setExecutor(new BlockHuntCommand(this));
    getServer().getPluginManager().registerEvents(new BlockListener(this), this);
  }

  BlockHuntRegion getBlockHuntRegion() {
    return blockHuntRegion;
  }

  BlockHuntScoreboard getBlockHuntScoreboard() {
    return blockHuntScoreboard;
  }

  Nicky getNickyPlugin() {
    if (nickyPlugin == null) {
      Plugin plugin = getServer().getPluginManager().getPlugin("Nicky");
      if ((plugin == null) || (!(plugin instanceof Nicky))) {
        nickyPlugin = null;
      } else {
        nickyPlugin = (Nicky) plugin;
      }
    }
    return nickyPlugin;
  }

  BukkitTask getTimer() {
    return timer;
  }

  WorldGuardPlugin getWorldGuardPlugin() {
    if (worldGuardPlugin == null) {
      Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
      if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
        worldGuardPlugin = null;
      } else {
        worldGuardPlugin = (WorldGuardPlugin) plugin;
      }
    }
    return worldGuardPlugin;
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
