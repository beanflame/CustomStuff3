package cubex2.cs3.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.block.BlockCSDoor;
import cubex2.cs3.block.attributes.DoorAttributes;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemCSDoor extends Item
{
    private WrappedBlock wrappedBlock;
    private DoorAttributes container;


    public ItemCSDoor(WrappedBlock wrappedBlock)
    {
        super();
        this.wrappedBlock = wrappedBlock;
        container = (DoorAttributes) wrappedBlock.container;
        setMaxStackSize(wrappedBlock.container.maxStack);
    }

    @Override
    public int getMetadata(int i)
    {
        return 0;
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        return container.iconFile.icon;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side != 1)
            return false;
        else
        {
            ++y;

            if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
            {
                if (!wrappedBlock.block.canPlaceBlockAt(world, x, y, z))
                {
                    return false;
                } else
                {
                    int var9 = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
                    placeDoorBlock(world, x, y, z, var9, wrappedBlock.block);
                    --stack.stackSize;
                    return true;
                }
            } else
                return false;
        }
    }

    public static void placeDoorBlock(World world, int x, int y, int z, int par4, Block doorBlock)
    {
        byte b0 = 0;
        byte b1 = 0;

        if (par4 == 0)
        {
            b1 = 1;
        }

        if (par4 == 1)
        {
            b0 = -1;
        }

        if (par4 == 2)
        {
            b1 = -1;
        }

        if (par4 == 3)
        {
            b0 = 1;
        }

        int var8 = (world.getBlock(x - b0, y, z - b1).isNormalCube() ? 1 : 0) + (world.getBlock(x - b0, y + 1, z - b1).isNormalCube() ? 1 : 0);
        int var9 = (world.getBlock(x + b0, y, z + b1).isNormalCube() ? 1 : 0) + (world.getBlock(x + b0, y + 1, z + b1).isNormalCube() ? 1 : 0);
        boolean var10 = world.getBlock(x - b0, y, z - b1) == doorBlock || world.getBlock(x - b0, y + 1, z - b1) == doorBlock;
        boolean var11 = world.getBlock(x + b0, y, z + b1) == doorBlock || world.getBlock(x + b0, y + 1, z + b1) == doorBlock;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        } else if (var9 > var8)
        {
            var12 = true;
        }

        world.setBlock(x, y, z, doorBlock, par4, 2);
        world.setBlock(x, y + 1, z, doorBlock, 8 | (var12 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, doorBlock);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, doorBlock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        container.iconFile.setIcon(iconRegister);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return wrappedBlock.block.getCreativeTabToDisplayOn();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        wrappedBlock.block.getSubBlocks(item, creativeTabs, list);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return wrappedBlock.block.getUnlocalizedName();
    }

    @Override
    public String getUnlocalizedName()
    {
        return wrappedBlock.block.getUnlocalizedName();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
    {
        wrappedBlock.addInformation(stack, player, list, flag);
    }
}
