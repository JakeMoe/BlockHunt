package me.JohnMoe.BlockHunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.UUID;

public class BlockHuntCommand implements CommandExecutor {

  private Main plugin;

  public BlockHuntCommand(Main plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

    if (args.length == 0) {
      sender.sendMessage("BlockHunt v0.1 developed by John Moe");
    } else if ((args.length == 1) && (args[0].equals("help"))) {
      showSyntax(sender);
    } else if ((args.length == 1) && (args[0].equals("start"))) {
      startHunt(sender);
    } else if ((args.length == 1) && (args[0].equals("stop"))) {
      stopHunt();
    } else {
      switch (args[0]) {
        case "endMessage":
          if (args.length == 1) {
            sender.sendMessage(plugin.config.getEndMessage());
          } else {
            String endMessage = "";
            for (int i = 1; i < args.length; i++) {
              endMessage += (i == args.length - 1) ? args[i] : args[i] + " ";
            }
            plugin.config.setEndMessage(endMessage);
          }
          break;
        case "material":
          if (args.length == 2) {
            try {
              Material m = Material.valueOf(args[1]);
              plugin.config.setMaterial(args[1]);
            } catch (Exception x) {
              sender.sendMessage(args[1] + " is not a valid Bukkit MATERIAL. See the Bukkit API reference for a list of materials");
            }
          } else {
            showSyntax(sender);
          }
          break;
        case "startMessage":
          if (args.length == 1) {
            sender.sendMessage(plugin.config.getStartMessage());
          } else {
            String startMessage = "";
            for (int i = 1; i < args.length; i++) {
              startMessage += (i == args.length - 1) ? args[i] : args[i] + " ";
            }
            plugin.config.setStartMessage(startMessage);
          }
          break;
        case "stopMessage":
          if (args.length == 1) {
            sender.sendMessage(plugin.config.getStopMessage());
          } else {
            String stopMessage = "";
            for (int i = 1; i < args.length; i++) {
              stopMessage += (i == args.length - 1) ? args[i] : args[i] + " ";
            }
            plugin.config.setStopMessage(stopMessage);
          }
          break;
        default:
          showSyntax(sender);
      }
    }

    return true;

  }

  private void endHunt() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] The hunt has finished");
    plugin.getServer().broadcastMessage(plugin.config.getEndMessage());
    clearScoreboards();
  }

  private void startHunt(CommandSender sender) {
    if (plugin.timerRunning) {
      sender.sendMessage("The Hunt has already begun! Stop the Hunt if you want to start another.");
    } else {
      plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Starting the hunt");
      plugin.getServer().broadcastMessage(plugin.config.getStartMessage());
      plugin.score = new TreeMap<>();

      plugin.scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
      Objective objective = plugin.scoreboard.registerNewObjective("score", "dummy");
      objective.setDisplayName("THE HUNT");
      objective.setDisplaySlot(DisplaySlot.SIDEBAR);

      plugin.timerRunning = true;
      plugin.timer = new Timer();
      plugin.timer.schedule(new TimerTask() {
        @Override
        public void run() {
          endHunt();
        }
      }, 5 * 60 * 1000);
    }
  }

  private void stopHunt() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Stopping the hunt");
    plugin.getServer().broadcastMessage(plugin.config.getStopMessage());
    plugin.timer.cancel();
    plugin.timerRunning = false;
    clearScoreboards();
  }

  private void clearScoreboards() {
    Scoreboard emptyBoard = plugin.getServer().getScoreboardManager().getNewScoreboard();
    for (UUID uuid : plugin.score.keySet()) {
      Player player = Bukkit.getPlayer(uuid);
      player.setScoreboard(emptyBoard);
    }
  }

  protected void refreshScoreboard() {

    int total = plugin.score.size();
    if (total > 10) {
      total = 10;
    }

    Objective objective = plugin.scoreboard.getObjective("score");

    Map<UUID, Integer> sortedMap = Util.sortTreeMapByValue(plugin.score);
    Iterator iterator = sortedMap.entrySet().iterator();

    for (int i = 0; i < total; i++) {
      Map.Entry entry = (Map.Entry) iterator.next();
      Score score = objective.getScore(entry.getKey().toString());
      score.setScore((int) entry.getValue());
    }

  }

  private void showSyntax(CommandSender sender) {
    sender.sendMessage("Syntax:");
    sender.sendMessage("  /bh - version info");
    sender.sendMessage("  /bh endMessage - gets the current end message");
    sender.sendMessage("  /bh endMessage <message> - sets the message at the end of the Hunt");
    sender.sendMessage("  /bh help - shows this help message");
    sender.sendMessage("  /bh material <material> - sets the block to hunt to MATERIAL");
    sender.sendMessage("  /bh start - start a new Hunt");
    sender.sendMessage("  /bh startMessage - gets the current start message");
    sender.sendMessage("  /bh startMessage <message> - sets the message at the end of the Hunt");
    sender.sendMessage("  /bh stop - cancel a Hunt in progress");
    sender.sendMessage("  /bh stopMessage - gets the current stop message");
    sender.sendMessage("  /bh stopMessage <message> - sets the message at the end of the Hunt");
  }

}
