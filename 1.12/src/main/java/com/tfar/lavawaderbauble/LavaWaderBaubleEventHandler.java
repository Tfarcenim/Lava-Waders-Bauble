package com.tfar.lavawaderbauble;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.tfar.lavawaderbauble.ModItems.*;
import static com.tfar.lavawaderbauble.Utils.*;
import static net.minecraft.util.math.MathHelper.ceil;
import static net.minecraft.util.math.MathHelper.floor;

public class LavaWaderBaubleEventHandler {

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void renderGameOverlay(RenderGameOverlayEvent event) {
    if (event.getType() != null && event instanceof RenderGameOverlayEvent.Post && event.getType() == RenderGameOverlayEvent.ElementType.ALL)
      renderLavaCharm(event);
  }

  @SideOnly(Side.CLIENT)
  private void renderLavaCharm(RenderGameOverlayEvent event) {
    ItemStack lavaProtector = ItemStack.EMPTY;

    EntityPlayerSP player = Minecraft.getMinecraft().player;

    ItemStack lavaWaderBauble = getBauble(ModItems.lavaWaderBauble, player);

    if (!lavaWaderBauble.isEmpty())
      lavaProtector = lavaWaderBauble;

    if (!lavaProtector.isEmpty()) {
      NBTTagCompound compound = lavaProtector.getTagCompound();
      if (compound != null) {
        float charge = compound.getInteger("charge");
        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(new ResourceLocation("lavawaderbauble:textures/gui/lavaCharmBar.png"));
        GuiIngame ingameGui = mc.ingameGUI;

        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();

        int count = (int) Math.floor(charge / 20F);

        int left = 0;
        if(player.getTotalArmorValue()>0)GuiIngameForge.left_height += 10;

        int top = height - GuiIngameForge.left_height - 1;
        GuiIngameForge.left_height += 10;

        GlStateManager.enableBlend();
        for (int i = 0; i < count + 1; i++) {
          if (i == count + 1 - 1) {
            float countFloat = charge / 20F + 10;
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
    ItemStack lavaCharm;

    lavaCharm = getBauble(lavaWaderBauble, (EntityPlayer) event.getEntityLiving());


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
    ItemStack bauble = getBaubles(player);
    if (!bauble.isEmpty() && bauble.getItem() != waterWalkingBootsBauble) event.setCanceled(true);
  }


  @SubscribeEvent
  public void livingUpdate(LivingEvent.LivingUpdateEvent event) throws IllegalAccessException {
    if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    if (player.isSneaking()) return;

    ItemStack boots = getBaubles(player);

    if (boots.isEmpty()) return;
    BlockPos liquid = new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    BlockPos air = new BlockPos((int) player.posX, (int) (player.posY + player.height), (int) player.posZ);
    IBlockState liquidBlockState = player.world.getBlockState(liquid);
    Material liquidMaterial = liquidBlockState.getMaterial();

    if ((liquidMaterial == Material.WATER || (boots.getItem() == lavaWaderBauble && liquidMaterial.isLiquid()) && player.world.getBlockState(air).getBlock().isAir(player.world.getBlockState(air), player.world, air))
            && Utils.isJumping(player)) {
      player.move(MoverType.SELF, 0, 0.22, 0);
    }
  }

  @SubscribeEvent
  public void waterWalking(LivingEvent.LivingUpdateEvent event) {
    Entity entity = event.getEntity();
    if (!(entity.motionY > 0) || !(entity instanceof EntityPlayer)) return;
    EntityPlayer player = (EntityPlayer) entity;

    ItemStack boots = getBaubles(player);

    if (boots.isEmpty()) return;

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
            bb.minY + 0.5,
            bb.minZ,
            bb.maxX,
            bb.minY + 0.5,
            bb.maxZ
    );
    if (player.world.isMaterialInBB(feet, Material.WATER) &&
            !(player.world.isMaterialInBB(ankles, Material.WATER) || (player.world.isMaterialInBB(ankles, Material.LAVA))
    && !(player.world.isMaterialInBB(ankles, Material.LAVA)
    ))) {
      player.motionY += 0.05F;
    }
  }

  @SubscribeEvent
  public void getCollisions(@Nonnull GetCollisionBoxesEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityPlayer) || entity.isInWater() || entity.world.isMaterialInBB(entity.getEntityBoundingBox(), Material.LAVA) || entity.isSneaking()) return;

    EntityPlayer player = (EntityPlayer) entity;

    ItemStack boots = getBaubles(player);

    if (boots.isEmpty()) return;

    AxisAlignedBB entityBoundingBox = player.getEntityBoundingBox();

    World world = event.getWorld();
    for (BlockPos.MutableBlockPos mutableBlockPos : BlockPos.getAllInBoxMutable(
            floor(entityBoundingBox.minX),
            floor(entityBoundingBox.minY - 1),
            floor(entityBoundingBox.minZ),
            ceil(entityBoundingBox.minX),
            floor(entityBoundingBox.minY),
            ceil(entityBoundingBox.minZ)
    )) {
      IBlockState state = world.getBlockState(mutableBlockPos);

      if (state.getMaterial() == Material.WATER || state.getMaterial().isLiquid() && boots.getItem() == lavaWaderBauble) {
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

  @SubscribeEvent
  public void anvilUpdate(AnvilUpdateEvent event){
    if (event.getLeft().isEmpty() || event.getRight().isEmpty()) return;
    AnvilRecipe recipe = AnvilRecipeHandler.getRecipe(event.getLeft(), event.getRight());

    if (recipe != null)
    {
      event.setOutput(recipe.getOutput());
      event.setCost(recipe.getCost());
    }
  }
}

