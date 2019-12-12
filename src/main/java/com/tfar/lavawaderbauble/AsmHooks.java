package com.tfar.lavawaderbauble;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

@SuppressWarnings("unused")
public class AsmHooks {
  public static VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
   // if (!(entity instanceof PlayerEntity) || entity.isSneaking())
   //   return

    VoxelShape shape = state.getShape(world, pos);
    Entity entity = context.getEntity();
    if (entity instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity)entity;
      boolean wet = player.isInWater() || player.isInLava();
      //if ( wet && Utils.isJumping(player))
          //  player.setMotion(player.getMotion().x,player.getMotion().y + .0001,player.getMotion().z);
      boolean applyCollision = !entity.isSneaking();
      applyCollision &= state.getBlock() instanceof FlowingFluidBlock;
      applyCollision &= !wet;
      applyCollision &= world.getBlockState(pos.up()).isAir(world,pos.up());
      ItemStack boots = Utils.getBaubles(player);
      applyCollision &= !boots.isEmpty()
              && (state.getMaterial() == Material.WATER
              || state.getMaterial() == Material.LAVA
              && boots.getItem() ==
              LavaWaderBauble.Objects.lavaWaderBauble);

      if (applyCollision) shape = VoxelShapes.fullCube();
    }
    return shape;
  }
}
