package cn.beanflame.test.client;

import cn.beanflame.test.block.TESRMagicCircle;
import cn.beanflame.test.block.TEMagicCircle;
import cn.beanflame.test.common.CommonProxy;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        //ClientRegistry.bindTileEntitySpecialRenderer(TESnowball.class, new TESRSnowball());
        ClientRegistry.bindTileEntitySpecialRenderer(TEMagicCircle.class, new TESRMagicCircle());

    }
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
