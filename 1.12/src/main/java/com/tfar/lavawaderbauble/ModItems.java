package com.tfar.lavawaderbauble;

public class ModItems {
  public static ItemLavaWaderBauble lavaWaderBauble;
  public static ItemWaterWalkingBootsBauble waterWalkingBootsBauble;
  public static ItemObsidianWaterWalkingBootsBauble obsidianWaterWalkingBootsBauble;

  public static void load(){
    lavaWaderBauble = new ItemLavaWaderBauble();
    waterWalkingBootsBauble = new ItemWaterWalkingBootsBauble();
    obsidianWaterWalkingBootsBauble = new ItemObsidianWaterWalkingBootsBauble();
  }
}
