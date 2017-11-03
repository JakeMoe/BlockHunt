package me.JakeMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Iterator;
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

    int minX = region.getMinimumPoint().getBlockX();
    int maxX = region.getMaximumPoint().getBlockX();
    int minZ = region.getMinimumPoint().getBlockZ();
    int maxZ = region.getMaximumPoint().getBlockZ();

    Random random = new Random();

    int rndX, rndY, rndZ;

    ArrayList<Material> whitelist = new ArrayList<>();
    whitelist.add(Material.COBBLESTONE);
    whitelist.add(Material.DIRT);
    whitelist.add(Material.GRASS);
    whitelist.add(Material.GRASS_PATH);
    whitelist.add(Material.SAND);
    whitelist.add(Material.STONE);

    Material materialUnder;

    do {
      rndX = random.nextInt(maxX - minX + 1) + minX;
      rndZ = random.nextInt(maxZ - minZ + 1) + minZ;
      rndY = world.getHighestBlockYAt(rndX, rndZ);

      Location locationUnder = new Location(world, rndX, rndY - 1, rndZ);
      Block blockUnder = locationUnder.getBlock();
      materialUnder = blockUnder.getType();
    } while (!region.contains(rndX, rndY, rndZ) || !whitelist.contains(materialUnder));

    return new Location(world, rndX, rndY, rndZ);

  }

  abstract void addPlayer(Player player);

  void removePlayer(Player player) {
    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
      player.removePotionEffect(potionEffect.getType());
    }
    player.setHealth(plugin.getOriginalHealth().get(player.getUniqueId()));
    player.getInventory().clear();
    player.getInventory().setContents(plugin.getOriginalInventory().get(player.getUniqueId()));
    player.getInventory().setArmorContents(plugin.getOriginalArmor().get(player.getUniqueId()));
    player.teleport(plugin.getOriginalLocations().get(player.getUniqueId()));
    players.remove(player);
  }

  void removePlayers() {
    for (Iterator<Player> player = players.iterator(); player.hasNext(); ) {
      Player currPlayer = player.next();
      for (PotionEffect potionEffect : currPlayer.getActivePotionEffects()) {
        currPlayer.removePotionEffect(potionEffect.getType());
      }
      currPlayer.setHealth(plugin.getOriginalHealth().get(currPlayer.getUniqueId()));
      currPlayer.getInventory().clear();
      currPlayer.getInventory().setContents(plugin.getOriginalInventory().get(currPlayer.getUniqueId()));
      currPlayer.getInventory().setArmorContents(plugin.getOriginalArmor().get(currPlayer.getUniqueId()));
      currPlayer.teleport(plugin.getOriginalLocations().get(currPlayer.getUniqueId()));
      player.remove();
    }
  }

  ArrayList<Player> getPlayers() {
    return players;
  }

}
