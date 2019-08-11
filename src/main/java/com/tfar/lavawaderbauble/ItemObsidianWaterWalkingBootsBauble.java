package com.tfar.lavawaderbauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(
        iface = "baubles.api.IBauble",
        modid = "baubles"
)
public class ItemObsidianWaterWalkingBootsBauble extends ItemBase implements IBauble {
  public ItemObsidianWaterWalkingBootsBauble() {
    super("obsidianwaterwalkingbootsbauble");
    this.setMaxStackSize(1);
  }

  @Override
  public EnumRarity getRarity(ItemStack stack)
  {
    return EnumRarity.RARE;
  }

  @Override
  public BaubleType getBaubleType(ItemStack itemstack)
  {
    return BaubleType.TRINKET;
  }

  @Override
  public void onEquipped(ItemStack itemstack, EntityLivingBase player)
  {
  }

  @Override
  public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
  {
  }

  @Override
  public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
  {
    return true;
  }

  @Override
  public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
  {
    return true;
  }

  @Override
  public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player)
  {
    return true;
  }
}