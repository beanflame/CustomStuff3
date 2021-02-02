package cubex2.cs3.ingame.gui.worldgen;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.control.HorizontalItemList;
import cubex2.cs3.ingame.gui.control.Label;
import net.minecraft.item.ItemStack;

public class WindowEditReplacedBlocks extends WindowEditWorldGenAttribute
{
    private HorizontalItemList overworld;
    private HorizontalItemList nether;
    private HorizontalItemList end;

    public WindowEditReplacedBlocks(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen, "replacedBlocks", 176, 164);

        Label label = label("Overworld").at(7, 7).add();

        overworld = horItemList(9).below(label).add();
        overworld.canAdd = true;
        overworld.canRemove = true;
        overworld.blocksOnly = true;
        overworld.setItems(Lists.newArrayList(container.overworldReplacedBlocks));

        label = label("Nether").below(overworld, 5).add();

        nether = horItemList(9).below(label).add();
        nether.canAdd = true;
        nether.canRemove = true;
        nether.blocksOnly = true;
        nether.setItems(Lists.newArrayList(container.netherReplacedBlocks));

        label = label("End").below(nether, 5).add();

        end = horItemList(9).below(label).add();
        end.canAdd = true;
        end.canRemove = true;
        end.blocksOnly = true;
        end.setItems(Lists.newArrayList(container.endReplacedBlocks));
    }

    @Override
    protected void applyChanges()
    {
        container.overworldReplacedBlocks = overworld.getStacks().toArray(new ItemStack[overworld.getStacks().size()]);
        container.netherReplacedBlocks = nether.getStacks().toArray(new ItemStack[nether.getStacks().size()]);
        container.endReplacedBlocks = end.getStacks().toArray(new ItemStack[end.getStacks().size()]);
    }

}
