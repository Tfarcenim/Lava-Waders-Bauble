package com.tfar.lavawaderbauble;

import lumien.randomthings.lib.IEntityFilterItem;
import lumien.randomthings.util.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

import static com.tfar.lavawaderbauble.ModItem.lavaWaderBauble;

public class LavaWaderBaubleEventHandler {

  static Random rng = new Random();

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void renderGameOverlay(RenderGameOverlayEvent event) {
    if (event.getType() != null && event instanceof RenderGameOverlayEvent.Post && event.getType() == RenderGameOverlayEvent.ElementType.ARMOR)
      renderLavaCharm(event);
  }

  @SideOnly(Side.CLIENT)
  private void renderLavaCharm(RenderGameOverlayEvent event) {
    ItemStack lavaProtector = ItemStack.EMPTY;

    EntityPlayerSP player = Minecraft.getMinecraft().player;

    ItemStack lavaWaderBauble = InventoryUtils.getBauble(ModItem.lavaWaderBauble, player);

    if (!lavaWaderBauble.isEmpty())
      lavaProtector = lavaWaderBauble;

    if (!lavaProtector.isEmpty()) {
      NBTTagCompound compound = lavaProtector.getTagCompound();
      if (compound != null) {
        float charge = compound.getInteger("charge");
        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(new ResourceLocation("randomthings:textures/gui/lavaCharmBar.png"));
        GuiIngame ingameGui = mc.ingameGUI;

        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();

        int count = (int) Math.floor(charge / 2F / 10F);

        int left = 0;

        int top = height - GuiIngameForge.left_height - 1;
        GuiIngameForge.left_height += 10;

        GlStateManager.enableBlend();
        for (int i = 0; i < count + 1; i++) {
          if (i == count + 1 - 1) {
            float countFloat = charge / 2F / 10F + 10f;
            GlStateManager.color(1, 1, 1, (countFloat) % ((int) (countFloat)));
          }

          ingameGui.drawTexturedModalRect(width / 2 - 92 + left, top, 0, 0, 10, 10);
          left += 8;
          GlStateManager.color(1, 1, 1, 1);
        }
        mc.renderEngine.bindTexture(Gui.ICONS);
        GlStateManager.disableBlend();
      }
    }
  }

  private void handleLavaProtection(LivingAttackEvent event) {
    ItemStack lavaProtector = ItemStack.EMPTY;
    ItemStack lavaCharm = ItemStack.EMPTY;

    lavaCharm = InventoryUtils.getBauble(lavaWaderBauble, (EntityPlayer) event.getEntityLiving());


    if (!lavaCharm.isEmpty()) {
      lavaProtector = lavaCharm;
    }

    if (!lavaProtector.isEmpty()) {
      NBTTagCompound compound = lavaProtector.getTagCompound();
      if (compound != null) {
        int charge = compound.getInteger("charge");
        if (charge > 0) {
          compound.setInteger("charge", charge - 1);
          compound.setInteger("chargeCooldown", 40);
          event.setCanceled(true);
        }
      }
    }
  }

  @SubscribeEvent
  public void livingAttacked(LivingAttackEvent event) {
    if (!event.getEntityLiving().world.isRemote) {
      if (!event.isCanceled() && event.getAmount() > 0 && event.getEntityLiving() instanceof EntityPlayerMP) {

        if (event.getSource() == DamageSource.LAVA) {
          handleLavaProtection(event);
        }

        if (event.getSource().isFireDamage() && event.getSource() != DamageSource.LAVA) {
          handleFireProtection(event);
        }
      }
    }
  }

  private void handleFireProtection(LivingAttackEvent event) {
    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    ItemStack baubleLavaWader = InventoryUtils.getBauble(lavaWaderBauble, player);

    if (!(baubleLavaWader.getItem() == lavaWaderBauble))
      baubleLavaWader = ItemStack.EMPTY;

    ItemStack skull = baubleLavaWader;

    if (!skull.isEmpty()) event.setCanceled(true);
  }


  @SubscribeEvent
  public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntityLiving() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) event.getEntityLiving();
      if (!player.isSneaking()) {
        ItemStack boots = InventoryUtils.getBauble(lavaWaderBauble, player);
        if (boots.getItem() == lavaWaderBauble) {
          BlockPos liquid = new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
          BlockPos air = new BlockPos((int) player.posX, (int) (player.posY + player.height), (int) player.posZ);
          Block liquidBlock = player.world.getBlockState(liquid).getBlock();
          Material liquidMaterial = liquidBlock.getMaterial(player.world.getBlockState(liquid));

          if ((liquidMaterial == Material.WATER || boots.getItem() == lavaWaderBauble && liquidMaterial == Material.LAVA) && player.world.getBlockState(air).getBlock().isAir(player.world.getBlockState(air), player.world, air) && EntityUtil.isJumping(player)) {
            player.move(MoverType.SELF, 0, 0.22, 0);
          }
        }
      }
    }
  }

  @SubscribeEvent
  public void waterWalking(LivingEvent.LivingUpdateEvent event) {
    Entity entity = event.getEntity();
    if (entity.motionY > 0 && entity instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) entity;

      ItemStack boots = InventoryUtils.getBauble(lavaWaderBauble, player);

      if (boots.getItem() == lavaWaderBauble) {
        AxisAlignedBB bb = player.getEntityBoundingBox();
        AxisAlignedBB feet = new AxisAlignedBB(
                bb.minX,
                bb.minY,
                bb.minZ,
                bb.maxX,
                bb.minY,
                bb.maxZ
        );
        AxisAlignedBB ankles = new AxisAlignedBB(
                bb.minX,
                bb.minY+0.5,
                bb.minZ,
                bb.maxX,
                bb.minY+0.5,
                bb.maxZ
        );
        if (player.world.isMaterialInBB(feet, Material.WATER) &&
                !player.world.isMaterialInBB(ankles, Material.WATER)
        ) {
          player.motionY += 0.05F;
        }
      }
    }
  }

  @SubscribeEvent
  public void getCollisions(@Nonnull GetCollisionBoxesEvent event) {
    Entity entity = event.getEntity();
    if (entity == null || entity.isInWater() || entity.isSneaking() || !(entity instanceof EntityPlayer)) return;

    EntityPlayer player = (EntityPlayer) entity;
    ItemStack boots = InventoryUtils.getBauble(lavaWaderBauble, player);
    if (boots.getItem() != lavaWaderBauble) return;

    AxisAlignedBB entityBoundingBox = player.getEntityBoundingBox();

    World world = event.getWorld();
    for (BlockPos.MutableBlockPos mutableBlockPos : BlockPos.getAllInBoxMutable(
            MathHelper.floor(entityBoundingBox.minX),
            MathHelper.floor(entityBoundingBox.minY - 1),
            MathHelper.floor(entityBoundingBox.minZ),
            MathHelper.ceil(entityBoundingBox.minX),
            MathHelper.floor(entityBoundingBox.minY),
            MathHelper.ceil(entityBoundingBox.minZ)
    )) {
      IBlockState state = world.getBlockState(mutableBlockPos);
      if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER || state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA) {
        AxisAlignedBB bb = new AxisAlignedBB(
                mutableBlockPos.getX(),
                mutableBlockPos.getY(),
                mutableBlockPos.getZ(),
                mutableBlockPos.getX() + 1,
                mutableBlockPos.getY() + 1,
                mutableBlockPos.getZ() + 1);
        if (event.getAabb().intersects(bb)) {
          event.getCollisionBoxesList().add(bb);
        }
      }
    }
  }
}

