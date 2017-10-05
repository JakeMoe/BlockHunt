package me.JohnMoe.BlockHunt;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.TreeMap;

public class Command implements CommandExecutor {

  private Main plugin;

  Command(Main plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {

    if ((!(sender instanceof Player)) || (sender.hasPermission("BlockParty.bh"))) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "BlockHunt v" + plugin.getVersion() + ChatColor.WHITE + " by " + ChatColor.AQUA + plugin.getAuthor());
      } else if (args[0].equals("help")) {
        if (args.length == 1) {
          showSyntax(sender, "bh");
        } else {
          showSyntax(sender, args[1]);
        }
      } else if (args[0].equals("start")) {
        if (args.length == 1) {
          startHunt(sender);
        } else {
          showSyntax(sender, args[0]);
        }
      } else if (args[0].equals("stop")) {
        if (args.length == 1) {
          stopHunt();
        } else {
          showSyntax(sender, args[0]);
        }
      } else if (plugin.isTimerRunning()) {
        sender.sendMessage(ChatColor.RED + "You can't configure the hunt while a hunt is in progress!");
      } else if (args[0].equals("game")) {
        if ((args.length == 1) || (args[1].equals("help"))) {
          showSyntax(sender, args[0]);
        } else if (args[1].equals("region")) {
          if (args.length == 2) {
            sender.sendMessage("WorldGuard region for the game is currently " + plugin.getPluginConfig().getGameRegion());
          } else if (args[2].equals("help")) {
            showSyntax(sender, args[0] + args[1]);
          } else if (args.length == 3) {
            plugin.getPluginConfig().setGameRegion(args[2]);
            sender.sendMessage("WorldGuard region for the game is now " + plugin.getPluginConfig().getGameRegion());
          } else {
            showSyntax(sender, args[0] + args[1]);
          }
        } else if (args[1].equals("spawn")) {
          if (args.length == 2) {
            sender.sendMessage("Game spawn point is currently " + plugin.getPluginConfig().getGameWorld());
          } else if (args[2].equals("set")) {

          } else {

          }
        } else if (args[1].equals("world")) {
          if (args.length == 2) {
            sender.sendMessage("Bukkit world for the game is currently " + plugin.getPluginConfig().getGameWorld());
          } else if (args[2].equals("help")) {
            showSyntax(sender, args[0] + args[1]);
          } else if (args.length == 3) {
            plugin.getPluginConfig().setGameWorld(args[2]);
            sender.sendMessage(" Bukkit world for the game is now " + plugin.getPluginConfig().getGameWorld());
          } else {
            showSyntax(sender, args[0] + args[1]);
          }
        }
      } else if (args[0].equals("lobby")) {
        if ((args.length == 1) || (args[1].equals("help"))) {
          showSyntax(sender, args[0]);
        } else if (args[1].equals("region")) {
          if (args.length == 2) {
            sender.sendMessage("WorldGuard region for the lobby is currently " + plugin.getPluginConfig().getLobbyRegion());
          } else if (args[2].equals("help")) {
            showSyntax(sender, args[0] + args[1]);
          } else if (args.length == 3) {
            plugin.getPluginConfig().setLobbyRegion(args[2]);
            sender.sendMessage("WorldGuard region for the lobby is now " + plugin.getPluginConfig().getLobbyRegion());
          } else {
            showSyntax(sender, args[0] + args[1]);
          }
        } else if (args[1].equals("world")) {
          if (args.length == 2) {
            sender.sendMessage("Bukkit world for the lobby is currently " + plugin.getPluginConfig().getLobbyWorld());
          } else if (args[2].equals("help")) {
            showSyntax(sender, args[0] + args[1]);
          } else if (args.length == 3) {
            plugin.getPluginConfig().setLobbyWorld(args[2]);
            sender.sendMessage(" Bukkit world for the lobby is now " + plugin.getPluginConfig().getLobbyWorld());
          } else {
            showSyntax(sender, args[0] + args[1]);
          }
        }
      } else {
        switch (args[0]) {
          case "duration":
            if (args.length == 1) {
              sender.sendMessage("Hunt duration currently set to " + Integer.toString(plugin.getPluginConfig().getHuntDuration()) + " seconds.");
            } else if (args.length == 2) {
              plugin.getPluginConfig().setHuntDuration(args[1]);
              sender.sendMessage("Hunt duration now set to " + Integer.toString(plugin.getPluginConfig().getHuntDuration()) + " seconds.");
            } else {
              showSyntax(sender, args[0]);
            }
            break;
          case "endMessage":
            if (args.length == 1) {
              sender.sendMessage("Hunt End message is currently: " + plugin.getPluginConfig().getEndMessage());
            } else if (args[1].equals("help")) {
              showSyntax(sender, args[0]);
            } else {
              StringBuilder endMessage = new StringBuilder();
              for (int i = 1; i < args.length; i++) {
                endMessage.append((i == args.length - 1) ? args[i] : args[i] + " ");
              }
              plugin.getPluginConfig().setEndMessage(endMessage.toString());
              sender.sendMessage("Hunt End message is now: " + plugin.getPluginConfig().getEndMessage());
            }
            break;
          case "material":
            if (args.length == 1) {
              sender.sendMessage("Material to hunt is currently " + plugin.getPluginConfig().getMaterial());
            } else if (args[1].equals("help")) {
              showSyntax(sender, args[0]);
            } else if (args.length == 2) {
              for (Material m : Material.values()) {
                if (m.name().equals(args[1])) {
                  plugin.getPluginConfig().setMaterial(args[1]);
                  sender.sendMessage("Material to hunt is now " + plugin.getPluginConfig().getMaterial());
                  break;
                }
              }
              sender.sendMessage(ChatColor.RED + args[1] + " is not a valid Bukkit MATERIAL. See the Bukkit API reference for a list of materials");
            } else {
              showSyntax(sender, args[0]);
            }
            break;
          case "scoreboardTitle":
            if (args.length == 1) {
              sender.sendMessage("Hunt scoreboard title is currently: " + plugin.getPluginConfig().getScoreboardTitle());
            } else if (args[1].equals("help")) {
              showSyntax(sender, args[0]);
            } else {
              StringBuilder scoreboardTitle = new StringBuilder();
              for (int i = 1; i < args.length; i++) {
                scoreboardTitle.append((i == args.length - 1) ? args[i] : args[i] + " ");
              }
              plugin.getPluginConfig().setScoreboardTitle(scoreboardTitle.toString());
              sender.sendMessage("Hunt scoreboard title is now: " + plugin.getPluginConfig().getScoreboardTitle());
            }
            break;
          case "startMessage":
            if (args.length == 1) {
              sender.sendMessage("Hunt Start message is currently: " + plugin.getPluginConfig().getStartMessage());
            } else if (args[1].equals("help")) {
              showSyntax(sender, args[0]);
            } else {
              StringBuilder startMessage = new StringBuilder();
              for (int i = 1; i < args.length; i++) {
                startMessage.append((i == args.length - 1) ? args[i] : args[i] + " ");
              }
              plugin.getPluginConfig().setStartMessage(startMessage.toString());
              sender.sendMessage("Hunt Start message is now: " + plugin.getPluginConfig().getStartMessage());
            }
            break;
          case "stopMessage":
            if (args.length == 1) {
              sender.sendMessage("Hunt Stop message is currently: " + plugin.getPluginConfig().getStopMessage());
            } else if (args[1].equals("help")) {
              showSyntax(sender, args[0]);
            } else {
              StringBuilder stopMessage = new StringBuilder();
              for (int i = 1; i < args.length; i++) {
                stopMessage.append((i == args.length - 1) ? args[i] : args[i] + " ");
              }
              plugin.getPluginConfig().setStopMessage(stopMessage.toString());
              sender.sendMessage("Hunt Stop message is now: " + plugin.getPluginConfig().getStopMessage());
            }
            break;
          case "useNicky":
            if (args.length == 1) {
              sender.sendMessage("Nicky support is currently " + (plugin.getPluginConfig().isNickyEnabled() ? "enabled" : "disabled"));
            } else if (args[1].equals("help")) {
              showSyntax(sender, args[0]);
            } else if (args.length == 2) {
              switch (args[1]) {
                case "enabled":
                case "true":
                  if (plugin.getNickyPlugin() == null) {
                    plugin.getPluginConfig().setNickyEnabled(false);
                    sender.sendMessage("Nicky plugin not found, Nicky support is now disabled");
                  } else {
                    plugin.getPluginConfig().setNickyEnabled(true);
                    sender.sendMessage("Nicky support is now enabled");
                  }
                  break;
                case "disabled":
                case "false":
                  plugin.getPluginConfig().setNickyEnabled(false);
                  sender.sendMessage("Nicky support is now disabled");
                  break;
                default:
                  showSyntax(sender, args[0]);
              }
            } else {
              showSyntax(sender, args[0]);
            }
          default:
            showSyntax(sender, "bh");
        }
      }
    }

    return true;

  }

  private void endHunt() {
    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] The hunt has finished");
    plugin.getServer().broadcastMessage(plugin.getPluginConfig().getEndMessage());
    plugin.setTimerRunning(false);
    plugin.getScoreboard().clear();
    Map<UUID, Integer> sortedMap = Util.sortTreeMapByValue(plugin.score);
    Iterator iterator = sortedMap.entrySet().iterator();
    UUID winner = (UUID) ((Map.Entry) iterator.next()).getKey();
    String playerName = Util.getNameByUUID(winner, plugin.getPluginConfig().isNickyEnabled());

    ArrayList<String[]> prizes = plugin.getPluginConfig().getPrizes();
    String message = (prizes.get(0))[1].replaceAll("%player%", playerName);

    plugin.getServer().broadcastMessage(playerName + " has won the Hunt and receives " + (prizes.get(0))[0]);
    plugin.getServer().broadcastMessage(message);
  }

  private void startHunt(CommandSender sender) {
    if (plugin.isTimerRunning()) {
      sender.sendMessage("The Hunt has already begun! Stop the Hunt if you want to start another.");
    } else {
      plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Starting the hunt");
      plugin.getServer().broadcastMessage(plugin.getPluginConfig().getStartMessage());
      plugin.score = new TreeMap<>();
      plugin.getScoreboard().reset();
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
    plugin.getServer().broadcastMessage(plugin.getPluginConfig().getStopMessage());
    plugin.getTimer().cancel();
    plugin.setTimerRunning(false);
    plugin.getScoreboard().clear();
  }

  private void showSyntax(CommandSender sender, String command) {
    switch (command) {
      case "duration":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh duration" + ChatColor.YELLOW + " [seconds]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the length of the");
        sender.sendMessage("    Hunt in seconds");
        break;
      case "endMessage":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh endMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
        sender.sendMessage("    end of the Hunt");
        break;
      case "gameRegion":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh gameRegion" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the WorldGuard region name for the game");
        break;
      case "gameWorld":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh gameWorld" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the Bukkit world name for the game");
        break;
      case "lobbyRegion":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh lobbyRegion" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the WorldGuard region name for the lobby");
        break;
      case "lobbyWorld":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh lobbyWorld" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the Bukkit world name for the lobby");
        break;
      case "material":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh material" + ChatColor.YELLOW + " [material]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the block to hunt to");
        sender.sendMessage("    MATERIAL");
        break;
      case "scoreboardTitle":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh scoreboardTitle" + ChatColor.WHITE + " - gets or sets the scoreboard title");
        break;
      case "start":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh start" + ChatColor.WHITE + " - start a new Hunt");
        break;
      case "startMessage":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh startMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
        sender.sendMessage("    the end of the Hunt");
        break;
      case "stop":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh stop" + ChatColor.WHITE + " - cancel a Hunt in progress");
        break;
      case "stopMessage":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh stopMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
        sender.sendMessage("    the end of the Hunt");
        break;
      case "useNicky":
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bh useNicky" + ChatColor.YELLOW + " [disabled/enabled/false/true]" + ChatColor.WHITE + " - gets or sets whether to use Nicky for names");
        break;
      case "bh":
      default:
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "BlockHunt Syntax:");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh" + ChatColor.WHITE + " - version info");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh duration" + ChatColor.YELLOW + " [seconds]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the length of the");
        sender.sendMessage("      Hunt in seconds");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh endMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
        sender.sendMessage("      end of the Hunt");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh help" + ChatColor.WHITE + " - shows this help message");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh gameRegion" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the WorldGuard region name for the game");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh gameWorld" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the Bukkit world name for the game");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh lobbyRegion" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the WorldGuard region name for the game");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh lobbyWorld" + ChatColor.YELLOW + " [name]" + ChatColor.WHITE + " - gets or sets the Bukkit world name for the game");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh material" + ChatColor.YELLOW + " [material]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the block to hunt to");
        sender.sendMessage("      MATERIAL");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh scoreboardTitle" + ChatColor.WHITE + " - gets or sets the scoreboard title");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh start" + ChatColor.WHITE + " - start a new Hunt");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh startMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
        sender.sendMessage("      the end of the Hunt");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh stop" + ChatColor.WHITE + " - cancel a Hunt in progress");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh stopMessage" + ChatColor.YELLOW + " [message]" + ChatColor.WHITE + " - gets or" + ChatColor.YELLOW + " sets" + ChatColor.WHITE + " the message at");
        sender.sendMessage("      the end of the Hunt");
        sender.sendMessage("  " + ChatColor.LIGHT_PURPLE + "/bh useNicky" + ChatColor.YELLOW + " [disabled/enabled/false/true]" + ChatColor.WHITE + " - gets or sets whether to use Nicky for names");
    }

  }

}
