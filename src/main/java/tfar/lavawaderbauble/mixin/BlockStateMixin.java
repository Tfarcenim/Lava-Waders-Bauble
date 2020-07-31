package tfar.lavawaderbauble.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.lavawaderbauble.LavaWaderBaubleEventHandler;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class BlockStateMixin {

	@Inject(method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;",at = @At("HEAD"),cancellable = true)
	private void waterWalk(IBlockReader worldIn, BlockPos pos, ISelectionContext context, CallbackInfoReturnable<VoxelShape> cir) {
		if (LavaWaderBaubleEventHandler.waterWalk((AbstractBlock.AbstractBlockState)(Object)this,worldIn, pos, context, cir)) {
			cir.setReturnValue(VoxelShapes.fullCube());
		}
	}
}
