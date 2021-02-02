package cubex2.cs3;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cubex2.cs3.asm.ICSMod;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.lib.Directories;
import cubex2.cs3.lib.ModInfo;
import cubex2.cs3.network.PacketOpenUserContainerGuiClient;
import cubex2.cs3.network.PacketOpenUserContainerGuiServer;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
public class CustomStuff3
{
    @Instance(ModInfo.ID)
    public static CustomStuff3 instance;

    @SidedProxy(clientSide = "cubex2.cs3.ClientProxy", serverSide = "cubex2.cs3.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    public static SimpleNetworkWrapper packetPipeline;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = FMLLog.getLogger();
        packetPipeline = NetworkRegistry.INSTANCE.newSimpleChannel("CustomStuff3");
        registerPackets();

        Directories.init(event.getModConfigurationDirectory().getParentFile());

        Config.init(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerEntities();
        proxy.registerTileEntities();
        proxy.initRendering();
        proxy.registerKeyBindings();
        proxy.registerEventHandlers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    public static void onPreInitPack(ICSMod pack)
    {
        BaseContentPackLoader.instance().onPreInitPack(pack);
    }

    public static void onInitPack(ICSMod pack)
    {
        BaseContentPackLoader.instance().onInitPack(pack);
    }

    public static void onPostInitPack(ICSMod pack)
    {
        BaseContentPackLoader.instance().onPostInitPack(pack);
    }

    private void registerPackets()
    {
        registerPacket(PacketOpenUserContainerGuiServer.Handler.class, PacketOpenUserContainerGuiServer.class, Side.SERVER);
        registerPacket(PacketOpenUserContainerGuiClient.Handler.class, PacketOpenUserContainerGuiClient.class, Side.CLIENT);
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        packetPipeline.registerMessage(messageHandler, requestMessageType, nextPacketId(), side);
    }

    private static int packetId = Byte.MIN_VALUE;

    private static int nextPacketId()
    {
        if (packetId >= Byte.MAX_VALUE + 1)
        {
            throw new RuntimeException("No more packet ids left!");
        }
        return packetId++;
    }
}
