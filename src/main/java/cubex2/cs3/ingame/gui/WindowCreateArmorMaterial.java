package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ArmorMaterial;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowCreateArmorMaterial extends Window
{
    private final BaseContentPack pack;

    private TextBox tbName;
    private NumericUpDown nupDurability;
    private NumericUpDown nupEnchantability;
    private NumericUpDown[] nupReduceAmounts;

    public WindowCreateArmorMaterial(BaseContentPack pack)
    {
        super("New Armor Material", CREATE | CANCEL, 180, 195);
        this.pack = pack;

        row("Name:");
        tbName = row(textBox());
        tbName.setValidityProvider(TextBoxValidators.NEW_ARMOR_MATERIAL_NAME);

        row("Durability:", Strings.INFO_DURABILITY_ARMOR);

        ControlContainer c = row(container().height(20));
        nupDurability = c.col(c.numericUpDown().width((180 - 14 - 2) / 2), 3);
        nupDurability.setMinValue(1);
        nupDurability.setValue(1);

        nupEnchantability = c.col(c.numericUpDown().width((180 - 14 - 4) / 2), 3);

        label("Enchantability:").left(nupEnchantability, 0, true).bottom(nupEnchantability, 4).add();

        row("Reduction Amounts", Strings.INFO_ARMOR_REDUCTION_AMOUNTS, 11);

        String[] names = {"Helmet", "Plate", "Legs", "Boots"};
        nupReduceAmounts = new NumericUpDown[4];
        for (int i = 0; i < 4; i += 2)
        {
            row(names[i] + ":");

            c = row(container().height(20));
            nupReduceAmounts[i] = c.col(c.numericUpDown().width((180 - 14 - 2) / 2), 3);
            nupReduceAmounts[i].setValue(1);
            nupReduceAmounts[i].setMinValue(1);
            nupReduceAmounts[i + 1] = c.col(c.numericUpDown().width((180 - 14 - 4) / 2), 3);
            nupReduceAmounts[i + 1].setValue(1);
            nupReduceAmounts[i + 1].setMinValue(1);

            label(names[i + 1] + ":").left(nupReduceAmounts[i + 1], 0, true).bottom(nupReduceAmounts[i + 1], 4).add();
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            int[] amounts = new int[4];
            for (int i = 0; i < 4; i++)
            {
                amounts[i] = nupReduceAmounts[i].getValue();
            }
            ArmorMaterial material = new ArmorMaterial(tbName.getText(), nupDurability.getValue(), nupEnchantability.getValue(), amounts, pack);
            material.apply();

            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
