package me.JakeMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

class BlockBreakListener implements Listener {

  private Main plugin;

  BlockBreakListener(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (plugin.getGameRegion().getRegion().contains(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ())) {
      if ((plugin.getGameTimer() != null) &&
          (event.getBlock().getType() == plugin.getPluginConfig().getMaterial())) {
        event.getBlock().setType(Material.AIR);
        UUID playerUuid = event.getPlayer().getUniqueId();
        Integer newScore = plugin.getScore(playerUuid) + 1;
        plugin.setScore(playerUuid, newScore);
        plugin.getScoreboard().refresh();
        event.setCancelled(true);
      }
    }
  }
}
