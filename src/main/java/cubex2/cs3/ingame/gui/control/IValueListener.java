package cubex2.cs3.ingame.gui.control;

public interface IValueListener<T extends Control>
{
    void onValueChanged(T control);
}
