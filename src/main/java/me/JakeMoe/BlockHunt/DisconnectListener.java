package me.JakeMoe.BlockHunt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class DisconnectListener implements Listener {

  Main plugin;

  DisconnectListener(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  void onPlayerQuit(PlayerQuitEvent event) {
    if (plugin.getLobbyRegion().getPlayers().contains(event.getPlayer())) {
      plugin.getLobbyRegion().removePlayer(event.getPlayer());
    }
    if (plugin.getGameRegion().getPlayers().contains(event.getPlayer())) {
      plugin.getGameRegion().removePlayer(event.getPlayer());
    }
  }

  @EventHandler
  void onPlayerKick(PlayerKickEvent event) {
    if (plugin.getLobbyRegion().getPlayers().contains(event.getPlayer())) {
      plugin.getLobbyRegion().removePlayer(event.getPlayer());
    }
    if (plugin.getGameRegion().getPlayers().contains(event.getPlayer())) {
      plugin.getGameRegion().removePlayer(event.getPlayer());
    }
  }

}
