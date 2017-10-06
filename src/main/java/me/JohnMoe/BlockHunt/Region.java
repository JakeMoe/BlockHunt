package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

class Region {

  private Main plugin;
  private ProtectedRegion gameRegion;
  private World gameWorld;
  private ArrayList<Player> players;

  Region(Main plugin) {
    this.plugin = plugin;
    updateRegion();
    this.players = new ArrayList<>();
  }

  ProtectedRegion getRegion() {
    return gameRegion;
  }

  World getWorld() {
    return gameWorld;
  }

  void updateRegion() {
    gameWorld = plugin.getServer().getWorld(plugin.getPluginConfig().getGameWorld());
    gameRegion = plugin.getWorldGuardPlugin().getRegionManager(gameWorld).getRegion(plugin.getPluginConfig().getGameRegion());
  }

  Location randomLocation() {

    Random random = new Random();

    int minX = gameRegion.getMinimumPoint().getBlockX();
    int maxX = gameRegion.getMaximumPoint().getBlockX();
    int minZ = gameRegion.getMinimumPoint().getBlockZ();
    int maxZ = gameRegion.getMaximumPoint().getBlockZ();

    int rndX = random.nextInt(maxX - minX + 1) + minX;
    int rndZ = random.nextInt(maxZ - minZ + 1) + minZ;
    int rndY = gameWorld.getHighestBlockYAt(rndX, rndZ);

    return new Location(gameWorld, rndX, rndY, rndZ);

  }

  void addPlayer(Player player) {
    if (!(players.contains(player))) {
      players.add(player);
    }
  }

  void removePlayer(Player player) {
    if (players.contains(player)) {
      players.remove(player);
    }
  }

  ArrayList<Player> getPlayers() {
    return players;
  }

}
