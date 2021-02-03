package cn.beanflame.test.block;

import cn.beanflame.test.Test;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockObjBlock extends Block {

    public BlockObjBlock()
    {
        super(Material.sand);
        this.setBlockName(Test.MODID + "." + "obj_block");
        this.setHardness(2.0F);
        this.setStepSound(soundTypeStone);
    }



}
