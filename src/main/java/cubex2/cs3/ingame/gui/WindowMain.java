package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.docs.ParsedDocFile;
import cubex2.cs3.ingame.gui.common.WindowDocs;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowMain extends Window
{
    private Button btnClose;
    private Button btnContentPacks;
    private Button btnJSDoc;
    private Button btnDocs;

    public WindowMain()
    {
        super("Custom Stuff", 114, 200);

        btnClose = button("Close").bottom(7).fillWidth(7).add();
        btnContentPacks = button("Content Packs").top(7).fillWidth(7).add();
        btnJSDoc = button("JavaScript").top(btnContentPacks, 3).fillWidth(7).add();
        //btnDocs = button("Documentation").top(btnJSDoc, 3).fillWidth(7).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnClose)
        {
            GuiBase.closeGui();
        } else if (c == btnContentPacks)
        {
            GuiBase.openWindow(new WindowContentPacks());
        } else if (c == btnJSDoc)
        {
            GuiBase.openWindow(new WindowDocs("JavaScript Objects", ParsedDocFile.fromPath("objects.txt")));
        } /*else if (c == btnDocs)
        {
            GuiBase.openWindow(new WindowDocs("JavaScript Objects", ParsedDocFile.fromPath("main.txt")));
        }*/
    }
}
