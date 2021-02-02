package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.EnumGuiType;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.*;

public class WindowCreateGui extends Window implements IValidityProvider, IStringProvider<EnumGuiType>
{
    private final BaseContentPack pack;

    private TextBox tbName;
    private DropBox<EnumGuiType> dbType;

    public WindowCreateGui(BaseContentPack pack)
    {
        super("New GUI", CREATE | CANCEL, 180, 201);
        this.pack = pack;

        row("Name:");
        tbName = row(textBox());
        row("Type:");
        dbType = row(dropBox(EnumGuiType.values()));

        tbName.setValidityProvider(this);
        dbType.setStringProvider(this);
        dbType.setSelectedValue(EnumGuiType.NORMAL);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            WrappedGui gui = new WrappedGui(tbName.getText(), dbType.getSelectedValue(), pack);
            gui.container = gui.getType().createAttributeContainer(gui);
            gui.apply();

            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public String getStringFor(EnumGuiType value)
    {
        return value.name;
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tb.getText().trim();
        if (text.length() == 0)
            message = "Enter a name";
        else
        {
            for (WrappedGui gui : pack.getContentRegistry(WrappedGui.class).getContentList())
            {
                if (gui.getName().equals(text))
                {
                    message = "There is already a GUI with this name.";
                    break;
                }
            }
        }

        return message;
    }
}
