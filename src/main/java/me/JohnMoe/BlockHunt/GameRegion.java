package me.JohnMoe.BlockHunt;

import org.bukkit.entity.Player;

import java.util.logging.Level;

class GameRegion extends Region {

  GameRegion(Main plugin) {
    super(plugin);
  }

  @Override
  void addPlayer(Player player) {
    player.teleport(randomLocation());
    players.add(player);
    player.sendMessage("The game has begun!");
  }

  void removePlayer(Player player) {
    plugin.getLogger().log(Level.INFO, "[BlockHunt] In removePlayer - " + player.getDisplayName());
    if (players.contains(player)) {
      player.setHealth(plugin.getOriginalHealth().get(player.getUniqueId()));
      player.teleport(plugin.getOriginalLocations().get(player.getUniqueId()));
      players.remove(player);
    }
  }

  void removePlayers() {
    plugin.getLogger().log(Level.INFO, "[BlockHunt] In removePlayers - " + players.size() + " players");
    for (Player player : players) {
      plugin.getLogger().log(Level.INFO, "[BlockHunt] Removing " + player.getDisplayName());
      removePlayer(player);
    }
  }

  void updateRegion() {
    world = plugin.getServer().getWorld(plugin.getPluginConfig().getGameWorld());
    region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getGameRegion());
  }

}
