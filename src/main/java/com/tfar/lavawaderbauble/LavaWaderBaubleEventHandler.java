package com.tfar.lavawaderbauble;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.lavawaderbauble.LavaWaderBauble.Objects.lavaWaderBauble;
import static com.tfar.lavawaderbauble.LavaWaderBauble.Objects.waterWalkingBootsBauble;
import static com.tfar.lavawaderbauble.Utils.getBauble;
import static com.tfar.lavawaderbauble.Utils.getBaubles;

@Mod.EventBusSubscriber
public class LavaWaderBaubleEventHandler {

@SubscribeEvent
  public static void handleLavaProtection(LivingAttackEvent event) {
  if (!(event.getEntityLiving() instanceof PlayerEntity)|| !event.getSource().isFireDamage())return;
    ItemStack lavaProtector = ItemStack.EMPTY;
    ItemStack lavaCharm;

    lavaCharm = getBauble(lavaWaderBauble, (PlayerEntity) event.getEntityLiving());


    if (!lavaCharm.isEmpty()) {
      lavaProtector = lavaCharm;
    }

    if (!lavaProtector.isEmpty()) {
      CompoundNBT compound = lavaProtector.getOrCreateTag();
        int charge = compound.getInt("charge");
        if (charge > 0) {
          if (event.getSource() == DamageSource.LAVA) {
            compound.putInt("charge", --charge);
            compound.putInt("chargeCooldown", 40);
          }
          event.setCanceled(event.getSource() != DamageSource.LAVA || charge > 0);
      }
    }
  }

  @SubscribeEvent
  public static void handleFireDamage(LivingAttackEvent event) {
    if (!event.getEntityLiving().world.isRemote) {
      if (!event.isCanceled() && event.getAmount() > 0 && event.getEntityLiving() instanceof ServerPlayerEntity) {

        if (event.getSource().isFireDamage() && event.getSource() != DamageSource.LAVA) {
          handleFireProtection(event);
        }
      }
    }
  }

  private static void handleFireProtection(LivingAttackEvent event) {
    PlayerEntity player = (PlayerEntity) event.getEntityLiving();
    ItemStack bauble = getBaubles(player);
    if (!bauble.isEmpty() && bauble.getItem() != waterWalkingBootsBauble)
      event.setCanceled(true);
  }

  /*
  @SubscribeEvent
  public void livingUpdate(LivingEvent.LivingUpdateEvent event) throws IllegalAccessException {
  if (true)return;
    if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
    PlayerEntity player = (PlayerEntity) event.getEntityLiving();
    if (player.isSneaking()) return;

    ItemStack boots = getBaubles(player);

    if (boots.isEmpty()) return;
    BlockPos liquid = new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    BlockPos air = new BlockPos((int) player.posX, (int) (player.posY + player.getHeight()), (int) player.posZ);
    BlockState liquidBlockState = player.world.getBlockState(liquid);
    Material liquidMaterial = liquidBlockState.getMaterial();

    if ((liquidMaterial == Material.WATER || (boots.getItem() == lavaWaderBauble && liquidMaterial.isLiquid()) && player.world.getBlockState(air).getBlock().isAir(player.world.getBlockState(air), player.world, air))
            && Utils.isJumping(player)) {
      player.move(MoverType.SELF, new Vec3d(0, 0.22, 0));
    }
  }

  /*
  @SubscribeEvent
  public static void waterWalking(LivingEvent.LivingUpdateEvent event) {
  if (true)return;
    Entity entity = event.getEntity();
    if (!(entity.getMotion().y > 0) || !(entity instanceof PlayerEntity)) return;
    PlayerEntity player = (PlayerEntity) entity;

    ItemStack boots = getBaubles(player);

    if (boots.isEmpty()) return;

    AxisAlignedBB bb = player.getBoundingBox();
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
      player.setMotion(player.getMotion().x,player.getMotion().y + .5,player.getMotion().z);
    }
  }
/*
  @SubscribeEvent
  public static void getCollisions(@Nonnull GetCollisionBoxesEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof PlayerEntity)
            || entity.isInWater()
            || entity.world.isMaterialInBB(entity.getBoundingBox(), Material.LAVA) || entity.isSneaking()) return;

    PlayerEntity player = (PlayerEntity) entity;

    ItemStack boots = getBaubles(player);

    if (boots.isEmpty()) return;

    AxisAlignedBB entityBoundingBox = player.getBoundingBox();

    World world = (World)event.getWorld();
    for (BlockPos pos : BlockPos.getAllInBoxMutable(
            floor(entityBoundingBox.minX),
            floor(entityBoundingBox.minY - 1),
            floor(entityBoundingBox.minZ),
            ceil(entityBoundingBox.minX),
            floor(entityBoundingBox.minY),
            ceil(entityBoundingBox.minZ))) {
      BlockState state = world.getBlockState(pos);

      if (state.getMaterial() == Material.WATER || state.getMaterial().isLiquid() && boots.getItem() == lavaWaderBauble) {
        AxisAlignedBB bb = new AxisAlignedBB(
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                pos.getX() + 1,
                pos.getY() + 1,
                pos.getZ() + 1);
        if (event.getAabb().intersects(bb)) event.getCollisionBoxesList().add(bb);
      }
    }
  }*/
}

