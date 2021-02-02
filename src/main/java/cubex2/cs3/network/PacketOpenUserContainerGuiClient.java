package cubex2.cs3.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.InventoryItemStack;
import cubex2.cs3.gui.WindowContainerNormal;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.registry.GuiRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class PacketOpenUserContainerGuiClient extends PacketOpenCustomGuiClient
{
    private String packName;
    private String guiName;
    private int slotInvType; // 0=stack, 1=tile entity
    private int slotId;
    private int x, y, z;

    public PacketOpenUserContainerGuiClient()
    {
    }

    public PacketOpenUserContainerGuiClient(WrappedGui gui, int slotId)
    {
        this.packName = gui.getPack().getName();
        this.guiName = gui.getName();
        this.slotInvType = 0;
        this.slotId = slotId;
    }

    public PacketOpenUserContainerGuiClient(WrappedGui gui, int x, int y, int z)
    {
        this.packName = gui.getPack().getName();
        this.guiName = gui.getName();
        this.slotInvType = 1;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketOpenUserContainerGuiClient(int windowId, WrappedGui gui)
    {
        super(windowId);
        this.packName = gui.getPack().getName();
        this.guiName = gui.getName();
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        super.fromBytes(buffer);

        packName = ByteBufUtils.readUTF8String(buffer);
        guiName = ByteBufUtils.readUTF8String(buffer);
        slotInvType = buffer.readByte();
        if (slotInvType == 0)
        {
            slotId = buffer.readByte();
        } else if (slotInvType == 1)
        {
            x = buffer.readInt();
            y = buffer.readInt();
            z = buffer.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        super.toBytes(buffer);

        ByteBufUtils.writeUTF8String(buffer, packName);
        ByteBufUtils.writeUTF8String(buffer, guiName);
        buffer.writeByte(slotInvType);
        if (slotInvType == 0)
        {
            buffer.writeByte(slotId);
        } else if (slotInvType == 1)
        {
            buffer.writeInt(x);
            buffer.writeInt(y);
            buffer.writeInt(z);
        }
    }

    public static class Handler extends PacketOpenCustomGuiClient.Handler<PacketOpenUserContainerGuiClient>
    {
        @Override
        protected Object getClientGuiElement(PacketOpenUserContainerGuiClient message, EntityPlayer player)
        {
            BaseContentPack pack = BaseContentPackLoader.instance().getContentPack(message.packName);
            WrappedGui gui = ((GuiRegistry) pack.getContentRegistry(WrappedGui.class)).getGui(message.guiName);

            IInventory slotInv;
            if (message.slotInvType == 0)
            {
                slotInv = new InventoryItemStack(gui, player, message.slotId);
            } else
            {
                slotInv = (IInventory) player.worldObj.getTileEntity(message.x, message.y, message.z);
            }

            return GuiBase.createContainerGui(new WindowContainerNormal(gui, slotInv));
        }
    }
}
