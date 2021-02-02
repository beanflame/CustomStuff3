package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.ingame.docs.ParsedDocFile;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.common.WindowDocs;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextField;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.ScriptWrapper;

public class WindowEditControlScript extends Window
{
    private final ScriptWrapper currentScript;
    private final ScriptWrapper newScript;
    private final String pathToInfo;

    private TextField textField;
    private Label lblError;
    private Button btnInfo;

    public WindowEditControlScript(ScriptWrapper currentScript, String pathToInfo)
    {
        super(null, EDIT | CANCEL, Math.min(GuiBase.INSTANCE.width, 504), Math.min(GuiBase.INSTANCE.height, 504));
        this.currentScript = currentScript;
        this.pathToInfo = pathToInfo;

        newScript = new ScriptWrapper(currentScript.getSource() == null ? "" : currentScript.getSource());

        btnEdit.setText("Save");

        textField = textField().top(7).bottom(34).fillWidth(7).add();
        textField.setText(newScript.getSource());

        lblError = label("Script has syntax errors!").left(btnEdit, 4).top(btnEdit, 4, true).add();
        lblError.setColor(Color.RED);
        lblError.setVisible(false);

        btnInfo = button("Info").right(btnCancel, 3).bottom(7).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnEdit)
        {
            boolean hasErrors = false;
            try
            {
                newScript.setSource(textField.getText());
            } catch (Exception e)
            {
                hasErrors = true;
            }

            if (!hasErrors)
            {
                currentScript.setSource(textField.getText());

                GuiBase.openPrevWindow();
            } else
            {
                lblError.setVisible(true);
            }
        } else if (c == btnInfo)
        {
            GuiBase.openWindow(new WindowDocs(pathToInfo, ParsedDocFile.fromPath(pathToInfo)));
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    public void onParentResized()
    {
        width = Math.min(GuiBase.INSTANCE.width, 504);
        height = Math.min(GuiBase.INSTANCE.height, 504);

        super.onParentResized();
    }
}
