package cubex2.cs3.handler.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cubex2.cs3.block.IBlockCS;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHandler
{
    @SubscribeEvent
    public void onUseBonemeal(BonemealEvent event)
    {
        if (event.block instanceof IBlockCS)
        {
            IBlockCS block = (IBlockCS) event.block;
            if (block.getWrappedBlock().onBonemeal(event.world, event.x, event.y, event.z, event.entityPlayer))
            {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
