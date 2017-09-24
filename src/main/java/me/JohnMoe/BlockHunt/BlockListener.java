package me.JohnMoe.BlockHunt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

  @EventHandler
  public void onHit(PlayerInteractEvent event) {
    Player player = event.getPlayer();
  }

}
