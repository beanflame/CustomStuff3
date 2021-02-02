package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.worldgen.EnumWorldGenType;

public class WindowCreateWorldGen extends Window implements IValidityProvider, IStringProvider<EnumWorldGenType>
{
    private final BaseContentPack pack;

    private Label lblName;
    private TextBox tbName;
    private Label lblType;
    private DropBox<EnumWorldGenType> dbType;

    public WindowCreateWorldGen(BaseContentPack pack)
    {
        super("New World Gen", CREATE | CANCEL, 180, 201);
        this.pack = pack;

        lblName = label("Name:").at(7, 7).add();
        tbName = textBox().below(lblName).size(166, 17).add();
        lblType = label("Type:").below(tbName, 5).add();
        dbType = dropBox(EnumWorldGenType.values()).below(lblType).size(100, 15).add();

        tbName.setValidityProvider(this);
        dbType.setStringProvider(this);
        dbType.setSelectedValue(EnumWorldGenType.ORE);

        btnCreate.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            WrappedWorldGen worldGen = new WrappedWorldGen(tbName.getText().trim(), dbType.getSelectedValue(), pack);
            worldGen.container = worldGen.getType().createAttributeContainer(worldGen);
            worldGen.worldGen = worldGen.getType().createWorldGen(worldGen);
            worldGen.apply();

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
            for (WrappedWorldGen worldGen : pack.getContentRegistry(WrappedWorldGen.class).getContentList())
            {
                if (worldGen.getName().equals(text))
                {
                    message = "There is already a world gen with this name.";
                    break;
                }
            }
        }

        return message;
    }

    @Override
    public String getStringFor(EnumWorldGenType value)
    {
        return value.name;
    }
}
