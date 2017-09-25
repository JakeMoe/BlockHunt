package me.JohnMoe.BlockHunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

class BlockHuntScoreboard {

  private Main plugin;
  private Scoreboard scoreboard;

  BlockHuntScoreboard(Main plugin) {
    this.plugin = plugin;
  }

  void clear() {
    Scoreboard emptyBoard = plugin.getServer().getScoreboardManager().getNewScoreboard();
    for (UUID uuid : plugin.score.keySet()) {
      Player player = Bukkit.getPlayer(uuid);
      player.setScoreboard(emptyBoard);
    }
  }

  Scoreboard getScoreboard() {
    return scoreboard;
  }

  void refresh() {

    int total = plugin.score.size();
    if (total > 10) {
      total = 10;
    }

    Objective objective = scoreboard.getObjective("score");

    Map<UUID, Integer> sortedMap = Util.sortTreeMapByValue(plugin.score);
    Iterator iterator = sortedMap.entrySet().iterator();

    for (int i = 0; i < total; i++) {
      Map.Entry entry = (Map.Entry) iterator.next();
      Score score = objective.getScore(plugin.getServer().getOfflinePlayer((UUID) entry.getKey()).getName());
      score.setScore((int) entry.getValue());
    }

  }

  void reset() {
    scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
    Objective objective = scoreboard.registerNewObjective("score", "dummy");
    objective.setDisplayName(plugin.config.getScoreboardTitle());
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
  }

}
