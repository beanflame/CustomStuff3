package cubex2.cs3.ingame.docs;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.WindowNewPack;

public class WindowRegistry
{
    public static boolean openWindow(String path)
    {
        BaseContentPack pack = BaseContentPackLoader.instance().getContentPack("tutorial");
        if (pack == null && BaseContentPackLoader.instance().getContentPacks().size() == 1)
            pack = BaseContentPackLoader.instance().getContentPacks().get(0);

        path = path.substring(2, path.length() - 4);
        if (path.equals("newPack"))
        {
            GuiBase.openWindow(new WindowNewPack());
            return true;
        }

        if (pack == null)
            return false;

        return false;
    }
}
