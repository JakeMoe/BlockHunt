package me.JohnMoe.BlockHunt;

import io.loyloy.nicky.Nick;
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
    for (Player player : plugin.getServer().getOnlinePlayers()) {
      if (player.getScoreboard() == plugin.getBlockHuntScoreboard().getScoreboard()) {
        player.setScoreboard(emptyBoard);
      }
    }
  }

  private Scoreboard getScoreboard() {
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
      Player player = plugin.getServer().getPlayer((UUID) entry.getKey());
      String name = null;
      if (plugin.config.isNickyEnabled()) {
        name = (new Nick(player)).get();
      }
      if (name == null) {
        name = plugin.getServer().getOfflinePlayer((UUID) entry.getKey()).getName();
      }
      Score score = objective.getScore(name);
      score.setScore((int) entry.getValue());
    }

    for (Player player : plugin.getServer().getOnlinePlayers()) {
      if (player.getScoreboard() != getScoreboard() && plugin.getBlockHuntRegion().isInRegion(player)) {
         player.setScoreboard(getScoreboard());
      }
    }


  }

  void reset() {
    scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
    Objective objective = scoreboard.registerNewObjective("score", "dummy");
    objective.setDisplayName(plugin.config.getScoreboardTitle());
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
  }

}
