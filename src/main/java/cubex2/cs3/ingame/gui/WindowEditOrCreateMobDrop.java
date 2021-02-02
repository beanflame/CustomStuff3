package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.MobDrop;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.TextBoxValidators;
import cubex2.cs3.lib.Validators;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.util.MathHelper;

public class WindowEditOrCreateMobDrop extends WindowEditOrCreate<MobDrop> implements IWindowClosedListener
{
    private DropBox<String> dbMob;
    private ItemDisplay display;
    private TextBox tbChance;
    private CheckBox cbPlayerKillOnly;
    private ButtonUpDown btnUp;
    private ButtonUpDown btnDown;

    public WindowEditOrCreateMobDrop(BaseContentPack pack)
    {
        super("New Mob Drop", 180, 225, pack);
    }

    public WindowEditOrCreateMobDrop(MobDrop drop, BaseContentPack pack)
    {
        super("Edit Mob Drop", 180, 225, drop, pack);
    }

    @Override
    protected void initControls()
    {
        row("Mob:");
        dbMob = row(dropBox(GeneralHelper.getMobNames()));
        dbMob.setSelectedValue("Creeper");

        row("Item:");
        display = row(itemDisplay());
        display.setDrawSlotBackground();
        display.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        display.useSelectItemDialog(false);

        btnUp = buttonUp().rightTo(display).add();
        btnDown = buttonDown().rightTo(display).add();

        row("Chance:");
        tbChance = row(textBox());
        tbChance.setValidityProvider(TextBoxValidators.FLOAT_ZERO_ONE);

        cbPlayerKillOnly = row(checkBox("player kill only").width(10));

        if (editingContent != null)
        {
            dbMob.setSelectedValue(editingContent.mob);
            display.setItemStack(editingContent.stack.copy());
            tbChance.setText(String.valueOf(editingContent.chance));
            cbPlayerKillOnly.setIsChecked(editingContent.playerKillOnly);
        } else
        {
            tbChance.setText("1.0");
        }

        updateButtons();
    }

    private void updateButtons()
    {
        btnUp.setEnabled(display.getItemStack() != null && display.getItemStack().stackSize < display.getItemStack().getMaxStackSize());
        btnDown.setEnabled(display.getItemStack() != null && display.getItemStack().stackSize > 1);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnUp || c == btnDown)
        {
            int numChange = (GuiBase.isShiftKeyDown() ? 5 : 1) * (c == btnUp ? 1 : -1);
            display.setStackSize(MathHelper.clamp_int(display.getItemStack().stackSize + numChange, 1, display.getItemStack().getMaxStackSize()));
            updateButtons();
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    protected MobDrop createContent()
    {
        return new MobDrop(dbMob.getSelectedValue(), display.getItemStack(), Float.parseFloat(tbChance.getText()), cbPlayerKillOnly.getIsChecked(), pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.mob = dbMob.getSelectedValue();
        editingContent.stack = display.getItemStack();
        editingContent.chance = Float.parseFloat(tbChance.getText());
        editingContent.playerKillOnly = cbPlayerKillOnly.getIsChecked();
    }

    @Override
    public void windowClosed(Window window)
    {
        updateButtons();
    }
}
