package cubex2.cs3.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cubex2.cs3.CustomStuff3;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public abstract class PacketOpenCustomGuiServer implements IMessage
{
    public PacketOpenCustomGuiServer()
    {
    }

    public static abstract class Handler<T extends PacketOpenCustomGuiServer> implements IMessageHandler<T, IMessage>
    {
        @Override
        public IMessage onMessage(T message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            player.getNextWindowId();
            player.closeContainer();
            int windowId = player.currentWindowId;

            CustomStuff3.packetPipeline.sendTo(getPacketForClient(message, windowId, player), player);

            player.openContainer = getServerContainer(message, player);
            player.openContainer.windowId = windowId;
            player.openContainer.addCraftingToCrafters(player);
            return null;
        }

        public void handleMessage(T message, EntityPlayerMP player)
        {
            player.getNextWindowId();
            player.closeContainer();
            int windowId = player.currentWindowId;

            CustomStuff3.packetPipeline.sendTo(getPacketForClient(message, windowId, player), player);

            player.openContainer = getServerContainer(message, player);
            player.openContainer.windowId = windowId;
            player.openContainer.addCraftingToCrafters(player);
        }

        protected abstract IMessage getPacketForClient(T message, int windowId, EntityPlayerMP player);

        protected abstract Container getServerContainer(T message, EntityPlayerMP player);
    }

    public static void openGuiOnServer(EntityPlayerMP player, PacketOpenCustomGuiClient clientPacket, Container container)
    {
        player.getNextWindowId();
        player.closeContainer();
        int windowId = player.currentWindowId;
        clientPacket.setWindowId(windowId);

        CustomStuff3.packetPipeline.sendTo(clientPacket, player);

        player.openContainer = container;
        player.openContainer.windowId = windowId;
        player.openContainer.addCraftingToCrafters(player);
    }
}