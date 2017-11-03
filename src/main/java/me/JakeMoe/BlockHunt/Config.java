package me.JakeMoe.BlockHunt;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

class Config {

  private Main plugin;
  private FileConfiguration fileConfig;

  Config(Main plugin) {
    this.plugin = plugin;
  }

  void loadConfig() {

    boolean booChanged = false;

    fileConfig = plugin.getConfig();

    if (!fileConfig.contains("BlockHunt.settings.game.blockAddBaseSeconds")) {
      fileConfig.set("BlockHunt.settings.game.blockAddBaseSeconds", 10);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.game.blockAddRandSeconds")) {
      fileConfig.set("BlockHunt.settings.game.blockAddRandSeconds", 10);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.game.duration")) {
      fileConfig.set("BlockHunt.settings.game.duration", "30");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.game.numBlocks")) {
      fileConfig.set("BlockHunt.settings.game.numBlocks", "10");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.game.potionAddBaseSeconds")) {
      fileConfig.set("BlockHunt.settings.game.potionAddBaseSeconds", 10);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.settings.game.potionAddRandSeconds")) {
      fileConfig.set("BlockHunt.settings.game.potionAddRandSeconds", 10);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.game.Region")) {
      fileConfig.set("BlockHunt.areas.game.Region", "BlockHuntGame");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.game.World")) {
      fileConfig.set("BlockHunt.areas.game.World", "world");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Join.X")) {
      fileConfig.set("BlockHunt.areas.lobby.Join.X", 0);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Join.Y")) {
      fileConfig.set("BlockHunt.areas.lobby.Join.Y", 0);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Join.Z")) {
      fileConfig.set("BlockHunt.areas.lobby.Join.Z", 0);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Max")) {
      fileConfig.set("BlockHunt.areas.lobby.Max", 5);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Min")) {
      fileConfig.set("BlockHunt.areas.lobby.Min", 1);
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Region")) {
      fileConfig.set("BlockHunt.areas.lobby.Region", "BlockHuntLobby");
      booChanged = true;
    }

    if (!fileConfig.contains("BlockHunt.areas.lobby.Timer")) {
      fileConfig.set("BlockHunt.areas.lobby.Timer", 10);
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

  int getBlockAddBaseSeconds() {
    return fileConfig.getInt("BlockHunt.settings.game.blockAddBaseSeconds");
  }

  void setBlockAddBaseSeconds(int seconds) {
    fileConfig.set("BlockHunt.settings.game.blockAddBaseSeconds", seconds);
    plugin.saveConfig();
  }

  int getBlockAddRandSeconds() {
    return fileConfig.getInt("BlockHunt.settings.game.blockAddRandSeconds");
  }

  void setBlockAddRandSeconds(int seconds) {
    fileConfig.set("BlockHunt.settings.game.blockAddRandSeconds", seconds);
    plugin.saveConfig();
  }

  String getEndMessage() {
    return fileConfig.getString("BlockHunt.messages.End");
  }

  void setEndMessage(String message) {
    fileConfig.set("BlockHunt.messages.End", message);
    plugin.saveConfig();
  }

  int getGameDuration() {
    return Integer.valueOf(fileConfig.getString("BlockHunt.settings.game.duration"));
  }

  void setGameDuration(int seconds) {
    fileConfig.set("BlockHunt.settings.game.duration", seconds);
    plugin.saveConfig();
  }

  int getGameNumBlocks() {
    return Integer.valueOf(fileConfig.getString("BlockHunt.settings.game.numBlocks"));
  }

  void setGameNumBlocks(int numBlocks) {
    fileConfig.set("BlockHunt.settings.game.numBlocks", numBlocks);
    plugin.saveConfig();
  }

  String getGameRegion() {
    return fileConfig.getString("BlockHunt.areas.game.Region");
  }

  void setGameRegion(String region) {
    fileConfig.set("BlockHunt.areas.game.Region", region);
    plugin.saveConfig();
    plugin.getGameRegion().updateRegion();
  }

  String getGameWorld() {
    return fileConfig.getString("BlockHunt.areas.game.World");
  }

  void setGameWorld(String world) {
    fileConfig.set("BlockHunt.areas.game.World", world);
    plugin.saveConfig();
    plugin.getLobbyRegion().updateRegion();
  }

  int getLobbyDuration() {
    return fileConfig.getInt("BlockHunt.areas.lobby.Timer");
  }

  void setLobbyDuration(int seconds) {
    fileConfig.set("BlockHunt.areas.lobby.Timer", seconds);
    plugin.saveConfig();
  }

  Location getLobbyJoinLocation() {
    int x = Integer.valueOf(fileConfig.getString("BlockHunt.areas.lobby.Join.X"));
    int y = Integer.valueOf(fileConfig.getString("BlockHunt.areas.lobby.Join.Y"));
    int z = Integer.valueOf(fileConfig.getString("BlockHunt.areas.lobby.Join.Z"));
    return new Location(plugin.getServer().getWorld(getLobbyWorld()), x, y, z);
  }

  void setLobbyJoinLocation(Location location) {
    fileConfig.set("BlockHunt.areas.lobby.Join.X", location.getBlockX());
    fileConfig.set("BlockHunt.areas.lobby.Join.Y", location.getBlockY());
    fileConfig.set("BlockHunt.areas.lobby.Join.Z", location.getBlockZ());
    plugin.saveConfig();
  }

  int getLobbyMax() {
    return fileConfig.getInt("BlockHunt.areas.lobby.Max");
  }

  void setLobbyMax(int max) {
    fileConfig.set("BlockHunt.areas.lobby.Max", max);
    plugin.saveConfig();
  }

  int getLobbyMin() {
    return fileConfig.getInt("BlockHunt.areas.lobby.Min");
  }

  void setLobbyMin(int min) {
    fileConfig.set("BlockHunt.areas.lobby.Min", min);
    plugin.saveConfig();
  }

  String getLobbyRegion() {
    return fileConfig.getString("BlockHunt.areas.lobby.Region");
  }

  void setLobbyRegion(String region) {
    fileConfig.set("BlockHunt.areas.lobby.Region", region);
    plugin.saveConfig();
    plugin.getLobbyRegion().updateRegion();
  }

  String getLobbyWorld() {
    return fileConfig.getString("BlockHunt.areas.lobby.World");
  }

  void setLobbyWorld(String world) {
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

  int getPotionAddBaseSeconds() {
    return fileConfig.getInt("BlockHunt.settings.game.potionAddBaseSeconds");
  }

  void setPotionAddBaseSeconds(int seconds) {
    fileConfig.set("BlockHunt.settings.game.potionAddBaseSeconds", seconds);
    plugin.saveConfig();
  }

  int getPotionAddRandSeconds() {
    return fileConfig.getInt("BlockHunt.settings.game.potionAddRandSeconds");
  }

  void setPotionAddRandSeconds(int seconds) {
    fileConfig.set("BlockHunt.settings.game.potionAddRandSeconds", seconds);
    plugin.saveConfig();
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