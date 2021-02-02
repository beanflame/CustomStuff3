package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.gui.WindowGuis;
import cubex2.cs3.lib.Strings;

public class GuiRegistry extends ContentRegistry<WrappedGui>
{
    public GuiRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    public WrappedGui getGui(String name)
    {
        for (WrappedGui gui : contentList)
        {
            if (gui.getName().equals(name))
                return gui;
        }

        return null;
    }

    @Override
    public WrappedGui newDataInstance()
    {
        return new WrappedGui(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowGuis(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "GUIs";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_GUI;
    }
}
