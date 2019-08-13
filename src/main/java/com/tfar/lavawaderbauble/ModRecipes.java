package com.tfar.lavawaderbauble;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ObjectHolder;

import static com.tfar.lavawaderbauble.AnvilRecipeHandler.addAnvilRecipe;
import static com.tfar.lavawaderbauble.ModRecipes.RTItems.*;

public class ModRecipes {

  private static final String RT_MOD_ID = "randomthings";

  @ObjectHolder(RT_MOD_ID)
  static class RTItems {
    static final Item obsidianskullring = null;
    static final Item obsidianskull = null;
    static final Item lavacharm = null;
  }

  public static void registerCompat() {
    addAnvilRecipe(new ItemStack(LavaWaderBauble.Objects.waterWalkingBootsBauble), new ItemStack(obsidianskull), new ItemStack(LavaWaderBauble.Objects.obsidianWaterWalkingBootsBauble), 10);
    addAnvilRecipe(new ItemStack(LavaWaderBauble.Objects.waterWalkingBootsBauble), new ItemStack(obsidianskullring), new ItemStack(LavaWaderBauble.Objects.obsidianWaterWalkingBootsBauble), 10);
    addAnvilRecipe(new ItemStack(LavaWaderBauble.Objects.obsidianWaterWalkingBootsBauble), new ItemStack(lavacharm), new ItemStack(LavaWaderBauble.Objects.lavaWaderBauble), 15);
  }
}
