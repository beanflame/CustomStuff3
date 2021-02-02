package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.ControlContainer;

public class CheckBoxBuilder extends ControlBuilder<CheckBox>
{
    private boolean isChecked = false;
    private String text = null;

    public CheckBoxBuilder(ControlContainer c)
    {
        super(c);
    }

    public CheckBoxBuilder(String text, ControlContainer c)
    {
        super(c);
        this.text = text;
    }

    public CheckBoxBuilder checked(boolean isChecked)
    {
        this.isChecked = isChecked;
        return this;
    }

    @Override
    protected CheckBox newInstance()
    {
        CheckBox cb =  new CheckBox(text, anchor, offsetX, offsetY, container);
        cb.setIsChecked(isChecked);
        return cb;
    }
}
