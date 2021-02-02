package cubex2.cs3.ingame.gui.tileentity;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.tileentity.EnumTileEntityType;

public class WindowCreateTileEntity extends Window implements IValidityProvider, IStringProvider<EnumTileEntityType>
{
    private final BaseContentPack pack;

    private TextBox tbName;
    private DropBox<EnumTileEntityType> dbType;

    public WindowCreateTileEntity(BaseContentPack pack)
    {
        super("New Tile Entity", CREATE | CANCEL, 180, 201);
        this.pack = pack;

        row("Name:");
        tbName = row(textBox());
        row("Type:");
        dbType = row(dropBox(EnumTileEntityType.values()));

        tbName.setValidityProvider(this);
        dbType.setStringProvider(this);
        dbType.setSelectedValue(EnumTileEntityType.NORMAL);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            WrappedTileEntity te = new WrappedTileEntity(tbName.getText(), dbType.getSelectedValue(), pack);
            te.container = te.getType().createAttributeContainer(te);
            te.apply();

            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public String getStringFor(EnumTileEntityType value)
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
            for (WrappedTileEntity te : pack.getContentRegistry(WrappedTileEntity.class).getContentList())
            {
                if (te.getName().equals(text))
                {
                    message = "There is already a tile entity with this name.";
                    break;
                }
            }
        }

        return message;
    }
}
