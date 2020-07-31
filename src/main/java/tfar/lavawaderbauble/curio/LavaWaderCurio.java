package tfar.lavawaderbauble.curio;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class LavaWaderCurio extends WaterWalkingCurio {

	private final ItemStack stack;

	public LavaWaderCurio(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity) {
		if (livingEntity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) livingEntity;
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
