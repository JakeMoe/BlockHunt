package me.JohnMoe.BlockHunt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

  private Main plugin;

  public DamageListener(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerDamage(EntityDamageEvent event) {
    if (plugin.isTimerRunning() &&
        plugin.getGameRegion().getRegion().contains(event.getEntity().getLocation().getBlockX(), event.getEntity().getLocation().getBlockY(), event.getEntity().getLocation().getBlockZ()) &&
        event.getEntity() instanceof Player &&
        ((Player) event.getEntity()).getHealth() < event.getFinalDamage()) {
      plugin.getGameRegion().removePlayer((Player) event.getEntity());
      event.setCancelled(true);
    }

  }

}