package cn.beanflame.test.block;

import cn.beanflame.test.Test;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMagicCircleBlock extends BlockContainer {

    public BlockMagicCircleBlock()
    {
        super(Material.glass);
        this.setBlockName(Test.MODID + "." + "magic_circle_block");
        this.setHardness(2.0F);
        this.setLightLevel(1.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEMagicCircle();
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }




}
