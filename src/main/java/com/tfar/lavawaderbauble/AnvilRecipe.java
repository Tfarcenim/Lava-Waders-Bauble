package com.tfar.lavawaderbauble;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;
import java.util.function.Predicate;

public class AnvilRecipe
{
  final Pair<ItemStack, ItemStack> inputPair;
  final ItemStack output;
  final int cost;

  public AnvilRecipe(ItemStack input1, ItemStack input2, ItemStack output, int cost)
  {
    this.inputPair = Pair.of(input1, input2);
    this.output = output;
    this.cost = cost;
  }

  public ItemStack getOutput()
  {
    return output;
  }

  public ItemStack getSecond()
  {
    return inputPair.getRight();
  }

  public ItemStack getFirst()
  {
    return inputPair.getLeft();
  }

  public int getCost()
  {
    return cost;
  }

}
