package com.tfar.lavawaderbauble;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class ItemLavaWaderBauble extends Item {
  public ItemLavaWaderBauble(Properties properties) {
    super(properties);
  }

  @Override
  public Rarity getRarity(ItemStack stack) {
    return Rarity.EPIC;
  }

  @Override
  public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    if (entityIn instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) entityIn;
      if (!player.world.isRemote) {
        CompoundNBT compound = stack.getOrCreateTag();

        int chargeCooldown = compound.getInt("chargeCooldown");
        if (chargeCooldown > 0) {
          compound.putInt("chargeCooldown", --chargeCooldown);
        } else {
          int charge = compound.getInt("charge");
          if (charge < 200) {
            compound.putInt("charge", ++charge);
          }
        }
      }
    }
  }
}