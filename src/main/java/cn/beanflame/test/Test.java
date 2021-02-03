package cn.beanflame.test;

import cn.beanflame.test.common.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;



@Mod(modid = Test.MODID, name = Test.NAME, version = Test.VERSION)
public class Test {

    public static final String MODID = "tests";
    public static final String NAME = "Tests";
    public static final String VERSION = "0.1.0";

    @Mod.Instance(Test.MODID)
    public static Test instance;

    @SidedProxy(
            clientSide = "cn.beanflame.test.client.ClientProxy",
            serverSide = "cn.beanflame.test.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }







}
