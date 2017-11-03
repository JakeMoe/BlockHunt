package me.JakeMoe.BlockHunt;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import io.loyloy.nicky.Nicky;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

  private static final String version = "1.3";
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
  private HashMap<UUID, ItemStack[]> originalArmor;
  private HashMap<UUID, ItemStack[]> originalInventory;
  private HashMap<UUID, Integer> allScores;
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
    lobbyRegion = new LobbyRegion(this);
    gameRegion = new GameRegion(this);
    scoreboard = new Scoreboard(this);

    originalHealth = new HashMap<>();
    originalLocations = new HashMap<>();
    originalInventory = new HashMap<>();
    originalArmor = new HashMap<>();

    allScores = new HashMap<>();

    getCommand("bh").setExecutor(new Command(this));
    getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
    getServer().getPluginManager().registerEvents(new DamageListener(this), this);
    getServer().getPluginManager().registerEvents(new DisconnectListener(this), this);
    getServer().getPluginManager().registerEvents(new HitListener(this), this);
    getServer().getPluginManager().registerEvents(new PickupItemListener(this), this);
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

  Integer getScore(UUID player) {
    return allScores.get(player);
  }

  HashMap<UUID, Integer> getAllScores() {
    return allScores;
  }

  void setScore(UUID player, int score) {
    allScores.put(player, score);
  }

  void resetScore() {
    allScores = new HashMap<>();
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

  HashMap<UUID, ItemStack[]> getOriginalInventory() {
    return originalInventory;
  }

  void setOriginalInventory(UUID player, ItemStack[] inventory) {
    originalInventory.put(player, inventory);
  }

  HashMap<UUID, ItemStack[]> getOriginalArmor() {
    return originalArmor;
  }

  void setOriginalArmor(UUID player, ItemStack[] inventory) {
    originalArmor.put(player, inventory);
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
    gameTimer = bukkitRunnable.runTaskLater(this, this.pluginConfig.getGameDuration() * 20);
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
