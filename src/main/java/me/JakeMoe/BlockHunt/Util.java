package me.JakeMoe.BlockHunt;

import io.loyloy.nicky.Nick;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

class Util {

  static String getNameByUUID(UUID uuid, boolean nickyEnabled) {
    String name;
    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
    if (player.isOnline() && nickyEnabled) {
      name = (new Nick(Bukkit.getPlayer(uuid))).get();
    } else {
      name = player.getName();
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
