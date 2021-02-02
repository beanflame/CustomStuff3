package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.NumericUpDown;

public class WindowEditInteger extends Window
{
    protected AttributeContainer container;
    private String fieldName;

    private NumericUpDown nup;

    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;

    public WindowEditInteger(AttributeData attributeData, AttributeContainer container)
    {
        super(attributeData.getDisplayName(), EDIT | CANCEL, 150, 55);
        this.container = container;
        this.fieldName = attributeData.field.getName();

        String info = attributeData.attribute.additionalInfo();
        if (info.length() > 0)
        {
            minValue = Integer.parseInt(info.split("-")[0]);
            maxValue = Integer.parseInt(info.split("-")[1]);
        }
        initControls(attributeData.desc);
    }

    public WindowEditInteger(String fieldName, String desc, int min, int max, AttributeContainer container)
    {
        super(fieldName, EDIT | CANCEL, 150, 55);
        this.container = container;
        this.fieldName = fieldName;
        minValue = min;
        maxValue = max;

        initControls(desc);
    }

    private void initControls(String desc)
    {
        if (desc != null)
        {
            infoButton(desc).right(7).top(13).add();
            nup = numericUpDown().top(7).left(7).right(lastControl, 3).add();
        } else
        {
            nup = numericUpDown().top(7).fillWidth(7).add();
        }
        nup.setMinValue(minValue);
        nup.setMaxValue(maxValue);
        nup.setValue((Integer) container.getAttribute(fieldName));
    }

    @Override
    protected void handleEditButtonClicked()
    {
        container.setAttribute(fieldName, nup.getValue());
        applyChangedValue();
        container.getPack().save();

        GuiBase.openPrevWindow();
    }

    protected void applyChangedValue()
    {

    }
}
