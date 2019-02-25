package com.tfar.simplemobradar.client;

import com.tfar.simplemobradar.config.ConfigHandler;
import com.tfar.simplemobradar.items.ItemSimpleMobRadar;
import com.tfar.simplemobradar.util.ItemUtils;
import com.tfar.simplemobradar.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientEventHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent

    public void onRenderTick(TickEvent.RenderTickEvent event) {

        if (event.phase == TickEvent.Phase.END && mc.player != null && !mc.gameSettings.hideGUI && !mc.gameSettings.showDebugInfo && (mc.currentScreen == null || (ConfigHandler.DISPLAY_WITH_CHAT_OPEN && mc.currentScreen instanceof GuiChat))) {
            final EntityPlayer player = mc.player;
            final ItemStack stack = ItemUtils.getHeldRadar(player);

            if (stack.getItem() instanceof ItemSimpleMobRadar) {
                if (stack.getTagCompound() == null) ItemSimpleMobRadar.writeToNBT(stack);
                final ItemSimpleMobRadar radar = (ItemSimpleMobRadar) stack.getItem();


                switch (radar.getState(stack)) {

                    case 0: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.notfound"), 5, 0, 0xFF0000, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0x00FF00, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getMobName(stack), 5, 0, 0xFFFF00, 4);
                        break;
                    }
                    case 1: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.found"), 5, 0, 0x00FF00, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getMobName(stack), 5, 0, 0xFFFF00, 4);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.distance"), 5, 0, 0xFFFFFF, 6);
                        RenderUtils.drawLineOffsetStringOnHUD(""+ItemUtils.getDistance(player,stack), 5, 0, 0xFFFF00, 7);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.totalfound"), 5, 0, 0xFFFFFF, 9);
                        RenderUtils.drawLineOffsetStringOnHUD(""+ItemUtils.getTotal(stack), 5, 0, 0x00FFFF, 10);
                        break;
                    }
                    case 2: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.inactive"), 5, 0, 0xBBBBBB, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getMobName(stack), 5, 0, 0xFFFF00, 4);
                        break;
                    }
                }
            }
        }
    }
}