package cn.beanflame.test.init;

import cn.beanflame.test.block.BlockObjBlock;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockLoader {

    public static Block ObjBlock = new BlockObjBlock();

    public BlockLoader(FMLPreInitializationEvent event)
    {
        GameRegistry.registerBlock(ObjBlock, "obj_block");
    }


}
