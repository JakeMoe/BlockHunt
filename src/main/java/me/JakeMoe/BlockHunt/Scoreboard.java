package me.JakeMoe.BlockHunt;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

class Scoreboard {

  private Main plugin;
  private org.bukkit.scoreboard.Scoreboard scoreboard;

  Scoreboard(Main plugin) {
    this.plugin = plugin;
  }

    void clear() {
    org.bukkit.scoreboard.Scoreboard emptyBoard = plugin.getServer().getScoreboardManager().getNewScoreboard();
    for (Player player : plugin.getServer().getOnlinePlayers()) {
      if (player.getScoreboard() == this.getScoreboard()) {
        player.setScoreboard(emptyBoard);
      }
    }
  }

  private org.bukkit.scoreboard.Scoreboard getScoreboard() {
    return scoreboard;
  }

  void refresh() {

    int total = plugin.getAllScores().size();
    if (total > 10) {
      total = 10;
    }

    Objective objective = scoreboard.getObjective("score");

    Iterator iterator = Util.sortByValue(plugin.getAllScores()).entrySet().iterator();

    for (int i = 0; i < total; i++) {
      Map.Entry entry = (Map.Entry) iterator.next();
      Score score = objective.getScore(Util.getNameByUUID((UUID) entry.getKey(), plugin.getPluginConfig().isNickyEnabled()));
      score.setScore((int) entry.getValue());
    }

    for (Player player : plugin.getGameRegion().getPlayers()) {
      if (player.getScoreboard() != getScoreboard()) {
         player.setScoreboard(getScoreboard());
      }
    }

  }

  void reset() {
    scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
    Objective objective = scoreboard.registerNewObjective("score", "dummy");
    objective.setDisplayName(plugin.getPluginConfig().getScoreboardTitle());
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
  }

}
