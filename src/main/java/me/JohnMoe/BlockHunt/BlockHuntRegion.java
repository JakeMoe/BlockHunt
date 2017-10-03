package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

class BlockHuntRegion  {

  private Main plugin;

  BlockHuntRegion(Main plugin) {
    this.plugin = plugin;
  }

  boolean isInRegion(Player player) {
    for (ProtectedRegion region : plugin.getWorldGuardPlugin().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
      if (region == plugin.config.getArenaRegion()) {
        return true;
      }
    }
    return false;
  }

  boolean isInRegion(Block block) {
    for (ProtectedRegion region : plugin.getWorldGuardPlugin().getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation())) {
      if (region == plugin.config.getArenaRegion()) {
        return true;
      }
    }
    return false;
  }

}
