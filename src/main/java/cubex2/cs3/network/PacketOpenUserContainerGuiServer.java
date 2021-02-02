package cubex2.cs3.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.ContainerBasic;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class PacketOpenUserContainerGuiServer extends PacketOpenCustomGuiServer
{
    private WrappedGui gui;
    private EntityPlayerMP player;

    public PacketOpenUserContainerGuiServer(WrappedGui gui, EntityPlayerMP player)
    {
        this.gui = gui;
        this.player = player;
    }

    public PacketOpenUserContainerGuiServer()
    {
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {

    }

    @Override
    public void toBytes(ByteBuf buffer)
    {

    }

    public static class Handler extends PacketOpenCustomGuiServer.Handler<PacketOpenUserContainerGuiServer>
    {
        @Override
        protected IMessage getPacketForClient(PacketOpenUserContainerGuiServer message, int windowId, EntityPlayerMP player)
        {
            return new PacketOpenUserContainerGuiClient(windowId, message.gui);
        }

        @Override
        protected Container getServerContainer(PacketOpenUserContainerGuiServer message, EntityPlayerMP player)
        {
            return new ContainerBasic(message.gui, message.player, null);
        }
    }
}
