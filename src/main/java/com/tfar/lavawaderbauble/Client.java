package com.tfar.lavawaderbauble;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.lavawaderbauble.Utils.getBauble;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class Client {
  private static final Minecraft mc = Minecraft.getInstance();
  private static final ResourceLocation ICON_LAVA = new ResourceLocation("lavawaderbauble:textures/gui/lavacharmbar.png");

  @SubscribeEvent
  public static void renderGameOverlay(RenderGameOverlayEvent event) {
    if (event.getType() != null && event instanceof RenderGameOverlayEvent.Post && event.getType() == RenderGameOverlayEvent.ElementType.ALL)
      renderLavaCharm(event);
  }

  private static void renderLavaCharm(RenderGameOverlayEvent event) {
    ItemStack lavaProtector = ItemStack.EMPTY;

    ClientPlayerEntity player = Minecraft.getInstance().player;

    ItemStack lavaWaderBauble = getBauble(LavaWaderBauble.Objects.lavaWaderBauble, player);

    if (!lavaWaderBauble.isEmpty())
      lavaProtector = lavaWaderBauble;

    if (!lavaProtector.isEmpty()) {
      CompoundNBT compound = lavaProtector.getTag();
      if (compound != null) {
        float charge = compound.getInt("charge");
        mc.getRenderManager().textureManager.bindTexture(ICON_LAVA);
        IngameGui ingameGui = mc.ingameGUI;

        int width = mc.mainWindow.getScaledWidth();
        int height = mc.mainWindow.getScaledHeight();

        int count = (int) Math.floor(charge / 20F);

        int left = 0;
        if(player.getTotalArmorValue()>0) ForgeIngameGui.left_height += 10;

        int top = height - ForgeIngameGui.left_height - 1;
        ForgeIngameGui.left_height += 10;

        GlStateManager.enableBlend();
        for (int i = 0; i < count + 1; i++) {
          if (i == count + 1 - 1) {
            float countFloat = charge / 20F + 10;
            GlStateManager.color4f(1, 1, 1, (countFloat) % ((int) (countFloat)));
          }

          ingameGui.blit(width / 2 - 92 + left, top, 0, 0, 10, 10);
          left += 8;
          GlStateManager.color4f(1, 1, 1, 1);
        }
        //mc.renderEngine.bindTexture(AbstractGui.ICONS);
        GlStateManager.disableBlend();
      }
    }
  }
}
