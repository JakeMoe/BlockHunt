package me.JohnMoe.BlockHunt;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.TreeMap;

public class BlockHuntCommand implements CommandExecutor {

  private Main plugin;

  BlockHuntCommand(Main plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

    if (args.length == 0) {
      sender.sendMessage(ChatColor.LIGHT_PURPLE + "BlockHunt v0.6" + ChatColor.WHITE + " by " + ChatColor.AQUA + "Jake (John) Moe");
    } else if ((args.length == 1) && (args[0].equals("help"))) {
      showSyntax(sender);
    } else if ((args.length == 1) && (args[0].equals("start"))) {
      startHunt(sender);
    } else if ((args.length == 1) && (args[0].equals("stop"))) {
      stopHunt();
    } else if (plugin.isTimerRunning()) {
      sender.sendMessage(ChatColor.RED + "You can't configure the hunt while a hunt is in progress!");
    } else {
      switch (args[0]) {
        case "duration":
          if (args.length == 1) {
            sender.sendMessage("Hunt duration currently set to " + Integer.toString(plugin.config.getHuntDuration()) + " seconds.");
          } else if (args.length == 2) {
            plugin.config.setHuntDuration(args[1]);
            sender.sendMessage("Hunt duration now set to " + Integer.toString(plugin.config.getHuntDuration()) + " seconds.");
          } else {
            showSyntax(sender);
          }
          break;
        case "endMessage":
          if (args.length == 1) {
            sender.sendMessage("Hunt End message is currently: " + plugin.config.getEndMessage());
          } else {
            StringBuilder endMessage = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
              endMessage.append((i == args.length - 1) ? args[i] : args[i] + " ");
            }
            plugin.config.setEndMessage(endMessage.toString());
            sender.sendMessage("Hunt End message is now: " + plugin.config.getEndMessage());
          }
          break;
        case "material":
          if (args.length == 1) {
            sender.sendMessage("Material to hunt is currently " + plugin.config.getMaterial());
          } else if (args.length == 2) {
            try {
              Material m = Material.valueOf(args[1]);
              plugin.config.setMaterial(args[1]);
              sender.sendMessage("Material to hunt is now " + plugin.config.getMaterial());

            } catch (Exception x) {
              sender.sendMessage(ChatColor.RED + args[1] + " is not a valid Bukkit MATERIAL. See the Bukkit API reference for a list of materials");
            }
          } else {
            showSyntax(sender);
          }
          break;
        case "scoreboardTitle":
          if (args.length == 1) {
            sender.sendMessage("Hunt scoreboard title is currently: " + plugin.config.getScoreboardTitle());
          } else {
            StringBuilder scoreboardTitle = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
              scoreboardTitle.append((i == args.length - 1) ? args[i] : args[i] + " ");
            }
            plugin.config.setScoreboardTitle(scoreboardTitle.toString());
            sender.sendMessage("Hunt scoreboard title is now: " + plugin.config.getScoreboardTitle());
          }
          break;
        case "startMessage":
          if (args.length == 1) {
            sender.sendMessage("Hunt Start message is currently: " + plugin.config.getStartMessage());
          } else {
            StringBuilder startMessage = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
              startMessage.append((i == args.length - 1) ? args[i] : args[i] + " ");
            }
            plugin.config.setStartMessage(startMessage.toString());
            sender.sendMessage("Hunt Start message is now: " + plugin.config.getStartMessage());
          }
          break;
        case "stopMessage":
          if (args.length == 1) {
            sender.sendMessage("Hunt Stop message is currently: " + plugin.config.getStopMessage());
          } else {
            StringBuilder stopMessage = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
              stopMessage.append((i == args.length - 1) ? args[i] : args[i] + " ");
            }
            plugin.config.setStopMessage(stopMessage.toString());
            sender.sendMessage("Hunt Stop message is now: " + plugin.config.getStopMessage());
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
    plugin.setTimerRunning(false);
    plugin.getBlockHuntScoreboard().clear();
    Map<UUID, Integer> sortedMap = Util.sortTreeMapByValue(plugin.score);
    Iterator iterator = sortedMap.entrySet().iterator();
    UUID winner = (UUID) ((Map.Entry) iterator.next()).getKey();
    plugin.getServer().broadcastMessage(plugin.getServer().getOfflinePlayer(winner).getName() + " has won the Hunt!");
  }

  private void startHunt(CommandSender sender) {
    if (plugin.isTimerRunning()) {
      sender.sendMessage("The Hunt has already begun! Stop the Hunt if you want to start another.");
    } else {
      plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Starting the hunt");
      plugin.getServer().broadcastMessage(plugin.config.getStartMessage());
      plugin.score = new TreeMap<>();
      plugin.getBlockHuntScoreboard().reset();
      plugin.setTimerRunning(true);

      plugin.startTimer(new BukkitRunnable() {
        @Override
        public void run() {
          endHunt();
        }
      });
    }
  }

  private void stopHunt() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Stopping the hunt");
    plugin.getServer().broadcastMessage(plugin.config.getStopMessage());
    plugin.getTimer().cancel();
    plugin.setTimerRunning(false);
    plugin.getBlockHuntScoreboard().clear();
  }

  private void showSyntax(CommandSender sender) {
    sender.sendMessage(ChatColor.LIGHT_PURPLE + "BlockHunt Syntax:");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh" + ChatColor.WHITE + " - version info");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh duration" + ChatColor.YELLOW + " [seconds]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the length of the");
    sender.sendMessage("      Hunt in seconds");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh endMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
    sender.sendMessage("      end of the Hunt");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh help" + ChatColor.WHITE + " - shows this help message");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh material" + ChatColor.YELLOW + " [material]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the block to hunt to");
    sender.sendMessage("      MATERIAL");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh scoreboardTitle" + ChatColor.WHITE + " - gets or sets the scoreboard title");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh start" + ChatColor.WHITE + " - start a new Hunt");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh startMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
    sender.sendMessage("      the end of the Hunt");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh stop" + ChatColor.WHITE + " - cancel a Hunt in progress");
    sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh stopMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
    sender.sendMessage("      the end of the Hunt");
  }

}
