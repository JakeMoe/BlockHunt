package me.JohnMoe.BlockHunt;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Config {

  private Main plugin;
  private FileConfiguration fileConfig;

  Config(Main plugin) {
    this.plugin = plugin;
  }

  public void loadConfig() {

    boolean booChanged = false;

    fileConfig = plugin.getConfig();


    if (!fileConfig.contains("BlockHunt.areas.arena.Region")) {
      fileConfig.set("BlockHunt.areas.arena.Region", "BlockHuntArena");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.arena.World")) {
      fileConfig.set("BlockHunt.areas.arena.World", "world");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Region")) {
      fileConfig.set("BlockHunt.areas.lobby.Region", "BlockHuntLobby");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.World")) {
      fileConfig.set("BlockHunt.areas.lobby.World", "world");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.messages.End")) {
      fileConfig.set("BlockHunt.messages.End", "The Hunt has ended!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.messages.ScoreboardTitle")) {
      fileConfig.set("BlockHunt.messages.ScoreboardTitle", "TOP HUNTERS");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.messages.Start")) {
      fileConfig.set("BlockHunt.messages.Start", "The Hunt has begun!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.messages.Stop")) {
      fileConfig.set("BlockHunt.messages.Stop", "The Hunt has been stopped!");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.prizes")) {
      List<String[]> prizes = new ArrayList<>();
      String[] prize = new String[2];
      prize[0] = "everyone's adoration";
      prize[1] = "say %player% has won the Hunt! Everyone give them a round of applause!";
      prizes.add(prize);
      fileConfig.set("BlockHunt.settings.prizes", prizes);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.huntDuration")) {
      fileConfig.set("BlockHunt.settings.huntDuration", "30");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.materialToFind")) {
      fileConfig.set("BlockHunt.settings.materialToFind", "PUMPKIN");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.useNicky")) {
      if (plugin.getNickyPlugin() == null) {
        fileConfig.set("BlockHunt.settings.useNicky", "false");
      } else {
        fileConfig.set("BlockHunt.settings.useNicky", "true");
      }
      booChanged = true;
    }

    if (booChanged) {
      this.plugin.saveConfig();
    }

  }

  void saveConfig() {
    plugin.saveConfig();
  }

  String getEndMessage() {
    return fileConfig.getString("BlockHunt.messages.End");
  }

  void setEndMessage(String message) {
    fileConfig.set("BlockHunt.messages.End", message);
    plugin.saveConfig();
  }

  int getHuntDuration() {
    return Integer.valueOf(fileConfig.getString("BlockHunt.settings.huntDuration"));
  }

  void setHuntDuration(String seconds) {
    fileConfig.set("BlockHunt.settings.huntDuration", seconds);
    plugin.saveConfig();
  }

  ProtectedRegion getLobbyRegion() {
    return plugin.getWorldGuardPlugin().getRegionManager(getHuntWorld()).getRegion(fileConfig.getString("BlockHunt.areas.lobby.Region"));
  }

  void setLobbyRegion(String region) {
    fileConfig.set("BlockHunt.areas.lobby.Region", region);
    plugin.saveConfig();
  }

  World getLobbyWorld() {
    return org.bukkit.Bukkit.getWorld(fileConfig.getString("BlockHunt.areas.lobby.World"));
  }

  void setLobbyWorld(String world) {
    fileConfig.set("BlockHunt.areas.lobby.World", world);
    plugin.saveConfig();
  }

  public ProtectedRegion getArenaRegion() {
    return plugin.getWorldGuardPlugin().getRegionManager(getHuntWorld()).getRegion(fileConfig.getString("BlockHunt.areas.arena.Region"));
  }

  public void setArenaRegion(String region) {
    fileConfig.set("BlockHunt.areas.arena.Region", region);
    plugin.saveConfig();
  }

  World getHuntWorld() {
    return org.bukkit.Bukkit.getWorld(fileConfig.getString("BlockHunt.areas.arena.World"));
  }

  void setHuntWorld(String world) {
    fileConfig.set("BlockHunt.areas.arena.World", world);
    plugin.saveConfig();
  }

  Material getMaterial() {
    return Material.valueOf(fileConfig.getString("BlockHunt.settings.materialToFind"));
  }

  void setMaterial(String material) {
    fileConfig.set("BlockHunt.settings.materialToFind", material);
    plugin.saveConfig();
  }

  ArrayList<String[]> getPrizes() {
    return (ArrayList<String[]>) fileConfig.get("BlockHunt.prizes");
  }

  String getScoreboardTitle() {
    return fileConfig.getString("BlockHunt.messages.ScoreboardTitle");
  }

  void setScoreboardTitle(String scoreboardTitle) {
    fileConfig.set("BlockHunt.messages.ScoreboardTitle", scoreboardTitle);
    plugin.saveConfig();
  }

  String getStartMessage() {
    return fileConfig.getString("BlockHunt.messages.Start");
  }

  void setStartMessage(String message) {
    fileConfig.set("BlockHunt.messages.Start", message);
    plugin.saveConfig();
  }

  String getStopMessage() {
    return fileConfig.getString("BlockHunt.messages.Stop");
  }

  void setStopMessage(String message) {
    fileConfig.set("BlockHunt.messages.Stop", message);
    plugin.saveConfig();
  }

  boolean isNickyEnabled() {
    return fileConfig.getBoolean("BlockHunt.settings.useNicky");
  }

  void setNickyEnabled(boolean enabled) {
    fileConfig.set("BlockHunt.settings.useNicky", enabled);
    plugin.saveConfig();
  }

}