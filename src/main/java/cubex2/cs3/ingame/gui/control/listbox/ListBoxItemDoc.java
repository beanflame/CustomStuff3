package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.docs.NamedLink;
import cubex2.cs3.ingame.docs.ParsedDocFile;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.common.WindowDocs;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;

public class ListBoxItemDoc extends ListBoxItemLabel<NamedLink>
{
    public ListBoxItemDoc(NamedLink value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        super.mouseDown(mouseX, mouseY, button);

        if (value.link != null && value.link.length() > 0)
        {
            GuiBase.openWindow(new WindowDocs(value.text, ParsedDocFile.fromPath(value.link)));
        }
    }
}
