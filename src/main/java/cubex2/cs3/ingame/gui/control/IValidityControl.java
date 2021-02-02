package cubex2.cs3.ingame.gui.control;

public interface IValidityControl
{
    boolean hasValidValue();

    void setValueChangedListener(IValueListener listener);
}
