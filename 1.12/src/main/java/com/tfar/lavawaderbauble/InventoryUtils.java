package com.tfar.lavawaderbauble;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {

  public static ItemStack getBauble(Item item, EntityPlayer player)
  {
    IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);

    for (int i = 0; i < handler.getSlots(); i++) {
      ItemStack is = handler.getStackInSlot(i);
      if (!is.isEmpty() && is.getItem() == item)
        return is;
    }

    return ItemStack.EMPTY;  }
}
