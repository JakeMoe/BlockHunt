package me.JohnMoe.BlockHunt;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

class GameManager {

  private Main plugin;

  GameManager(Main plugin) {
    this.plugin = plugin;
  }

  private void end() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] The hunt has finished");
    plugin.getServer().broadcastMessage(plugin.getPluginConfig().getEndMessage());
    plugin.getScoreboard().clear();
    plugin.getGameRegion().removePlayers();
    plugin.clearGameTimer();
    Map<UUID, Integer> sortedMap = Util.sortTreeMapByValue(plugin.score);
    Iterator iterator = sortedMap.entrySet().iterator();
    UUID winner = (UUID) ((Map.Entry) iterator.next()).getKey();
    String playerName = Util.getNameByUUID(winner, plugin.getPluginConfig().isNickyEnabled());

    plugin.getServer().broadcastMessage(playerName + " has won the Hunt!");

    plugin.getGameRegion().removePlayers();
  }

  void start() {
    if (plugin.getGameTimer() == null) {
      plugin.getServer().broadcastMessage(plugin.getPluginConfig().getStartMessage());
      plugin.score = new TreeMap<>();
      plugin.getScoreboard().reset();

      plugin.startGameTimer(new BukkitRunnable() {
        @Override
        public void run() {
          end();
        }
      });
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
      plugin.clearGameTimer();
      plugin.getGameRegion().removePlayers();
    }
  }

}
