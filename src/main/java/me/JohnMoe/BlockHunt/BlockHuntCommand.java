package me.JohnMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;
import java.util.TreeMap;

public class BlockHuntCommand implements CommandExecutor {

  private Main plugin;

  BlockHuntCommand(Main plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

    if (args.length == 0) {
      sender.sendMessage("BlockHunt v0.4 by Jake (John) Moe");
    } else if ((args.length == 1) && (args[0].equals("help"))) {
      showSyntax(sender);
    } else if ((args.length == 1) && (args[0].equals("start"))) {
      startHunt(sender);
    } else if ((args.length == 1) && (args[0].equals("stop"))) {
      stopHunt();
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
            String endMessage = "";
            for (int i = 1; i < args.length; i++) {
              endMessage += (i == args.length - 1) ? args[i] : args[i] + " ";
            }
            plugin.config.setEndMessage(endMessage);
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
              sender.sendMessage(args[1] + " is not a valid Bukkit MATERIAL. See the Bukkit API reference for a list of materials");
            }
          } else {
            showSyntax(sender);
          }
          break;
        case "startMessage":
          if (args.length == 1) {
            sender.sendMessage("Hunt Start message is currently: " + plugin.config.getStartMessage());
          } else {
            String startMessage = "";
            for (int i = 1; i < args.length; i++) {
              startMessage += (i == args.length - 1) ? args[i] : args[i] + " ";
            }
            plugin.config.setStartMessage(startMessage);
            sender.sendMessage("Hunt Start message is now: " + plugin.config.getStartMessage());
          }
          break;
        case "stopMessage":
          if (args.length == 1) {
            sender.sendMessage("Hunt Stop message is currently: " + plugin.config.getStopMessage());
          } else {
            String stopMessage = "";
            for (int i = 1; i < args.length; i++) {
              stopMessage += (i == args.length - 1) ? args[i] : args[i] + " ";
            }
            plugin.config.setStopMessage(stopMessage);
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
    plugin.blockHuntScoreboard.clear();
  }

  private void startHunt(CommandSender sender) {
    if (plugin.timerRunning) {
      sender.sendMessage("The Hunt has already begun! Stop the Hunt if you want to start another.");
    } else {
      plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Starting the hunt");
      plugin.getServer().broadcastMessage(plugin.config.getStartMessage());
      plugin.score = new TreeMap<>();
      plugin.blockHuntScoreboard.reset();

      plugin.timerRunning = true;

      plugin.timer = new BukkitRunnable() {
        @Override
        public void run() {
          endHunt();
        }
      }.runTaskLater(plugin, plugin.config.getHuntDuration() * 20);
    }
  }

  private void stopHunt() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Stopping the hunt");
    plugin.getServer().broadcastMessage(plugin.config.getStopMessage());
    plugin.timer.cancel();
    plugin.timerRunning = false;
    plugin.blockHuntScoreboard.clear();
  }

  private void showSyntax(CommandSender sender) {
    sender.sendMessage("Syntax:");
    sender.sendMessage("  /bh - version info");
    sender.sendMessage("  /bh duration [seconds] - gets or sets the length of the Hunt in seconds");
    sender.sendMessage("  /bh endMessage [message] - gets or sets the message at the end of the Hunt");
    sender.sendMessage("  /bh help - shows this help message");
    sender.sendMessage("  /bh material [material] - gets or sets the block to hunt to MATERIAL");
    sender.sendMessage("  /bh start - start a new Hunt");
    sender.sendMessage("  /bh startMessage [message] - gets or sets the message at the end of the Hunt");
    sender.sendMessage("  /bh stop - cancel a Hunt in progress");
    sender.sendMessage("  /bh stopMessage [message] - gets or sets the message at the end of the Hunt");
  }

}
