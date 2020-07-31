package tfar.lavawaderbauble;

import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.Logger;

import tfar.lavawaderbauble.curio.LavaWaderCurio;
import tfar.lavawaderbauble.curio.WaterWalkingCurio;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


@Mod(value = LavaWaderBauble.MODID)
public class LavaWaderBauble {
  public static final String MODID = "lavawaderbauble";

  private static Logger logger;

  public LavaWaderBauble() {
  }

  public static Item lavaWaderBauble;
  public static Item waterWalkingBootsBauble;
  public static Item obsidianWaterWalkingBootsBauble;

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
      IForgeRegistry<Item> registry = e.getRegistry();
      Item.Properties properties = new Item.Properties().group(ItemGroup.COMBAT);
      Item.Properties water = properties.rarity(Rarity.UNCOMMON);
      Item.Properties obsidian = properties.rarity(Rarity.RARE);
      Item.Properties lava = properties.rarity(Rarity.EPIC);

      registry.register(lavaWaderBauble = new LavaWadersCurioItem(lava).setRegistryName(MODID));
      registry.register(obsidianWaterWalkingBootsBauble = new WaterWalkingBootsItem(obsidian).setRegistryName("obsidianwaterwalkingbootsbauble"));
      registry.register(waterWalkingBootsBauble = new WaterWalkingBootsItem(water).setRegistryName("waterwalkingbootsbauble"));
    }

    @SubscribeEvent
    public static void communication(InterModEnqueueEvent e) {
      InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
              () -> SlotTypePreset.CHARM.getMessageBuilder().build());
    }
  }
}
