package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import net.minecraft.util.MathHelper;

public class NumericUpDown extends ControlContainer
{
    private TextBox tb;
    private ButtonUpDown btnUp;
    private ButtonUpDown btnDown;
    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;

    public NumericUpDown(int width, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, 20, anchor, offsetX, offsetY, parent);

        tb = textBox().height(-1).left(0).top(0).bottom(0).right(0).add();
        tb.setNumbersOnly(true);

        btnUp = buttonUp().top(1).right(1).add();
        btnDown = buttonDown().bottom(1).right(1).add();

        updateControls();
    }

    public void setMinValue(int value)
    {
        minValue = value;
        updateControls();
    }

    public void setMaxValue(int value)
    {
        maxValue = value;
        updateControls();
    }

    public void setValue(int value)
    {
        value = MathHelper.clamp_int(value, minValue, maxValue);
        tb.setText(value + "");

        btnUp.setEnabled(value < maxValue);
        btnDown.setEnabled(value > minValue);
    }

    public int getValue()
    {
        updateControls();
        return Integer.parseInt(tb.getText());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        super.mouseClicked(mouseX, mouseY, button, intoControl);

        if (!tb.hasFocus())
        {
            updateControls();
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnUp)
        {
            setValue(getValue() + (GuiBase.isShiftKeyDown() ? 5 : 1));
        } else if (c == btnDown)
        {
            setValue(getValue() - (GuiBase.isShiftKeyDown() ? 5 : 1));
        }
    }

    private void updateControls()
    {
        try
        {
            if (tb.getText().length() == 0 || Integer.parseInt(tb.getText()) < minValue)
                tb.setText(minValue + "");
            else if (Integer.parseInt(tb.getText()) > maxValue)
                tb.setText(maxValue + "");
        } catch (NumberFormatException e)
        {
            tb.setText(maxValue + "");
        }

        int value = Integer.parseInt(tb.getText());
        btnUp.setEnabled(value < maxValue);
        btnDown.setEnabled(value > minValue);
    }
}
