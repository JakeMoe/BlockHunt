package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

class Region {

  private String name;
  private Main plugin;
  private ProtectedRegion region;
  private World world;
  private ArrayList<Player> players;

  Region(String name, Main plugin) {
    this.name = name;
    this.plugin = plugin;
    updateRegion();
    this.players = new ArrayList<>();
  }

  ProtectedRegion getRegion() {
    return region;
  }

  World getWorld() {
    return world;
  }

  void updateRegion() {
    if (name.equals("game")) {
      world = plugin.getServer().getWorld(plugin.getPluginConfig().getGameWorld());
      region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getGameRegion());
    } else if (name.equals("lobby")) {
      world = plugin.getServer().getWorld(plugin.getPluginConfig().getLobbyWorld());
      region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getLobbyRegion());
    }
  }

  private Location randomLocation() {

    Random random = new Random();

    int minX = region.getMinimumPoint().getBlockX();
    int maxX = region.getMaximumPoint().getBlockX();
    int minZ = region.getMinimumPoint().getBlockZ();
    int maxZ = region.getMaximumPoint().getBlockZ();

    int rndX = random.nextInt(maxX - minX + 1) + minX;
    int rndZ = random.nextInt(maxZ - minZ + 1) + minZ;
    int rndY = world.getHighestBlockYAt(rndX, rndZ);

    return new Location(world, rndX, rndY, rndZ);

  }

  void addPlayer(Player player) {
    if (!players.contains(player)) {
      if (players.size() < plugin.getPluginConfig().getLobbyMax()) {

        plugin.setOriginalHealth(player.getUniqueId(), player.getHealth());
        plugin.setOriginalLocation(player.getUniqueId(), player.getLocation());
        player.teleport(randomLocation());
        players.add(player);

        if (plugin.getLobbyTimer() != null) {
          plugin.clearLobbyTimer();
        }

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
                  player.teleport(plugin.getGameRegion().randomLocation());
                  player.sendMessage("The game has begun!");
                  players.remove(player);
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
  }

  void removePlayer(Player player) {
    if (players.contains(player)) {
      player.setHealth(plugin.getOriginalHealth().get(player.getUniqueId()));
      player.teleport(plugin.getOriginalLocations().get(player.getUniqueId()));
      players.remove(player);
    }
  }

  void removePlayers() {
    for (Player player : players) {
      removePlayer(player);
    }
  }

  ArrayList<Player> getPlayers() {
    return players;
  }

}
