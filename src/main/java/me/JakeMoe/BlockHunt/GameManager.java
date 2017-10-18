package me.JakeMoe.BlockHunt;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

class GameManager {

  private Main plugin;
  private ArrayList<Location> previousLocations;

  GameManager(Main plugin) {
    this.plugin = plugin;
    this.previousLocations = null;
  }

  void dropRandomBlock() {
    plugin.getGameRegion().getWorld().getBlockAt(plugin.getGameRegion().getRandomLocation()).setType(plugin.getPluginConfig().getMaterial());
  }

  private void end() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] The hunt has finished");
    plugin.getServer().broadcastMessage(plugin.getPluginConfig().getEndMessage());

    Map<UUID, Integer> sortedMap = Util.sortByValue(plugin.getAllScores());
    UUID winner = null;
    for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
      winner = entry.getKey();
    }

    String playerName = Util.getNameByUUID(winner, plugin.getPluginConfig().isNickyEnabled());
    plugin.getServer().broadcastMessage(playerName + " has won the Hunt!");

    plugin.getScoreboard().clear();
    plugin.resetScore();
    plugin.clearGameTimer();
    plugin.getGameRegion().removePlayers();

  }

  private void resetBlocks() {

    if (!previousLocations.isEmpty()) {
      for (Location previousLocation : previousLocations) {
        plugin.getGameRegion().getWorld().getBlockAt(previousLocation).setType(Material.AIR);
      }
    }

    ArrayList<Location> locations = new ArrayList<>();
    for (int i = 0; i < plugin.getPluginConfig().getGameNumBlocks(); i++) {
      Location location = plugin.getGameRegion().getRandomLocation();
      while (locations.contains(location)) {
        location = plugin.getGameRegion().getRandomLocation();
      }
      locations.add(location);
      plugin.getGameRegion().getWorld().getBlockAt(location).setType(plugin.getPluginConfig().getMaterial());
    }

    previousLocations = locations;

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

      BukkitRunnable dropTimer = new BukkitRunnable() {
        @Override
        public void run() {
          dropRandomBlock();
          this.runTaskLater(plugin, (new Random().nextInt(100)) + 100);
        }
      };
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

      plugin.getScoreboard().clear();
      plugin.resetScore();
      plugin.clearGameTimer();
      plugin.getGameRegion().removePlayers();
    }
  }

}
