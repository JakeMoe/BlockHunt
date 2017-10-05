package me.JohnMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {

  private Main plugin;

  Listener(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onHit(PlayerInteractEvent event) {
    if (plugin.getGameRegion().isInRegion(event.getClickedBlock())) {
      if ((plugin.isTimerRunning()) &&
          (event.getAction() == Action.LEFT_CLICK_BLOCK) &&
          (event.getClickedBlock().getType() == plugin.getPluginConfig().getMaterial())) {
        event.getClickedBlock().setType(Material.AIR);
        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();
        int pScore;
        if (plugin.score.containsKey(playerUuid)) {
          pScore = plugin.score.get(playerUuid) + 1;
        } else {
          pScore = 1;
        }
        plugin.score.put(playerUuid, pScore);
        plugin.getScoreboard().refresh();
      }
      event.setCancelled(true);
    }
  }

}
