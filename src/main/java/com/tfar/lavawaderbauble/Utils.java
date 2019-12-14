package com.tfar.lavawaderbauble;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Utils {

  public static ItemStack getBauble(Item item, PlayerEntity player) {
    return getAllSlots(player).stream().filter(stack -> stack.getItem() == item).findFirst().orElse(ItemStack.EMPTY);
  }

  public static List<ItemStack> getAllSlots(PlayerEntity player) {

    List<ItemStack> list = new ArrayList<>();

    CuriosAPI.getCuriosHandler(player)
            .map(iCurioItemHandler -> iCurioItemHandler.getCurioMap().values()).ifPresent(curioStackHandlers -> curioStackHandlers
                    .forEach(curioStackHandler -> IntStream.range(0, curioStackHandler.getSlots())
                            .filter(i -> curioStackHandler.getStackInSlot(i).isEmpty())
                            .forEach(i -> list.add(curioStackHandler.getStackInSlot(i)))));
    return list;
  }

  public static ItemStack getBaubles(PlayerEntity player) {
    ItemStack boots = getBauble(LavaWaderBauble.Objects.lavaWaderBauble, player);

    if (boots.isEmpty())
      boots = getBauble(LavaWaderBauble.Objects.waterWalkingBootsBauble, player);

    if (boots.isEmpty())
      boots = getBauble(LavaWaderBauble.Objects.obsidianWaterWalkingBootsBauble, player);
    return boots;
  }
}
