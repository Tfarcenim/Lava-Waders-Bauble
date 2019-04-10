package com.tfar.lavawaderbauble;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemModel {
  public static void register() {
    registerItem(ModItem.lavaWaderBauble);
  }
  private static void registerItem(Item i)
  {
    ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
  }
}
