package me.JohnMoe.BlockHunt;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
      plugin.setOriginalLocation(player.getUniqueId(), player.getLocation());
      player.teleport(randomLocation());
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
              for (Player player : players) {
                players.remove(player);
                plugin.getGameRegion().addPlayer(player);
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

  void updateRegion() {
    world = plugin.getServer().getWorld(plugin.getPluginConfig().getLobbyWorld());
    region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getLobbyRegion());
  }

}
