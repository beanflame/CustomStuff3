package cubex2.cs3.gui;

import com.google.common.collect.Maps;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiAttributes;
import cubex2.cs3.gui.attributes.GuiContainerAttributes;
import cubex2.cs3.ingame.gui.Window;
import net.minecraft.inventory.IInventory;

import java.lang.reflect.Constructor;
import java.util.Map;

public enum EnumGuiType
{
    NORMAL("normal", WindowNormal.class, GuiAttributes.class),
    CONTAINER("container", WindowContainerNormal.class, GuiContainerAttributes.class);

    public final String name;
    public final Class<? extends Window> guiClass;
    public final Class<? extends GuiAttributes> attributeClass;

    private final Constructor<? extends Window> constructor;

    EnumGuiType(String name, Class<? extends Window> guiClass, Class<? extends GuiAttributes> attributeClass)
    {
        this.name = name;
        this.guiClass = guiClass;
        this.attributeClass = attributeClass;
        constructor = createConstructor();
    }

    private Constructor<? extends Window> createConstructor()
    {
        try
        {
            if (guiClass == WindowNormal.class)
                return guiClass.getConstructor(WrappedGui.class);
            return guiClass.getConstructor(WrappedGui.class, IInventory.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Window createWindow(WrappedGui wrappedGui)
    {
        try
        {
            Window gui = constructor.newInstance(wrappedGui);
            return gui;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public GuiAttributes createAttributeContainer(WrappedGui wrappedGui)
    {
        try
        {
            return attributeClass.getConstructor(BaseContentPack.class).newInstance(wrappedGui.getPack());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Map<String, EnumGuiType> map = Maps.newHashMap();

    public static EnumGuiType get(String name)
    {
        if (map.isEmpty())
        {
            for (EnumGuiType e : values())
            {
                map.put(e.name, e);
            }
        }
        return map.get(name);
    }
}
