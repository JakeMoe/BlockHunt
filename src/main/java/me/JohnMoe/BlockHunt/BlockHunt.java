package me.JohnMoe.BlockHunt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Timer;
import java.util.logging.Level;

public class BlockHunt implements CommandExecutor {

  Main plugin;
  Timer timer;

  public BlockHunt(Main plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("BlockHunt v0.1 developed by John Moe");
    } else {
      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case "start":
            plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Starting the hunt!");
            plugin.getServer().broadcastMessage("The Hunt has begun!");
            this.timer = new Timer();
            break;
          default:

        }
      }
    }
    return true;
  }

}
