package cubex2.cs3;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.entity.EntityCSGravityBlock;
import cubex2.cs3.handler.event.BonemealHandler;
import cubex2.cs3.handler.event.FillBucketHandler;
import cubex2.cs3.tileentity.TileEntityCS;
import cubex2.cs3.tileentity.TileEntityInventory;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public void registerKeyBindings()
    {
        // do nothing
    }

    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new BonemealHandler());
        MinecraftForge.EVENT_BUS.register(new FillBucketHandler());
    }

    public void registerEntities()
    {
        EntityRegistry.registerModEntity(EntityCSGravityBlock.class, "cs_gravityblock", 1, CustomStuff3.instance, 80, 1, true);
    }

    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityCS.class, "cs3_normal");
        GameRegistry.registerTileEntity(TileEntityInventory.class, "cs3_inventory");
    }

    public void initRendering()
    {
        // do nothing
    }
}
