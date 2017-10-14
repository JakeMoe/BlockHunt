package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import io.loyloy.nicky.Nicky;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

  private static final String version = "0.10";
  private static final String author = "Jake Moe";

  private GameManager gameManager;
  private GameRegion gameRegion;
  private LobbyRegion lobbyRegion;
  private Scoreboard scoreboard;
  private boolean settingLobbyJoin;
  private BukkitTask gameTimer;
  private BukkitTask lobbyTimer;
  private Config pluginConfig;
  private Nicky nickyPlugin;
  private HashMap<UUID, Double> originalHealth;
  private HashMap<UUID, Location> originalLocations;
  TreeMap<UUID, Integer> score;
  private WorldGuardPlugin worldGuardPlugin;

  @Override
  public void onDisable() {
    pluginConfig.saveConfig();
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

    pluginConfig = new Config(this);
    pluginConfig.loadConfig();

    gameManager = new GameManager(this);
    lobbyRegion = new LobbyRegion("lobby", this);
    gameRegion = new GameRegion("game", this);
    scoreboard = new Scoreboard(this);

    originalHealth = new HashMap<>();
    originalLocations = new HashMap<>();

    getCommand("bh").setExecutor(new Command(this));
    getServer().getPluginManager().registerEvents(new DamageListener(this), this);
    getServer().getPluginManager().registerEvents(new HitListener(this), this);
  }

  @Override
  public void onLoad() {
    getServer().getLogger().log(Level.INFO, "[BlockHunt] Loading version " + version);
  }

  GameManager getGameManager() {
    return gameManager;
  }

  Region getGameRegion() {
    return gameRegion;
  }

  Region getLobbyRegion() {
    return lobbyRegion;
  }

  Scoreboard getScoreboard() {
    return scoreboard;
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

  HashMap<UUID, Double> getOriginalHealth() {
    return originalHealth;
  }

  void setOriginalHealth(UUID player, Double health) {
    originalHealth.put(player, health);
  }

  HashMap<UUID, Location> getOriginalLocations() {
    return originalLocations;
  }

  void setOriginalLocation(UUID player, Location location) {
    originalLocations.put(player, location);
  }

  Config getPluginConfig() {
    return pluginConfig;
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

  boolean isSettingLobbyJoin() {
    return settingLobbyJoin;
  }

  void setSettingLobbyJoin(boolean settingLobbyJoin) {
    this.settingLobbyJoin = settingLobbyJoin;
  }

  void clearGameTimer() {
    gameTimer.cancel();
    gameTimer = null;
  }

  BukkitTask getGameTimer() {
    return gameTimer;
  }

  void startGameTimer(BukkitRunnable bukkitRunnable) {
    gameTimer = bukkitRunnable.runTaskLater(this, this.pluginConfig.getHuntDuration() * 20);
  }

  void clearLobbyTimer() {
    lobbyTimer.cancel();
    lobbyTimer = null;
  }

  BukkitTask getLobbyTimer() {
    return lobbyTimer;
  }

  void startLobbyTimer(BukkitRunnable bukkitRunnable) {
    lobbyTimer = bukkitRunnable.runTaskTimer(this, 5, 20);
  }

  String getAuthor() {
    return author;
  }

  String getVersion() {
    return version;
  }

}
