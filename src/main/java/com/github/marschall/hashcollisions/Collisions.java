package com.github.marschall.hashcollisions;

public class Collisions {

  public static void main(String[] args) {
    String s1 = "\u9103\u9103";
    String s2 = "\u9104\u9104";
    System.out.println(s1.hashCode());
    System.out.println(s2.hashCode());
    System.out.println((s1 + s2).hashCode());
    System.out.println((s2 + s1).hashCode());

    System.out.println((int) s1.charAt(0));
  }

}
