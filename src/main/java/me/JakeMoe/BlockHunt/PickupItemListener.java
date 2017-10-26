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

  @EventHandler
  void onPlayerPickupItem(EntityPickupItemEvent e) {
    ItemStack itemStack = new ItemStack(Material.POTION, 1);
    if ((e.getEntity() instanceof Player) && (e.getItem().getItemStack().getType().equals(itemStack.getType()))) {
//      ((Player) e.getEntity()).playSound(e.getEntity().getLocation(), Sound.ENTITY_SPLASH_POTION_THROW, 1, 1);
      ((Player) e.getEntity()).playSound(e.getEntity().getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
      PotionMeta meta = (PotionMeta) e.getItem().getItemStack().getItemMeta();
      for (PotionEffect effect : meta.getCustomEffects()) {
        e.getEntity().addPotionEffect(effect);
      }
      e.getItem().remove();
      e.setCancelled(true);
    }
  }

}
