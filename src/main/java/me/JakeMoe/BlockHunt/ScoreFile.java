package me.JakeMoe.BlockHunt;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class ScoreFile {

  private Main plugin;

  ScoreFile(Main plugin) {
    this.plugin = plugin;
  }

  void save(String filePath, String fileName, HashMap<UUID, Integer> scores) {
    File scoreFile = new File(filePath, fileName + ".yml");
    if (!scoreFile.exists()) {
      try {
        scoreFile.createNewFile();
      } catch (IOException e) {

      }
    }

    YamlConfiguration yc = new YamlConfiguration();
    Iterator<Map<UUID, Integer>> iterator = scores.entrySet().iterator();

  }

}
