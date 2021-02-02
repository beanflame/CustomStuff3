package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.docs.ParsedDocFile;
import cubex2.cs3.ingame.docs.WindowRegistry;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.common.WindowDocs;

public class ButtonDoc extends Button
{
    private final String path;

    public ButtonDoc(String text, String path, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(text, width, height, anchor, offsetX, offsetY, parent);
        this.path = path;
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        super.mouseDown(mouseX, mouseY, button);

        if (button == 0 && path != null && path.length() > 0)
        {
            if (!path.startsWith("::") || !WindowRegistry.openWindow(path))
                GuiBase.openWindow(new WindowDocs("", ParsedDocFile.fromPath(path)));
        }
    }
}
