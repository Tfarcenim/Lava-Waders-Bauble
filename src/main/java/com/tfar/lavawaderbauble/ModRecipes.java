package com.tfar.lavawaderbauble;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static com.tfar.lavawaderbauble.AnvilRecipeHandler.*;
import static com.tfar.lavawaderbauble.ModItems.*;

public class ModRecipes {

  private static final String RT_MOD_ID = "randomthings";

  private static Item obsidianSkullRing = ForgeRegistries.ITEMS.getValue(new ResourceLocation(RT_MOD_ID,"obsidianskullring"));
  private static Item obsidianSkull = ForgeRegistries.ITEMS.getValue(new ResourceLocation(RT_MOD_ID,"obsidianskull"));
  private static Item lavaCharm = ForgeRegistries.ITEMS.getValue(new ResourceLocation(RT_MOD_ID,"lavacharm"));


  public static void register() {
    addAnvilRecipe(new ItemStack(waterWalkingBootsBauble), new ItemStack(obsidianSkull), new ItemStack(obsidianWaterWalkingBootsBauble), 10);
    addAnvilRecipe(new ItemStack(waterWalkingBootsBauble), new ItemStack(obsidianSkullRing), new ItemStack(obsidianWaterWalkingBootsBauble), 10);
    addAnvilRecipe(new ItemStack(obsidianWaterWalkingBootsBauble), new ItemStack(lavaCharm), new ItemStack(lavaWaderBauble), 15);
  }
}
