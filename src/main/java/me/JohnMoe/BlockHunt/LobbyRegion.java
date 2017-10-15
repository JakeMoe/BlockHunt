package me.JohnMoe.BlockHunt;

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
//      plugin.setOriginalItemInHand(player.getUniqueId(), player.getInventory().getItemInMainHand());
      plugin.setOriginalArmor(player.getUniqueId(), player.getInventory().getArmorContents());
      plugin.setOriginalLocation(player.getUniqueId(), player.getLocation());
      player.getInventory().clear();
      player.teleport(getRandomLocation());
      players.add(player);

      if (players.size() >= plugin.getPluginConfig().getLobbyMin()) {
        plugin.startLobbyTimer(new BukkitRunnable() {

          int count = plugin.getPluginConfig().getLobbyDuration();

          @Override
          public void run() {
            if (count > 0) {
              for (Player player : players) {
                player.sendMessage("The game will begin in " + count + " seconds!");
              }
              count--;
            } else {
              for (Iterator<Player> player = players.iterator(); player.hasNext(); ) {
                plugin.getGameRegion().addPlayer(player.next());
                player.remove();
              }
              plugin.getLobbyTimer().cancel();
              plugin.getGameManager().start();
            }
          }

        });
      } else {
        player.sendMessage("The game lobby is full!");
      }
    }
  }

  void removePlayer(Player player) {
  }

  void removePlayers() {
  }

  void updateRegion() {
    world = plugin.getServer().getWorld(plugin.getPluginConfig().getLobbyWorld());
    region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getLobbyRegion());
  }

}
