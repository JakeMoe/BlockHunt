package me.JohnMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class HitListener implements Listener {

  private Main plugin;

  HitListener(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onHit(PlayerInteractEvent event) {
    if (!(event.getClickedBlock() == null)) {
      if (plugin.isSettingLobbyJoin() &&
          event.getAction() == Action.LEFT_CLICK_BLOCK &&
          !(event.getClickedBlock().getType() == Material.AIR)) {
        plugin.getPluginConfig().setLobbyJoinLocation(event.getClickedBlock().getLocation());
        plugin.setSettingLobbyJoin(false);
      } else if ((event.getAction() == Action.LEFT_CLICK_BLOCK) &&
                 (event.getClickedBlock().getLocation().equals(plugin.getPluginConfig().getLobbyJoinLocation()))) {
        plugin.getLobbyRegion().addPlayer(event.getPlayer());
      } else if (plugin.getGameRegion().getRegion().contains(event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ())) {
        if ((plugin.getGameTimer() != null) &&
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
          event.setCancelled(true);
        }
      }
    }
  }

}
