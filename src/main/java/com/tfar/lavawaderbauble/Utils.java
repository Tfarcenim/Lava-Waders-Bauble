package com.tfar.lavawaderbauble;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Utils {

  static Field isJumping = ObfuscationReflectionHelper.findField(LivingEntity.class, "field_70703_bu");

  public static ItemStack getBauble(Item item, PlayerEntity player) {
    List<ItemStack> items = getAllSlots(player);
    for (ItemStack stack : items)
      if (stack.getItem() == item)
        return stack;
    return ItemStack.EMPTY;
  }

  public static List<ItemStack> getAllSlots(PlayerEntity player) {

    List<ItemStack> list = new ArrayList<>();

    ICurioItemHandler handler = CuriosAPI.getCuriosHandler(player).orElse(null);

    if (handler != null) {

      for (CurioStackHandler value : handler.getCurioMap().values()) {

        for (int i = 0; i < value.getSlots(); i++) {

          ItemStack stack = value.getStackInSlot(i);

          if (!stack.isEmpty()) {
            list.add(stack);
          }
        }
      }
    }
    return list;

  }

  public static ItemStack getBaubles(PlayerEntity player){
    ItemStack boots = getBauble(LavaWaderBauble.Objects.lavaWaderBauble, player);

    if (boots.isEmpty())
      boots = getBauble(LavaWaderBauble.Objects.waterWalkingBootsBauble, player);

    if (boots.isEmpty())
      boots = getBauble(LavaWaderBauble.Objects.obsidianWaterWalkingBootsBauble, player);
    return boots;
  }

  public static boolean isJumping(LivingEntity player) throws IllegalAccessException {
      return isJumping.getBoolean(player);
    }
}
