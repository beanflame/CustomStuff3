package cubex2.cs3.ingame.gui.control;

import com.google.common.base.Predicate;

public abstract class ValidityControl<T extends Control> extends Control implements IValidityControl
{
    protected Predicate<T> validatorFunc = null;
    protected IValueListener valueChangedListener = null;

    public ValidityControl(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
    }

    public void setValidatorFunc(Predicate<T> func)
    {
        validatorFunc = func;
        valueChanged();
    }

    @Override
    public boolean hasValidValue()
    {
        return validatorFunc == null || validatorFunc.apply((T) this);
    }

    @Override
    public void setValueChangedListener(IValueListener listener)
    {
        valueChangedListener = listener;
        valueChanged();
    }

    protected void valueChanged()
    {
        if (valueChangedListener != null)
        {
            valueChangedListener.onValueChanged(this);
        }
    }
}
