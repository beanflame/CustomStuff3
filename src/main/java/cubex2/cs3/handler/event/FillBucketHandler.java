package cubex2.cs3.handler.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.item.EnumItemType;
import cubex2.cs3.item.ItemCSBucket;
import cubex2.cs3.item.attributes.BucketAttributes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class FillBucketHandler
{
    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        if (event.target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
            return;
        World world = event.world;
        int x = event.target.blockX;
        int y = event.target.blockY;
        int z = event.target.blockZ;
        Block block = world.getBlock(x, y, z);
        for (BaseContentPack pack : BaseContentPackLoader.instance().getContentPacks())
        {
            for (WrappedItem wrappedItem : pack.getContentRegistry(WrappedItem.class).getContentList())
            {
                if (wrappedItem.getType() == EnumItemType.BUCKET)
                {
                    ItemCSBucket bucket = (ItemCSBucket) wrappedItem.item;
                    if (block == ((BucketAttributes) wrappedItem.container).fluid && world.getBlockMetadata(x, y, z) == 0)
                    {
                        world.setBlockToAir(x, y, z);
                        event.result = new ItemStack(bucket);
                        event.setResult(Event.Result.ALLOW);
                        return;
                    }
                }
            }
        }
    }
}
