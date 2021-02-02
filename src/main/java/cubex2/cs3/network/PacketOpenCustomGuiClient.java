package cubex2.cs3.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cubex2.cs3.util.ClientHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public abstract class PacketOpenCustomGuiClient implements IMessage
{
    public int windowId;

    public PacketOpenCustomGuiClient()
    {
    }

    public PacketOpenCustomGuiClient(int windowId)
    {
        this.windowId = windowId;
    }

    public void setWindowId(int windowId)
    {
        this.windowId = windowId;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        windowId = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(windowId);
    }

    public static abstract class Handler<T extends PacketOpenCustomGuiClient> implements IMessageHandler<T, IMessage>
    {
        @Override
        public IMessage onMessage(T message, MessageContext ctx)
        {
            EntityPlayer player = ClientHelper.getPlayer();
            FMLCommonHandler.instance().showGuiScreen(getClientGuiElement(message, player));
            player.openContainer.windowId = message.windowId;
            return null;
        }

        protected abstract Object getClientGuiElement(T message, EntityPlayer player);
    }
}