package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowEditGenDimension extends WindowEditWorldGenAttribute
{
    private CheckBox cbOverworld;
    private CheckBox cbNether;
    private CheckBox cbEnd;

    public WindowEditGenDimension(WrappedWorldGen wrappedWorldGen)
    {
        super(wrappedWorldGen, "dimensions", 150, 95);

        cbOverworld = checkBox("Overworld", container.generateInOverworld).at(7, 7).add();
        cbNether = checkBox("Nether", container.generateInNether).below(cbOverworld).add();
        cbEnd = checkBox("End", container.generateInEnd).below(cbNether).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        super.controlClicked(c, mouseX, mouseY);

        btnEdit.setEnabled(cbOverworld.getIsChecked() || cbNether.getIsChecked() || cbEnd.getIsChecked());
    }

    @Override
    protected void applyChanges()
    {
        container.generateInOverworld = cbOverworld.getIsChecked();
        container.generateInNether = cbNether.getIsChecked();
        container.generateInEnd = cbEnd.getIsChecked();
    }
}
