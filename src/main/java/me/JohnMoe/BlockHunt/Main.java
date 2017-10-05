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

  private static String version = "0.8";
  private static String author = "Jake (John) Moe";

  private Region gameRegion;
  private Region lobbyRegion;
  private Scoreboard scoreboard;
  private boolean timerRunning;
  private BukkitTask timer;
  public Config config;
  private Nicky nickyPlugin;
  TreeMap<UUID, Integer> score;
  private WorldGuardPlugin worldGuardPlugin;

  @Override
  public void onDisable() {
    config.saveConfig();
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

    lobbyRegion = new Region("lobby", this);
    gameRegion = new Region("game", this);
    scoreboard = new Scoreboard(this);
    getCommand("bh").setExecutor(new Command(this));
    getServer().getPluginManager().registerEvents(new Listener(this), this);
  }

  @Override
  public void onLoad() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Loading version " + version);
  }

  public Region getGameRegion() {
    return gameRegion;
  }

  public Region getLobbyRegion() {
    return lobbyRegion;
  }

  public Scoreboard getScoreboard() {
    return scoreboard;
  }

  public Nicky getNickyPlugin() {
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

  public BukkitTask getTimer() {
    return timer;
  }

  public WorldGuardPlugin getWorldGuardPlugin() {
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

  public boolean isTimerRunning() {
    return timerRunning;
  }

  public void setTimerRunning(boolean timerRunning) {
    this.timerRunning = timerRunning;
  }

  public void startTimer(BukkitRunnable bukkitRunnable) {
    timer = bukkitRunnable.runTaskLater(this, this.config.getHuntDuration() * 20);
  }

  public String getAuthor() {
    return author;
  }

  public String getVersion() {
    return version;
  }

}
