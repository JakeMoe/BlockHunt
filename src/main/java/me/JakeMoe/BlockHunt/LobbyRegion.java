package me.JakeMoe.BlockHunt;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

class LobbyRegion extends Region {

  LobbyRegion(Main plugin) {
    super(plugin);
  }

  void addPlayer(Player player) {
    if (players.size() < plugin.getPluginConfig().getLobbyMax()) {

      if (plugin.getLobbyTimer() != null) {
        plugin.clearLobbyTimer();
      }

      plugin.setOriginalHealth(player.getUniqueId(), player.getHealth());
      plugin.setOriginalInventory(player.getUniqueId(), player.getInventory().getContents());
      plugin.setOriginalArmor(player.getUniqueId(), player.getInventory().getArmorContents());
      plugin.setOriginalLocation(player.getUniqueId(), player.getLocation());
      player.getInventory().clear();
      player.teleport(getRandomLocation());
      players.add(player);
      player.sendTitle(ChatColor.GOLD + "PUMPKIN HUNT", "", 20, 100, 20);

      for (Player p : players) {
        p.sendMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " has joined " + ChatColor.YELLOW + "[" + players.size() + "/" + plugin.getPluginConfig().getLobbyMax() + "]");
      }

      if (players.size() >= plugin.getPluginConfig().getLobbyMin()) {
        plugin.startLobbyTimer(new BukkitRunnable() {

          int count = plugin.getPluginConfig().getLobbyDuration();

          @Override
          public void run() {
            if (count <= 0) {
              for (Iterator<Player> player = players.iterator(); player.hasNext(); ) {
                plugin.getGameRegion().addPlayer(player.next());
                player.remove();
              }
              plugin.getLobbyTimer().cancel();
              plugin.getGameManager().start();
            } else if (count <= 5) {
              for (Player player : players) {
                player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "» " + ChatColor.RESET + ChatColor.YELLOW + "The hunt starts in " + ChatColor.RED + count + " seconds!");
                player.sendTitle("" + ChatColor.YELLOW + "The hunt starts in " + ChatColor.RED + count + " seconds!", "", 2, 16, 2);
              }
              count--;
            } else if (count % 5 == 0) {
              for (Player player : players) {
                player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "» " + ChatColor.RESET + ChatColor.YELLOW + "The hunt starts in " + ChatColor.GOLD + count + " seconds!");
                player.sendTitle("" + ChatColor.YELLOW + "The hunt starts in " + ChatColor.GOLD + count + " seconds!", "", 5, 20, 5);
              }
              count--;
            } else {
              count--;
            }
          }

        });
      }
    } else {
      player.sendMessage("The game lobby is full!");
    }
  }

  void updateRegion() {
    world = plugin.getServer().getWorld(plugin.getPluginConfig().getLobbyWorld());
    region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getLobbyRegion());
  }

}
