package me.JohnMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

class HitListener implements Listener {

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
            (event.getClickedBlock().getType() != plugin.getPluginConfig().getMaterial())) {
          event.setCancelled(true);
        }
      }
    }
  }

}
