package cubex2.cs3.item;

import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public class ItemCSFluid extends ItemCSBlock
{
    public ItemCSFluid(Block block, WrappedBlock wrappedBlock)
    {
        super(block, wrappedBlock);
    }

    @Override
    public int getMetadata(int i)
    {
        return 0;
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        return field_150939_a.getIcon(0, damage);
    }
}
