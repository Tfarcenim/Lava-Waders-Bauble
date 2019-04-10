package com.tfar.lavawaderbauble;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class ItemBase extends Item{

  public ItemBase(String name)
  {
    registerItem(name, this);
  }

  public static void registerItem(String name, Item item)
  {
    item.setRegistryName(name);
    item.setCreativeTab(CreativeTabs.REDSTONE);
    item.setTranslationKey(name);
    ForgeRegistries.ITEMS.register(item);
  }
}
