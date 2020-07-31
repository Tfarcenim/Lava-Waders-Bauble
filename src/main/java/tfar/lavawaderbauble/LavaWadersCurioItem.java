package tfar.lavawaderbauble;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import tfar.lavawaderbauble.curio.LavaWaderCurio;
import tfar.lavawaderbauble.curio.WaterWalkingCurio;
import top.theillusivec4.curios.common.capability.CurioItemCapability;

public class LavaWadersCurioItem extends Item {

	public LavaWadersCurioItem(Properties properties) {
		super(properties);
	}

	public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
		return CurioItemCapability.createProvider(new WaterWalkingCurio() {

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
		});
	}
}
