package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.ingame.docs.ParsedDocFile;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowDocs extends Window
{
    private static final boolean RELOAD = false;
    private final ParsedDocFile docFile;
    private Button btnReload;

    public WindowDocs(String title, ParsedDocFile docFile)
    {
        super(title, BACK, docFile.getWidth(), 200);
        this.docFile = docFile;

        docFile.add(this);

        if (RELOAD)
            btnReload = button("Reload").left(7).bottom(7).add();
    }

    @Override
    public void onParentResized()
    {
        width = docFile.getWidth();

        super.onParentResized();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (RELOAD && c == btnReload)
        {
            GuiBase.openPrevWindow();
            GuiBase.openWindow(new WindowDocs("", ParsedDocFile.fromPath(docFile.path, false)));
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    public Button getBackButton()
    {
        return btnBack;
    }
}
