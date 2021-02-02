package cubex2.cs3.util;

import cubex2.cs3.common.BaseContentPack;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IconWrapper
{
    public IIcon icon;
    public String iconString;

    public IconWrapper(String iconString)
    {
        this.iconString = iconString;
    }

    public void setIcon(IIconRegister iconRegister)
    {
        icon = iconString != null && iconString.length() > 0 ? iconRegister.registerIcon(iconString) : null;
    }

    public String getTextForGui(BaseContentPack pack)
    {
        String res = iconString;
        if (res.contains(":") && res.split(":")[0].equals(pack.id.toLowerCase()) && !res.endsWith(":"))
            res = res.split(":")[1];
        return res;
    }

    @Override
    public String toString()
    {
        return "icon(" + iconString + ")";
    }
}
