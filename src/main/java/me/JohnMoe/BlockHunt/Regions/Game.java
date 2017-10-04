package me.JohnMoe.BlockHunt.Regions;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.JohnMoe.BlockHunt.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class Game {

  private Main plugin;
  private List<Player> players;

  public Game(Main plugin) {
    this.plugin = plugin;
  }

  public boolean isInRegion(Player player) {
    ProtectedRegion game = plugin.config.getArenaRegion();
    for (ProtectedRegion region : plugin.getWorldGuardPlugin().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
      if (region == game) {
        return true;
      }
    }
    return false;
  }

  public boolean isInRegion(Block block) {
    ProtectedRegion game = plugin.config.getArenaRegion();
    for (ProtectedRegion region : plugin.getWorldGuardPlugin().getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation())) {
      if (region == game) {
        return true;
      }
    }
    return false;
  }

  public void add(Player player) {
    if (!(players.contains(player))) {
      players.add(player);
    }
  }

}
