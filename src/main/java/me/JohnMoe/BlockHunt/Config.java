package me.JohnMoe.BlockHunt;

import org.bukkit.Material;
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

    if (!fileConfig.contains("BlockHunt.areas.game.Region")) {
      fileConfig.set("BlockHunt.areas.game.Region", "BlockHuntGame");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.game.World")) {
      fileConfig.set("BlockHunt.areas.game.World", "world");
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

  public void saveConfig() {
    plugin.saveConfig();
  }

  public String getEndMessage() {
    return fileConfig.getString("BlockHunt.messages.End");
  }

  public void setEndMessage(String message) {
    fileConfig.set("BlockHunt.messages.End", message);
    plugin.saveConfig();
  }

  public int getHuntDuration() {
    return Integer.valueOf(fileConfig.getString("BlockHunt.settings.huntDuration"));
  }

  public void setHuntDuration(String seconds) {
    fileConfig.set("BlockHunt.settings.huntDuration", seconds);
    plugin.saveConfig();
  }

  public String getGameRegion() {
    return fileConfig.getString("BlockHunt.areas.game.Region");
  }

  public void setGameRegion(String region) {
    fileConfig.set("BlockHunt.areas.game.Region", region);
    plugin.saveConfig();
    plugin.getGameRegion().updateRegion();
  }

  public String getGameWorld() {
    return fileConfig.getString("BlockHunt.areas.game.World");
  }

  public void setGameWorld(String world) {
    fileConfig.set("BlockHunt.areas.game.World", world);
    plugin.saveConfig();
    plugin.getLobbyRegion().updateRegion();
  }

  public String getLobbyRegion() {
    return fileConfig.getString("BlockHunt.areas.lobby.Region");
  }

  public void setLobbyRegion(String region) {
    fileConfig.set("BlockHunt.areas.lobby.Region", region);
    plugin.saveConfig();
    plugin.getLobbyRegion().updateRegion();
  }

  public String getLobbyWorld() {
    return fileConfig.getString("BlockHunt.areas.lobby.World");
  }

  public void setLobbyWorld(String world) {
    fileConfig.set("BlockHunt.areas.lobby.World", world);
    plugin.saveConfig();
    plugin.getLobbyRegion().updateRegion();
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