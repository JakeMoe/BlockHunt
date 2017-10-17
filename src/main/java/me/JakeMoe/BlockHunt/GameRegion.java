package me.JakeMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

//import java.util.Iterator;

class GameRegion extends Region {

  GameRegion(Main plugin) {
    super(plugin);
  }

  @Override
  void addPlayer(Player player) {
    player.teleport(getRandomLocation());

    ItemStack stoneAxe = new ItemStack(Material.STONE_AXE);
    player.getInventory().setItemInMainHand(stoneAxe);

    plugin.setScore(player.getUniqueId(), 0);

    players.add(player);
    player.sendMessage(plugin.getPluginConfig().getStartMessage());
  }
/*
  void removePlayer(Player player) {
    player.setHealth(plugin.getOriginalHealth().get(player.getUniqueId()));
    player.getInventory().clear();
    player.getInventory().setContents(plugin.getOriginalInventory().get(player.getUniqueId()));
    player.getInventory().setArmorContents(plugin.getOriginalArmor().get(player.getUniqueId()));
//    player.getInventory().setItemInMainHand(plugin.getOriginalItemInHand().get(player.getUniqueId()));
    player.teleport(plugin.getOriginalLocations().get(player.getUniqueId()));
    players.remove(player);
  }

  void removePlayers() {
    for (Iterator<Player> player = players.iterator(); player.hasNext(); ) {
      Player currPlayer = player.next();
      currPlayer.setHealth(plugin.getOriginalHealth().get(currPlayer.getUniqueId()));
      currPlayer.getInventory().clear();
      currPlayer.getInventory().setContents(plugin.getOriginalInventory().get(currPlayer.getUniqueId()));
      currPlayer.getInventory().setArmorContents(plugin.getOriginalArmor().get(currPlayer.getUniqueId()));
//      player.getInventory().setItemInMainHand(plugin.getOriginalItemInHand().get(player.getUniqueId()));
      currPlayer.teleport(plugin.getOriginalLocations().get(currPlayer.getUniqueId()));
      player.remove();
    }
  }
*/

  void resetBlocks() {

  }

  void updateRegion() {
    world = plugin.getServer().getWorld(plugin.getPluginConfig().getGameWorld());
    region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getGameRegion());
  }

}
