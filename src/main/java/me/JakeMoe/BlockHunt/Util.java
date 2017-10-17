package me.JakeMoe.BlockHunt;

import io.loyloy.nicky.Nick;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

class Util {

  static String getNameByUUID(UUID uuid, boolean nickyEnabled) {
    Player player = Bukkit.getPlayer(uuid);
    String name = null;
    if (nickyEnabled) {
      name = (new Nick(player)).get();
    }
    if (name == null) {
      name = Bukkit.getOfflinePlayer(uuid).getName();
    }
    return name;
  }

  static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    return map.entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue())
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new
      ));
  }

}
