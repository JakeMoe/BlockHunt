package me.JohnMoe.BlockHunt;

import java.util.*;

class Util {

  static Map<UUID, Integer> sortTreeMapByValue(TreeMap<UUID, Integer> unsortedMap) {

    List<Map.Entry<UUID, Integer>> list = new LinkedList<Map.Entry<UUID, Integer>>(unsortedMap.entrySet());

    Collections.sort(list, new Comparator<Map.Entry<UUID, Integer>>() {
      @Override
      public int compare(Map.Entry<UUID, Integer> o1, Map.Entry<UUID, Integer> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    Map<UUID, Integer> sortedMap = new LinkedHashMap<UUID, Integer>();
    for (Map.Entry<UUID, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;

  }

}
