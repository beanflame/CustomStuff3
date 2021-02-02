package cubex2.cs3.ingame.gui.block;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;

import java.util.List;

public class WindowEditTileEntity extends WindowEditBlockAttribute implements IStringProvider<WrappedTileEntity>
{
    private DropBox<WrappedTileEntity> dbTileEntities;

    public WindowEditTileEntity(WrappedBlock block)
    {
        super(block, "tileEntity", 150, 55);

        List<WrappedTileEntity> tes = Lists.newArrayList(block.getPack().getContentRegistry(WrappedTileEntity.class).getContentList());
        tes.add(null);
        dbTileEntities = dropBox(tes.toArray(new WrappedTileEntity[tes.size()])).top(7).fillWidth(7).add();
        dbTileEntities.drawNullValue = true;
        dbTileEntities.setStringProvider(this);
        dbTileEntities.setSelectedValue(block.container.tileEntity);
    }

    @Override
    protected void applyChanges()
    {
        container.tileEntity = dbTileEntities.getSelectedValue();
    }

    @Override
    public String getStringFor(WrappedTileEntity value)
    {
        if (value == null)
            return "none";
        return value.getName();
    }
}
