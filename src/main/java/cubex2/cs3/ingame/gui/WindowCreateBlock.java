package cubex2.cs3.ingame.gui;

import cubex2.cs3.block.EnumBlockType;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.*;

public class WindowCreateBlock extends Window implements IValidityProvider, IStringProvider<EnumBlockType>
{
    private final BaseContentPack pack;

    private Label lblName;
    private TextBox tbName;
    private Label lblType;
    private DropBox<EnumBlockType> dbType;

    private Label lblInfo;

    public WindowCreateBlock(BaseContentPack pack)
    {
        super("New Block", CREATE | CANCEL, 180, 201);
        this.pack = pack;

        lblName = label("Name:").at(7, 7).add();
        tbName = textBox().below(lblName).size(166, 17).add();
        lblType = label("Type:").below(tbName, 5).add();
        dbType = dropBox(EnumBlockType.values()).below(lblType).size(100, 15).add();
        lblInfo = label("You need to restart Minecraft\nin order to use or edit this block.").below(dbType, 10).add();

        tbName.setValidityProvider(this);
        dbType.setStringProvider(this);
        dbType.setSelectedValue(EnumBlockType.NORMAL);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            WrappedBlock block = new WrappedBlock(tbName.getText().trim(), dbType.getSelectedValue(), pack);
            block.apply();

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
            message = "Enter a name";
        else
        {
            for (WrappedBlock block : pack.getContentRegistry(WrappedBlock.class).getContentList())
            {
                if (block.getName().equals(text))
                {
                    message = "There is already a block with this name.";
                    break;
                }
            }
        }

        //btnCreate.setEnabled(message == null);
        return message;
    }

    @Override
    public String getStringFor(EnumBlockType value)
    {
        return value.name;
    }
}
