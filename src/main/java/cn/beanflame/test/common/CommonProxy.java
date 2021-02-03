package cn.beanflame.test.common;


import cn.beanflame.test.Test;
import cn.beanflame.test.block.TEMagicCircle;
import cn.beanflame.test.init.BlockLoader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event)
    {
        new BlockLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        // 魔法阵 Magic circle
        GameRegistry.registerTileEntity(TEMagicCircle.class, Test.MODID + ":" +"type.magic_circle_block");
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
