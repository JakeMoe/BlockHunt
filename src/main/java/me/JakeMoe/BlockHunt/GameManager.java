package me.JakeMoe.BlockHunt;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

class GameManager {

  private Main plugin;
  private ArrayList<Location> blockLocations;
  private BukkitRunnable dropTimer;
  private boolean stopTimer;

  class DropTimer extends BukkitRunnable {
    @Override
    public void run() {
      if (stopTimer) {
        dropTimer.cancel();
        dropTimer = null;
      } else {
        dropRandomBlock();
        dropTimer = new DropTimer();
        dropTimer.runTaskLater(plugin, (new Random().nextInt(100)) + 100);
      }
    }
  }

  GameManager(Main plugin) {
    this.plugin = plugin;
    this.blockLocations = null;
  }

  private void dropRandomBlock() {
    Location location = plugin.getGameRegion().getRandomLocation();
    plugin.getGameRegion().getWorld().getBlockAt(location).setType(plugin.getPluginConfig().getMaterial());
    blockLocations.add(location);
  }

  private void end() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] The hunt has finished");
    plugin.getServer().broadcastMessage(plugin.getPluginConfig().getEndMessage());

    saveScores(plugin.getAllScores());

    Map<UUID, Integer> sortedMap = Util.sortByValue(plugin.getAllScores());
    UUID winner = null;
    for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
      winner = entry.getKey();
    }

    String playerName = Util.getNameByUUID(winner, plugin.getPluginConfig().isNickyEnabled());
    plugin.getServer().broadcastMessage(playerName + " has won the Hunt!");

    if (!(blockLocations== null)) {
      for (Location blockLocation : blockLocations) {
        plugin.getGameRegion().getWorld().getBlockAt(blockLocation).setType(Material.AIR);
      }
    }

    plugin.getScoreboard().clear();
    plugin.resetScore();

    stopTimer = true;

    plugin.clearGameTimer();
    plugin.getGameRegion().removePlayers();

  }

  private void resetBlocks() {

    blockLocations = new ArrayList<>();
    for (int i = 0; i < plugin.getPluginConfig().getGameNumBlocks(); i++) {
      Location location = plugin.getGameRegion().getRandomLocation();
      while (blockLocations.contains(location)) {
        location = plugin.getGameRegion().getRandomLocation();
      }
      blockLocations.add(location);
      plugin.getGameRegion().getWorld().getBlockAt(location).setType(plugin.getPluginConfig().getMaterial());
    }

  }

  void saveScores(HashMap<UUID, Integer> scores) {
    File scoreFile = new File(plugin.getDataFolder(), (new SimpleDateFormat("yyMMdd-hhmmss")).format(new Date()) + ".yml");
    if (!scoreFile.exists()) {
      try {
        scoreFile.createNewFile();
      } catch (IOException e) {
        plugin.getLogger().log(Level.INFO, e.getMessage());
      }
    }

    YamlConfiguration yc = new YamlConfiguration();
    for (Object object : scores.entrySet()) {
      Map.Entry entry = (Map.Entry) object;
      yc.set(entry.getKey().toString(), entry.getValue());
    }

    try {
      yc.save(scoreFile);
    } catch (IOException e) {
      plugin.getLogger().log(Level.INFO, e.getMessage());
    }
  }

  void start() {
    if (plugin.getGameTimer() == null) {
      plugin.getScoreboard().reset();
      resetBlocks();

      plugin.startGameTimer(new BukkitRunnable() {
        @Override
        public void run() {
          end();
        }
      });

      stopTimer = false;
      dropTimer = new DropTimer();
      dropTimer.runTaskLater(plugin, (new Random().nextInt(100)) + 100);


    } else {
      plugin.getServer().broadcastMessage("A game is already in progress.");
    }
  }

  void stop() {
    if (plugin.getGameTimer() == null) {
      plugin.getServer().broadcastMessage("The game has not yet begun!");
    } else {
      plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Stopping the hunt");
      plugin.getServer().broadcastMessage(plugin.getPluginConfig().getStopMessage());
      plugin.getGameTimer().cancel();

      if (!(blockLocations== null)) {
        for (Location blockLocation : blockLocations) {
          plugin.getGameRegion().getWorld().getBlockAt(blockLocation).setType(Material.AIR);
        }
      }

      plugin.getScoreboard().clear();
      plugin.resetScore();

      stopTimer = true;

      plugin.clearGameTimer();
      plugin.getGameRegion().removePlayers();
    }
  }

}
