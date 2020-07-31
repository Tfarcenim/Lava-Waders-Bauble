package tfar.lavawaderbauble;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class Utils {

  public static ItemStack getBauble(Item item, PlayerEntity player) {
    return CuriosApi.getCuriosHelper().getCuriosHandler(player).map(ICuriosItemHandler::getCurios)
    .map(map -> map.get("charm"))
            .map(ICurioStacksHandler::getStacks)
            .map(dynamicStackHandler -> {
              for (int i = 0; i < dynamicStackHandler.getSlots(); i++) {
                ItemStack stack = dynamicStackHandler.getStackInSlot(i);
                if (stack.getItem() == item) return stack;
              }
              return ItemStack.EMPTY;
            }).orElse(ItemStack.EMPTY);
  }

  public static ItemStack getBaubles(PlayerEntity player) {
    ItemStack boots = getBauble(LavaWaderBauble.lavaWaderBauble, player);

    if (boots.isEmpty())
      boots = getBauble(LavaWaderBauble.waterWalkingBootsBauble, player);

    if (boots.isEmpty())
      boots = getBauble(LavaWaderBauble.obsidianWaterWalkingBootsBauble, player);
    return boots;
  }
}
