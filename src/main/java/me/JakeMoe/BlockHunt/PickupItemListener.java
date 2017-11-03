package me.JakeMoe.BlockHunt;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

class PickupItemListener implements Listener {

  Main plugin;

  PickupItemListener(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  void onPlayerPickupItem(EntityPickupItemEvent event) {
    ItemStack itemStack = new ItemStack(Material.POTION, 1);
    if (plugin.getGameRegion().getRegion().contains((int) event.getEntity().getLocation().getX(), (int) event.getEntity().getLocation().getY(), (int) event.getEntity().getLocation().getZ()) &&
        (event.getEntity() instanceof Player) &&
        (event.getItem().getItemStack().getType().equals(itemStack.getType()))) {
      ((Player) event.getEntity()).playSound(event.getEntity().getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
      PotionMeta meta = (PotionMeta) event.getItem().getItemStack().getItemMeta();
      for (PotionEffect effect : meta.getCustomEffects()) {
        event.getEntity().addPotionEffect(effect);
      }
      event.getEntity().sendMessage("You got " + meta.getDisplayName());
      plugin.getGameManager().getPotionsDropped().remove(event.getItem());
      event.getItem().remove();
      event.setCancelled(true);
    }
  }

}
