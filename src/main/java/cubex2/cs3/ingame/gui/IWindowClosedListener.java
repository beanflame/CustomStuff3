package cubex2.cs3.ingame.gui;

public interface IWindowClosedListener<T extends Window>
{
    void windowClosed(T window);
}
