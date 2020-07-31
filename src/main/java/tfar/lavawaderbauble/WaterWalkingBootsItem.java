package tfar.lavawaderbauble;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import tfar.lavawaderbauble.curio.WaterWalkingCurio;
import top.theillusivec4.curios.common.capability.CurioItemCapability;

public class WaterWalkingBootsItem extends Item {

	public WaterWalkingBootsItem(Properties properties) {
		super(properties);
	}

	public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
		return CurioItemCapability.createProvider(new WaterWalkingCurio());
	}
}
