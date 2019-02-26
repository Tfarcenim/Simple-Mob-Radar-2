package com.tfar.simplemobradar.network;

import com.tfar.simplemobradar.items.ItemSimpleMobRadar;
import com.tfar.simplemobradar.items.ItemSimplePassiveRadar;
import com.tfar.simplemobradar.util.Reference;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class NetworkHandler implements IMessage {

    public boolean increment;

    public NetworkHandler(){}

    public NetworkHandler(boolean increment) {
        this.increment = increment;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.increment = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(increment);
    }

    public static class Handler implements IMessageHandler<NetworkHandler, IMessage> {
        @Override
        public IMessage onMessage(NetworkHandler message, MessageContext ctx) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {

                ItemStack stack = ctx.getServerHandler().player.getHeldItemMainhand();
                if (stack.getItem() instanceof ItemSimpleMobRadar) {
                    if (stack.getTagCompound()==null) ItemSimpleMobRadar.resetNBT(stack);
                        int type = stack.getTagCompound().getInteger("mobtype");
                    stack.getTagCompound().setInteger("State", 2);
                    stack.getTagCompound().setInteger("mobtype",type + (message.increment ? -1 : 1));
                        if (stack.getTagCompound().getInteger("mobtype") == -1)
                            stack.getTagCompound().setInteger("mobtype",Reference.mobs.size()-1);
                        else if (stack.getTagCompound().getInteger("mobtype") == Reference.mobs.size())
                            stack.getTagCompound().setInteger("mobtype",0);
                }
                if (stack.getItem() instanceof ItemSimplePassiveRadar) {
                    if (stack.getTagCompound()==null) ItemSimplePassiveRadar.resetNBT(stack);
                    int type = stack.getTagCompound().getInteger("mobtype");
                    stack.getTagCompound().setInteger("State", 2);
                    stack.getTagCompound().setInteger("mobtype",type + (message.increment ? -1 : 1));
                    if (stack.getTagCompound().getInteger("mobtype") == -1)
                        stack.getTagCompound().setInteger("mobtype",Reference.animals.size()-1);
                    else if (stack.getTagCompound().getInteger("mobtype") == Reference.animals.size())
                        stack.getTagCompound().setInteger("mobtype",0);
                }
            });
            return null;
        }
    }
}

