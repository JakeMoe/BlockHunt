package me.JohnMoe.BlockHunt;

import io.loyloy.nicky.Nick;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

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

  static Map<UUID, Integer> sortTreeMapByValue(TreeMap<UUID, Integer> unsortedMap) {

    List<Map.Entry<UUID, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

    Collections.sort(list, new Comparator<Map.Entry<UUID, Integer>>() {
      @Override
      public int compare(Map.Entry<UUID, Integer> o1, Map.Entry<UUID, Integer> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    Map<UUID, Integer> sortedMap = new LinkedHashMap<>();
    for (Map.Entry<UUID, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;

  }

}
