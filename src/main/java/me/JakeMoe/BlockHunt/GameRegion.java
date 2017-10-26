package me.JakeMoe.BlockHunt;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
    player.sendMessage(ChatColor.YELLOW + plugin.getPluginConfig().getStartMessage());
  }

  void updateRegion() {
    world = plugin.getServer().getWorld(plugin.getPluginConfig().getGameWorld());
    region = plugin.getWorldGuardPlugin().getRegionManager(world).getRegion(plugin.getPluginConfig().getGameRegion());
  }

}
