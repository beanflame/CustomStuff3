package cubex2.cs3.ingame.gui.control.listbox;

public interface IListBoxItemClickListener<T>
{
    void itemClicked(T item, ListBox<T> listBox, int button);
}
