package tfar.lavawaderbauble;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class Client {
  private static final Minecraft mc = Minecraft.getInstance();
  private static final ResourceLocation ICON_LAVA = new ResourceLocation("lavawaderbauble:textures/gui/lavacharmbar.png");

  @SubscribeEvent(receiveCanceled = true)
  public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {
    if (event.getType() == RenderGameOverlayEvent.ElementType.ARMOR) {
      ClientPlayerEntity player = mc.player;
      ItemStack lavaProtector = Utils.getBauble(LavaWaderBauble.lavaWaderBauble, player);

      if (!lavaProtector.isEmpty()) {
        CompoundNBT compound = lavaProtector.getTag();
        if (compound != null) {
          float charge = compound.getInt("charge");
          mc.getRenderManager().textureManager.bindTexture(ICON_LAVA);
          IngameGui ingameGui = mc.ingameGUI;

          int width = mc.getMainWindow().getScaledWidth();
          int height = mc.getMainWindow().getScaledHeight();

          int count = (int) Math.floor(charge / 20F);

          int left = 0;
          int top = height - ForgeIngameGui.left_height;

          RenderSystem.enableBlend();
          for (int i = 0; i < count + 1; i++) {
            if (i == count) {
              float countFloat = charge / 20F + 10;
              RenderSystem.color4f(1, 1, 1, (countFloat) % ((int) (countFloat)));
            }

            ingameGui.blit(event.getMatrixStack(),width / 2 - 92 + left, top, 0, 0, 10, 10);
            left += 8;
            RenderSystem.color4f(1, 1, 1, 1);
          }
          ForgeIngameGui.left_height += 10;
          mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
          RenderSystem.disableBlend();
        }
      }
    }
  }
}
