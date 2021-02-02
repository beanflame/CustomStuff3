package cubex2.cs3.ingame.gui.common;

import com.google.common.collect.Lists;
import cubex2.cs3.common.AttributeContent;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowEditAttributeContent extends Window implements IListBoxItemClickListener<AttributeData>
{
    protected final AttributeContent content;

    private ListBox<AttributeData> listBox;

    public WindowEditAttributeContent(AttributeContent content)
    {
        super(content.getName(), BACK, 263, 160);
        this.content = content;

        ListBoxDescription<AttributeData> desc = new ListBoxDescription<AttributeData>(7, 7);
        desc.rows = 9;
        desc.columns = 1;
        desc.elements = Lists.newArrayList(content.getContainer().getAttributeDatas(content.getTypeString()));
        desc.canSelect = false;
        desc.sorted = true;
        listBox = listBox(desc).fillWidth(7).top(7).add();
    }

    @Override
    public void itemClicked(AttributeData item, ListBox<AttributeData> listBox, int button)
    {
        try
        {
            Class<? extends Window> windowClass = content.getContainer().getWindowClass(item);
            if (windowClass == WindowEditScript.class)
            {
                GuiBase.openWindow(new WindowEditScript(item.field.getName(), content.getContainer()));
            } else if (windowClass == WindowEditInteger.class)
            {
                GuiBase.openWindow(new WindowEditInteger(item, content.getContainer()));
            } else if (windowClass == WindowEditFloat.class)
            {
                GuiBase.openWindow(new WindowEditFloat(item, content.getContainer()));
            } else if (windowClass == WindowEditBoolean.class)
            {
                GuiBase.openWindow(new WindowEditBoolean(item, content.getContainer()));
            } else
            {
                GuiBase.openWindow(windowClass.getConstructor(content.getClass()).newInstance(content));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
