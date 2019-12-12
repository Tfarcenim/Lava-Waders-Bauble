package com.tfar.lavawaderbauble;

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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.tfar.lavawaderbauble.ModRecipes.registerCompat;

@Mod(value = LavaWaderBauble.MODID)
public class LavaWaderBauble {
  public static final String MODID = "lavawaderbauble";

  private static Logger logger;

  @SubscribeEvent
  public void init(FMLCommonSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new LavaWaderBaubleEventHandler());
    if (ModList.get().isLoaded("randomthings")) registerCompat();
  }

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
      IForgeRegistry<Item> registry = e.getRegistry();
      Item.Properties properties = new Item.Properties().group(ItemGroup.COMBAT);
      Item.Properties water = properties.rarity(Rarity.UNCOMMON);
      Item.Properties obsidian = properties.rarity(Rarity.RARE);
      Item.Properties lava = properties.rarity(Rarity.EPIC);

      registry.register(new ItemLavaWaderBauble(lava).setRegistryName(MODID));
      registry.register(new Item(obsidian).setRegistryName("obsidianwaterwalkingbootsbauble"));
      registry.register(new Item(water).setRegistryName("waterwalkingbootsbauble"));
    }

    @SubscribeEvent
    public static void communication(InterModEnqueueEvent e) {
      InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE,
              () -> new CurioIMCMessage("charm"));
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {

      if (!MODID.equals(evt.getObject().getItem().getCreatorModId(evt.getObject()))) {
        return;
      }

      ICurio curio = new ICurio() {

        @Override
        public boolean canRightClickEquip() {
          return true;
        }
      };
      ICapabilityProvider provider = new ICapabilityProvider() {
        private final LazyOptional<ICurio> curioOpt =
                LazyOptional.of(() -> curio);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                                 @Nullable Direction side) {

          return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
        }
      };
      evt.addCapability(CuriosCapability.ID_ITEM, provider);
    }

  }

  @ObjectHolder(MODID)
  public static class Objects {

    public static final Item lavaWaderBauble = null;
    public static final Item waterWalkingBootsBauble = null;
    public static final Item obsidianWaterWalkingBootsBauble = null;
  }
}
