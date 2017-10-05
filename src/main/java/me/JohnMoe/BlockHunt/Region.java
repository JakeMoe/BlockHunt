package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Region {

  private String name;
  private Main plugin;
  private String worldName;
  private String regionName;
  private ProtectedRegion gameRegion;
  private ArrayList<Player> players;

  public Region(String name, Main plugin) {
    this.name = name;
    this.plugin = plugin;
    updateRegion();
    this.players = new ArrayList<>();
  }

  public ProtectedRegion getRegion() {
    return gameRegion;
  }

  public void updateRegion() {
    this.worldName = plugin.config.getGameWorld();
    this.regionName = plugin.config.getGameRegion();
    gameRegion = plugin.getWorldGuardPlugin().getRegionManager(plugin.getServer().getWorld(worldName)).getRegion(regionName);
  }

  public boolean isInRegion(Player player) {
    for (ProtectedRegion region : plugin.getWorldGuardPlugin().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
      if (region == gameRegion) {
        return true;
      }
    }
    return false;
  }

  public boolean isInRegion(Block block) {
    for (ProtectedRegion region : plugin.getWorldGuardPlugin().getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation())) {
      if (region == gameRegion) {
        return true;
      }
    }
    return false;
  }

  public void addPlayer(Player player) {
    if (!(players.contains(player))) {
      players.add(player);
    }
  }

  public void removePlayer(Player player) {
    if (players.contains(player)) {
      players.remove(player);
    }
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

}
