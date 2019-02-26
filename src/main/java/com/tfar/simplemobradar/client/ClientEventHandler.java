package com.tfar.simplemobradar.client;

import com.tfar.simplemobradar.MainClass;
import com.tfar.simplemobradar.config.ConfigHandler;
import com.tfar.simplemobradar.items.ItemSimpleMobRadar;
import com.tfar.simplemobradar.items.ItemSimplePassiveRadar;
import com.tfar.simplemobradar.network.NetworkHandler;
import com.tfar.simplemobradar.util.ItemUtils;
import com.tfar.simplemobradar.util.Reference;
import com.tfar.simplemobradar.util.RenderUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import org.lwjgl.input.Keyboard;

import static com.tfar.simplemobradar.config.ConfigHandler.DISPLAY_DISTANCE;
import static com.tfar.simplemobradar.config.ConfigHandler.DISPLAY_TOTAL_MOBS;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MOD_ID)

public class ClientEventHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {

        if (event.phase == TickEvent.Phase.END && mc.player != null && !mc.gameSettings.hideGUI && !mc.gameSettings.showDebugInfo && (mc.currentScreen == null || (ConfigHandler.DISPLAY_WITH_CHAT_OPEN && mc.currentScreen instanceof GuiChat))) {
            final EntityPlayer player = mc.player;
            final ItemStack stack = ItemUtils.getHeldRadar(player);

            if (stack.getItem() instanceof ItemSimpleMobRadar) {
                if (stack.getTagCompound() == null) ItemSimpleMobRadar.resetNBT(stack);
                if (stack.getTagCompound().getInteger("mobtype")>=Reference.sorted_mobs.size())stack.getTagCompound().setInteger("mobtype",0);
                final ItemSimpleMobRadar radar = (ItemSimpleMobRadar) stack.getItem();

                switch (radar.getState(stack)) {

                    case 0: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.notfound"), 5, 0, 0xFF0000, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getMobName(stack), 5, 0, 0xFFFF00, 4);
                        break;
                    }
                    case 1: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.found"), 5, 0, 0x00FF00, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getMobName(stack), 5, 0, 0xFFFF00, 4);

                        if (DISPLAY_DISTANCE) {
                            RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.distance"), 5, 0, 0xFFFFFF, 6);
                            RenderUtils.drawLineOffsetStringOnHUD("" + ItemUtils.getDistance(player, stack), 5, 0, 0x00FFFF, 7);
                        }

                        if (DISPLAY_TOTAL_MOBS) {
                            RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.totalfound"), 5, 0, 0xFFFFFF, 9);
                            RenderUtils.drawLineOffsetStringOnHUD("" + ItemUtils.getTotal(stack), 5, 0, 0x00FFFF, 10);
                        }
                        break;
                    }
                    case 2: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.inactive"), 5, 0, 0xAAAAAA, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getMobName(stack), 5, 0, 0xFFFF00, 4);
                        break;
                    }
                }
            }

            if (stack.getItem() instanceof ItemSimplePassiveRadar) {
                if (stack.getTagCompound() == null) ItemSimplePassiveRadar.resetNBT(stack);
                if (stack.getTagCompound().getInteger("mobtype")>=Reference.sorted_mobs.size())stack.getTagCompound().setInteger("mobtype",0);
                final ItemSimplePassiveRadar radar = (ItemSimplePassiveRadar) stack.getItem();

                switch (radar.getState(stack)) {

                    case 0: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.notfound"), 5, 0, 0xFF0000, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getPassiveName(stack), 5, 0, 0xFFFF00, 4);
                        break;
                    }
                    case 1: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.found"), 5, 0, 0x00FF00, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getPassiveName(stack), 5, 0, 0xFFFF00, 4);

                        if (DISPLAY_DISTANCE) {
                            RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.distance"), 5, 0, 0xFFFFFF, 6);
                            RenderUtils.drawLineOffsetStringOnHUD("" + ItemUtils.getDistance(player, stack), 5, 0, 0x00FFFF, 7);
                        }

                        if (DISPLAY_TOTAL_MOBS) {
                            RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.totalfound"), 5, 0, 0xFFFFFF, 9);
                            RenderUtils.drawLineOffsetStringOnHUD("" + ItemUtils.getTotal(stack), 5, 0, 0x00FFFF, 10);
                        }
                        break;
                    }
                    case 2: {
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.status"), 5, 0, 0xFFFFFF, 0);
                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.inactive"), 5, 0, 0xBBBBBB, 1);

                        RenderUtils.drawLineOffsetStringOnHUD(I18n.format("string.simplemobradar.mob"), 5, 0, 0xFFFFFF, 3);
                        RenderUtils.drawLineOffsetStringOnHUD(ItemUtils.getPassiveName(stack), 5, 0, 0xFFFF00, 4);
                        break;
                    }
                }
            }
        }
    }

    public static final KeyBinding SCROLL_CATALYST = new KeyBinding("key." + Reference.MOD_ID + ".scroll", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_LSHIFT, Reference.NAME);

    static {
        ClientRegistry.registerKeyBinding(SCROLL_CATALYST);
    }

    @SubscribeEvent
    public static void onMouseInput(MouseEvent event) {
        if (Minecraft.getMinecraft().currentScreen == null) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
            if (event.getDwheel() != 0 && SCROLL_CATALYST.isKeyDown() && (held.getItem() instanceof ItemSimpleMobRadar || held.getItem() instanceof ItemSimplePassiveRadar)) {
                MainClass.NETWORK_WRAPPER.sendToServer(new NetworkHandler(event.getDwheel() > 0));
                event.setCanceled(true);
            }
        }
    }
}