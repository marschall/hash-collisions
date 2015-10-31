package com.github.marschall.hashcollisions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gs.collections.api.map.primitive.MutableIntIntMap;
import com.gs.collections.api.map.primitive.MutableIntObjectMap;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.tuple.primitive.IntIntPair;
import com.gs.collections.impl.factory.Sets;
import com.gs.collections.impl.factory.primitive.IntIntMaps;
import com.gs.collections.impl.factory.primitive.IntObjectMaps;

public class CollisionFinder {
  // https://github.com/emboss/schadcode

  public static void main(String[] args) {
//    findByCountOnly3();
    findByhashCode2(1216347);
//    System.out.printf("%04H%n", 127);
  }

  private static String toHex(char c) {
    String hexString = Integer.toHexString(c);
    switch (hexString.length()) {
      case 4:
        return hexString;
      case 3:
        return "0" + hexString;
      case 2:
        return "00" + hexString;
      case 1:
        return "000" + hexString;
      default:
        throw new IllegalArgumentException("unsupported string: " + hexString);
    }
  }

  private static void findByhashCode2(int hashCode) {

    MutableSet<String> collisions = Sets.mutable.empty();
    for (char i = 0; i < Character.MAX_VALUE; i++) {
      if (!Character.isSurrogate(i)) {
        for (char j = 0; j < Character.MAX_VALUE; j++) {
          if (!Character.isSurrogate(j)) {
            String string = new String(new char[]{(char) i, (char) j});
            if (string.hashCode() == hashCode) {
              collisions.add(string);
            }
          }

        }
      }
    }

    System.out.println(collisions.size());
    for (String collision : collisions) {
      System.out.println("\"\\u" + toHex(collision.charAt(0)) + "\\u" + toHex(collision.charAt(1)) + "\",");
    }
  }

  private static void findByhashCode3(int hashCode) {

    MutableSet<String> collisions = Sets.mutable.empty();
    for (char i = 0; i < Character.MAX_VALUE; i++) {
      if (!Character.isSurrogate(i)) {
        for (char j = 0; j < Character.MAX_VALUE; j++) {
          if (!Character.isSurrogate(j)) {
            for (char k = 0; k < Character.MAX_VALUE; k++) {
              if (!Character.isSurrogate(k)) {
                String string = new String(new char[]{(char) i, (char) j, (char) k});
                if (string.hashCode() == hashCode) {
                  collisions.add(string);
                }
              }
            }
          }

        }
      }
    }

    System.out.println(collisions.size());
    for (String collision : collisions) {
      System.out.println("\"\\u" + toHex(collision.charAt(0)) + "\\u" + toHex(collision.charAt(1)) + "\\u" + toHex(collision.charAt(2)) + "\",");
    }
  }

  private static void findByCountOnly2() {
    MutableIntIntMap counts = IntIntMaps.mutable.empty();
    //IntStream chars = IntStream.rangeClosed(0, Character.MAX_VALUE);
    for (char i = 0; i < Character.MAX_VALUE; i++) {
      if (!Character.isSurrogate(i)) {
        for (char j = 0; j < Character.MAX_VALUE; j++) {
          if (!Character.isSurrogate(j)) {
            String string = new String(new char[]{(char) i, (char) j});
            int key = string.hashCode();
            int count = counts.getIfAbsent(key, 0);
            counts.put(key, count + 1);
          }
        }
      }
    }

    int maxCount = 0;
    int maxHashCode = 0;
    Iterator<IntIntPair> iterator = counts.keyValuesView().iterator();
    while (iterator.hasNext()) {
      IntIntPair pair = iterator.next();
      int hashCode = pair.getOne();
      int count = pair.getTwo();
      if (count > maxCount) {
        maxCount = count;
        maxHashCode = hashCode;
      }
    }
    System.out.println("hashCode: " + maxHashCode + " count: " + maxCount);
  }

  private static void findByCountOnly3() {
    MutableIntIntMap counts = IntIntMaps.mutable.empty();
    //IntStream chars = IntStream.rangeClosed(0, Character.MAX_VALUE);
    for (char i = 0; i < Character.MAX_VALUE; i++) {
      if (!Character.isSurrogate(i)) {
        for (char j = 0; j < Character.MAX_VALUE; j++) {
          if (!Character.isSurrogate(j)) {
            for (char k = 0; k < Character.MAX_VALUE; k++) {
              if (!Character.isSurrogate(k)) {
                String string = new String(new char[]{(char) i, (char) j, (char) k});
                int key = string.hashCode();
                int count = counts.getIfAbsent(key, 0);
                counts.put(key, count + 1);
              }
            }
          }
        }
      }
    }

    int maxCount = 0;
    int maxHashCode = 0;
    Iterator<IntIntPair> iterator = counts.keyValuesView().iterator();
    while (iterator.hasNext()) {
      IntIntPair pair = iterator.next();
      int hashCode = pair.getOne();
      int count = pair.getTwo();
      if (count > maxCount) {
        maxCount = count;
        maxHashCode = hashCode;
      }
    }
    System.out.println("hashCode: " + maxHashCode + " count: " + maxCount);
  }

  private static void findAscii() {
    MutableIntObjectMap<List<String>> counts = IntObjectMaps.mutable.empty();
    //IntStream chars = IntStream.rangeClosed(0, Character.MAX_VALUE);
    for (int i = 0; i < 255; i++) {
      for (int j = 0; j < 255; j++) {
        for (int k = 0; k < 255; k++) {
          String string = new String(new char[]{(char) i, (char) j, (char) k});
          List<String> list = counts.getIfAbsentPut(string.hashCode(), () -> new ArrayList<>(1));
          list.add(string);
        }
      }

    }

    int max = counts.keySet().max();
    List<String> collisions = counts.get(max);
    System.out.println(max + ": " + collisions + " (" + collisions.size() + ")");
  }

}
