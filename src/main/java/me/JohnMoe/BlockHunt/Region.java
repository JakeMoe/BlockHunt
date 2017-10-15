package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

abstract class Region {

  Main plugin;
  ProtectedRegion region;
  World world;
  ArrayList<Player> players;

  Region(Main plugin) {
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

  abstract void updateRegion();

  Location getRandomLocation() {

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

  abstract void addPlayer(Player player);

  abstract void removePlayer(Player player);

  abstract void removePlayers();

  ArrayList<Player> getPlayers() {
    return players;
  }

}
