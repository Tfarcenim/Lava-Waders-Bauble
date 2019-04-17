package com.tfar.lavawaderbauble;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

import static com.tfar.lavawaderbauble.ModItems.*;

public class Utils {

  static Field isJumping;

  static {
    isJumping = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_70703_bu");
  }

  public static ItemStack getBauble(Item item, EntityPlayer player) {
    IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);

    for (int i = 0; i < handler.getSlots(); i++) {
      ItemStack is = handler.getStackInSlot(i);
      if (!is.isEmpty() && is.getItem() == item)
        return is;
    }

    return ItemStack.EMPTY;
  }

  public static ItemStack getBaubles(EntityPlayer player){
    ItemStack boots = getBauble(lavaWaderBauble, player);

    if (boots.isEmpty())
      boots = getBauble(waterWalkingBootsBauble, player);

    if (boots.isEmpty())
      boots = getBauble(obsidianWaterWalkingBootsBauble, player);
    return boots;
  }

  public static boolean isJumping(EntityLivingBase player) throws IllegalAccessException {
      return isJumping.getBoolean(player);
    }
}
