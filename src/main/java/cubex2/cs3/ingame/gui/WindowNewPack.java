package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.BaseContentPackLoader;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.lib.Strings;

public class WindowNewPack extends Window implements IValidityProvider
{
    private Label lblName;
    private Label lblId;
    private TextBox tbName;
    private TextBox tbId;

    public WindowNewPack()
    {
        super("New Content Pack", CREATE | CANCEL, 180, 201);

        lblName = label("Name:").at(7, 7).add();
        tbName = textBox().below(lblName).fillWidth(7).height(17).add();
        tbName.setValidityProvider(this);

        lblId = label("ID:").below(tbName).add();
        infoButton(Strings.INFO_NEW_PACK).rightTo(lblId).add();
        tbId = textBox().below(lblId).fillWidth(7).height(17).add();
        tbId.setValidityProvider(this);

        label("You need to restart Minecraft\nfor the pack to appear.").below(tbId, 10).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            BaseContentPackLoader.instance().createContentPack(tbName.getText().trim(), tbId.getText().trim());
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tb.getText().trim();
        if (text.length() == 0)
        {
            message = tb == tbName ? "Enter a name." : "Enter an ID.";
        } else if (text.contains(" ") && tb == tbId)
        {
            message = "Whitespaces are not allowed.";
        } else
        {
            for (BaseContentPack pack : BaseContentPackLoader.instance().getContentPacks())
            {
                if (tb == tbName)
                {
                    if (pack.name.equals(text))
                    {
                        message = "There is already a pack with this name.";
                        break;
                    }
                } else
                {
                    if (pack.id.equals(text))
                    {
                        message = "There is already a pack with this id.";
                        break;
                    }
                }
            }
        }

        return message;
    }
}
