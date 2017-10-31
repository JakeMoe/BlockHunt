package me.JakeMoe.BlockHunt;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

class GameManager {

  private Main plugin;
  private ArrayList<Location> blockLocations;
  private ArrayList<ItemStack> potionsDropped;
  private BukkitRunnable potionTimer;
  private BukkitRunnable pumpkinTimer;
  private boolean stopTimer;

  class PotionTimer extends BukkitRunnable {
    @Override
    public void run() {
      if (stopTimer) {
        potionTimer.cancel();
        potionTimer = null;
      } else {
        dropPotion();
        potionTimer = new PotionTimer();
        potionTimer.runTaskLater(plugin, (new Random().nextInt(100)) + 100);
      }
    }
  }

  class PumpkinTimer extends BukkitRunnable {
    @Override
    public void run() {
      if (stopTimer) {
        pumpkinTimer.cancel();
        pumpkinTimer = null;
      } else {
        dropRandomBlock();
        pumpkinTimer = new PumpkinTimer();
        pumpkinTimer.runTaskLater(plugin, (new Random().nextInt(100)) + 100);
      }
    }
  }

  GameManager(Main plugin) {
    this.plugin = plugin;
    this.blockLocations = null;
    this.potionsDropped = null;
  }

  private void dropPotion() {

    ItemStack itemStack = new ItemStack(Material.POTION, 1);
    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
    PotionEffect potionEffect;

    int duration = 10;

    switch ((new Random()).nextInt(10)) {
      case 0:
        potionEffect = new PotionEffect(PotionEffectType.ABSORPTION, duration * 20, 0, true, true);
        break;
      case 1:
        potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, duration * 20, 0, true, true);
        break;
      case 2:
        potionEffect = new PotionEffect(PotionEffectType.CONFUSION, duration * 20, 0, true, true);
        break;
      case 3:
        potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration * 20, 0, true, true);
        break;
      case 4:
        potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, duration * 20, 0, true, true);
        break;
      case 5:
        potionEffect = new PotionEffect(PotionEffectType.HARM, 1, 0, true, true);
        break;
      case 6:
        potionEffect = new PotionEffect(PotionEffectType.HEAL, 1, 0, true, true);
        break;
      case 7:
        potionEffect = new PotionEffect(PotionEffectType.JUMP, duration * 20, 0, true, true);
        break;
      case 8:
        potionEffect = new PotionEffect(PotionEffectType.SLOW, duration * 20, 0, true, true);
        break;
      case 9:
        potionEffect = new PotionEffect(PotionEffectType.SPEED, duration * 20, 0, true, true);
        break;
      default:
        potionEffect = new PotionEffect(PotionEffectType.GLOWING, duration * 20, 0, true, true);
        break;
    }

    potionMeta.addCustomEffect(potionEffect, true);
    itemStack.setItemMeta(potionMeta);

    Location randomLocation = plugin.getGameRegion().getRandomLocation();
    plugin.getGameRegion().getWorld().strikeLightningEffect(randomLocation);
    plugin.getGameRegion().getWorld().dropItem(randomLocation, itemStack);
    potionsDropped.add(itemStack);

  }

  private void dropRandomBlock() {
    Location location = plugin.getGameRegion().getRandomLocation();
    plugin.getGameRegion().getWorld().getBlockAt(location).setType(plugin.getPluginConfig().getMaterial());
    blockLocations.add(location);
  }

  private void end() {

    stopTimer = true;
    plugin.clearGameTimer();

    plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] The hunt has finished");
    plugin.getServer().broadcastMessage(plugin.getPluginConfig().getEndMessage());

    saveScores(plugin.getAllScores());

    Map<UUID, Integer> sortedMap = Util.sortByValue(plugin.getAllScores());
    UUID winner = null;
    for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
      winner = entry.getKey();
    }

    String playerName = Util.getNameByUUID(winner, plugin.getPluginConfig().isNickyEnabled());
    plugin.getServer().broadcastMessage(playerName + " has won the Hunt!");

    if (blockLocations != null) {
      for (Location blockLocation : blockLocations) {
        plugin.getGameRegion().getWorld().getBlockAt(blockLocation).setType(Material.AIR);
      }
    }

    if (potionsDropped != null) {
      for (ItemStack potion : potionsDropped) {
        potion.setType(Material.AIR);
      }
    }

    plugin.getScoreboard().clear();
    plugin.resetScore();

    plugin.getGameRegion().removePlayers();

  }

  ArrayList<ItemStack> getPotionsDropped() {
    return potionsDropped;
  }

  private void resetBlocks() {

    blockLocations = new ArrayList<>();
    for (int i = 0; i < plugin.getPluginConfig().getGameNumBlocks(); i++) {
      Location location = plugin.getGameRegion().getRandomLocation();
      while (blockLocations.contains(location)) {
        location = plugin.getGameRegion().getRandomLocation();
      }
      blockLocations.add(location);
      plugin.getGameRegion().getWorld().getBlockAt(location).setType(plugin.getPluginConfig().getMaterial());
    }

  }

  private void saveScores(HashMap<UUID, Integer> scores) {
    File scoreFile = new File(plugin.getDataFolder() + "/scores/", (new SimpleDateFormat("yyMMdd-hhmmss")).format(new Date()) + ".yml");
    if (!scoreFile.exists()) {
      try {
        scoreFile.createNewFile();
      } catch (IOException e) {
        plugin.getLogger().log(Level.INFO, e.getMessage());
      }
    }

    YamlConfiguration yc = new YamlConfiguration();
    for (Object object : scores.entrySet()) {
      Map.Entry entry = (Map.Entry) object;
      yc.set(entry.getKey().toString(), entry.getValue());
    }

    try {
      yc.save(scoreFile);
    } catch (IOException e) {
      plugin.getLogger().log(Level.INFO, e.getMessage());
    }
  }

  void start() {
    if (plugin.getGameTimer() == null) {
      plugin.getScoreboard().reset();
      plugin.getScoreboard().refresh();
      resetBlocks();

      plugin.startGameTimer(new BukkitRunnable() {
        @Override
        public void run() {
          end();
        }
      });

      stopTimer = false;

      potionTimer = new PotionTimer();
      potionTimer.runTaskLater(plugin, (new Random().nextInt(100)) + 100);

      pumpkinTimer = new PumpkinTimer();
      pumpkinTimer.runTaskLater(plugin, (new Random().nextInt(100)) + 100);

    } else {
      plugin.getServer().broadcastMessage("A game is already in progress.");
    }
  }

  void stop() {
    if (plugin.getGameTimer() == null) {
      plugin.getServer().broadcastMessage("The game has not yet begun!");
    } else {

      stopTimer = true;
      plugin.clearGameTimer();

      plugin.getServer().getLogger().log(Level.INFO, "[BlockHunt] Stopping the hunt");
      plugin.getServer().broadcastMessage(plugin.getPluginConfig().getStopMessage());
      plugin.getGameTimer().cancel();

      if (!(blockLocations== null)) {
        for (Location blockLocation : blockLocations) {
          plugin.getGameRegion().getWorld().getBlockAt(blockLocation).setType(Material.AIR);
        }
      }

      if (potionsDropped != null) {
        for (ItemStack potion : potionsDropped) {
          potion.setType(Material.AIR);
        }
      }

      plugin.getScoreboard().clear();
      plugin.resetScore();

      plugin.getGameRegion().removePlayers();
    }
  }

}
