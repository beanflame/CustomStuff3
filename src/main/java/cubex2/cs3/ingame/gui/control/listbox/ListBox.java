package cubex2.cs3.ingame.gui.control.listbox;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.ingame.gui.control.builder.ControlBuilder;
import cubex2.cs3.util.Filter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListBox<T> extends ControlContainer implements IValueListener<Slider>
{
    public static final int HORIZONTAL_GAP = 1;
    public static final int VERTICAL_GAP = 1;

    private Slider slider;
    private ControlContainer scrollerWindow;
    private ControlContainer itemContainer;
    private TextBox tbSearch;

    private Comparator<T> comparator;
    private Filter<T> filter;
    private final List<T> allElements;

    private final List<T> elements;
    private final List<Integer> selectedIndices = Lists.newArrayList();
    private int currentScroll = 0;

    private final boolean multiSelect;
    private final boolean canSelect;
    private final boolean isSorted;
    private final int columns;
    private final int elementHeight;
    private final int elementWidth;
    private final int listBoxItemMeta;

    private IListBoxItemClickListener<T> itemClickListener;
    private IListBoxItemProvider itemProvider;

    private int mouseX = -1;
    private int mouseY = -1;

    public ListBox(ListBoxDescription<T> desc, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);

        multiSelect = desc.multiSelect;
        canSelect = desc.canSelect;
        isSorted = desc.sorted;
        columns = desc.columns;
        elementHeight = desc.elementHeight;
        elementWidth = desc.elementWidth;
        listBoxItemMeta = desc.listBoxItemMeta;
        comparator = desc.comparator;
        itemProvider = desc.itemProvider;

        allElements = Lists.newArrayList(desc.elements);
        elements = Lists.newArrayList(desc.elements);
        sortElements();

        if (desc.hasSearchBar)
        {
            filter = desc.filter;
            tbSearch = textBox().fillWidth(0).bottom(0).add();
        }

        slider = verticalSlider(calculateTotalHeight()).top(0).bottom(desc.hasSearchBar ? 17 : 0).right(0).width(desc.sliderWidth).add();
        slider.setValueListener(this);
        slider.setWheelScrollEverywhere(true);
        slider.setWheelScrollStep(elementHeight + VERTICAL_GAP);

        scrollerWindow = container().at(0, 0).bottom(slider, 0, true).right(slider, 3).add();
        scrollerWindow.enableScissor = true;
        itemContainer = new ItemContainerBuilder<T>(this, scrollerWindow).fill().add();

        createListBoxItems();

        if (rootControl instanceof IListBoxItemClickListener)
        {
            itemClickListener = (IListBoxItemClickListener<T>) rootControl;
        }
    }

    private void sortElements()
    {
        if (isSorted)
        {
            if (comparator != null)
            {
                Collections.sort(elements, comparator);
            } else
            {
                Collections.sort((List<Comparable>) elements);
            }
        }
    }

    /**
     * Makes wheel scroll and the use of pgDown... keys only work if mouse is over listbox.
     */
    public void disableGlobalScrolling()
    {
        slider.setWheelScrollEverywhere(false);
        slider.setWheelScrollParent(true);
    }

    public TextBox getSearchBox()
    {
        return tbSearch;
    }

    private int elementWidth()
    {
        if (elementWidth == -1 && columns > 1)
            return (itemContainer.getWidth() - (columns - 1) * HORIZONTAL_GAP) / columns;
        return elementWidth;
    }

    private void createListBoxItems()
    {
        itemContainer.getControls().clear();
        for (int i = 0; i < elements.size(); i++)
        {
            int elementX = (elementWidth() + HORIZONTAL_GAP) * (i % columns);
            int elementY = i / columns * (elementHeight + VERTICAL_GAP);

            Anchor anchor = new Anchor(elementX, -1, elementY, -1);
            anchor.controlLeft = itemContainer;
            anchor.controlTop = itemContainer;
            anchor.sameSideLeft = true;
            anchor.sameSideTop = true;
            if (elementWidth() == -1)
            {
                anchor.controlRight = itemContainer;
                anchor.sameSideRight = true;
                anchor.distanceLeft = 0;
                anchor.distanceRight = 0;
            }
            itemContainer.addControl(itemProvider.createListBoxItem(elements.get(i), i, listBoxItemMeta, elementWidth(), elementHeight, anchor, 0, 0, itemContainer));
        }
    }

    private int calculateTotalHeight()
    {
        int numRows = elements.size() / columns + (elements.size() % columns != 0 ? 1 : 0);
        return numRows * elementHeight + (numRows - 1) * VERTICAL_GAP;
    }

    /**
     * Gets the selected index.
     *
     * @return The selected index or -1 if no item is selected.
     */
    public int getSelectedIndex()
    {
        return selectedIndices.size() != 0 ? selectedIndices.get(0) : -1;
    }

    /**
     * Gets the selected item.
     *
     * @return The selected item or null if no item is selected.
     */
    public T getSelectedItem()
    {
        return getSelectedIndex() != -1 ? elements.get(getSelectedIndex()) : null;
    }

    public List<Integer> getSelectedIndices()
    {
        return Collections.unmodifiableList(selectedIndices);
    }

    public List<T> getSelectedItems()
    {
        List<T> items = Lists.newArrayList();
        for (Integer i : getSelectedIndices())
        {
            items.add(elements.get(i));
        }
        return Collections.unmodifiableList(items);
    }

    public void removeSelection()
    {
        selectedIndices.clear();
        for (Control control : itemContainer.getControls())
        {
            ((ListBoxItem) control).setSelected(false);
        }
    }

    public void updateElements(List<T> newElements)
    {
        updateElements(newElements, null, null);
    }

    public void updateElements(List<T> newElements, Filter<T> filter, String searchText)
    {
        elements.clear();
        elements.addAll(newElements);

        if (filter != null)
        {
            for (int i = 0; i < elements.size(); i++)
            {
                try
                {
                    if (!filter.matches(elements.get(i), searchText))
                        elements.remove(i--);
                } catch (Exception e)
                {
                    elements.remove(i--);
                }
            }
        } else
        {
            allElements.clear();
            allElements.addAll(newElements);
        }

        sortElements();

        selectedIndices.clear();

        createListBoxItems();
        onParentResized();

        slider.updateScroll();
    }

    public void select(T item)
    {
        if (elements.contains(item))
        {
            for (int i = 0; i < elements.size(); i++)
            {
                if (elements.get(i).equals(item))
                    select(i);
            }
        }
    }

    public void select(int index)
    {
        if (index < elements.size())
        {
            if (!multiSelect)
            {
                selectedIndices.clear();
            }
            selectedIndices.add(index);

            for (Integer idx : selectedIndices)
            {
                ((ListBoxItem<T>) itemContainer.getControls().get(idx)).setSelected(true);
            }

            Collections.sort(selectedIndices);
        }
    }

    public void select(List<T> items)
    {
        if (!multiSelect) return;
        for (T t : items)
        {
            select(t);
        }
    }

    private void setScroll(int value)
    {
        if (currentScroll != value)
        {
            currentScroll = value;
            itemContainer.offsetY = -currentScroll;
            onParentResized();
        }
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        slider.setMaxValue(calculateTotalHeight() - slider.getHeight());
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (tbSearch == null)
        {
            super.keyTyped(c, key);
        } else
        {
            String prev = tbSearch.getText();
            super.keyTyped(c, key);
            String now = tbSearch.getText();
            if (!prev.equals(now))
            {
                updateElements(allElements, filter, now);
                rootControl.claimFocus(tbSearch);
            }
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c instanceof ListBoxItem)
        {
            ListBoxItem<T> lbItem = (ListBoxItem<T>) c;

            if (canSelect)
            {
                if (button == 0 && !selectedIndices.contains(lbItem.index))
                {
                    if (!multiSelect)
                    {
                        selectedIndices.clear();
                        for (Control control : itemContainer.getControls())
                        {
                            ((ListBoxItem) control).setSelected(false);
                        }
                    }
                    selectedIndices.add(lbItem.index);
                    lbItem.setSelected(true);
                } else if (button == 1 && selectedIndices.contains(lbItem.index))
                {
                    selectedIndices.remove((Integer) lbItem.index);
                    lbItem.setSelected(false);
                }
                Collections.sort(selectedIndices);
            }

            if (itemClickListener != null)
            {
                itemClickListener.itemClicked(lbItem.value, this, button);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        super.draw(mouseX, mouseY, renderTick);
    }

    @Override
    public void onValueChanged(Slider slider)
    {
        setScroll(slider.getValue());
    }

    private static final class ItemContainer<T> extends ControlContainer
    {
        private final ListBox<T> listBox;

        public ItemContainer(ListBox<T> listBox, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
        {
            super(width, height, anchor, offsetX, offsetY, parent);
            this.listBox = listBox;
        }

        @Override
        protected void controlClicked(Control c, int mouseX, int mouseY, int button)
        {
            listBox.controlClicked(c, mouseX, mouseY, button);
        }

        @Override
        protected int firstControl()
        {
            return listBox.currentScroll / (listBox.elementHeight + VERTICAL_GAP) * listBox.columns;
        }

        @Override
        protected int numControls()
        {
            return listBox.scrollerWindow.getHeight() / listBox.elementHeight * listBox.columns + listBox.columns;
        }

        @Override
        public boolean isMouseOverControl(int mouseX, int mouseY)
        {
            return listBox.scrollerWindow.isMouseOverControl(mouseX, mouseY);
        }
    }

    private static final class ItemContainerBuilder<T> extends ControlBuilder<ItemContainer<T>>
    {
        private final ListBox<T> listBox;

        public ItemContainerBuilder(ListBox<T> listBox, ControlContainer c)
        {
            super(c);
            this.listBox = listBox;
        }

        @Override
        protected ItemContainer<T> newInstance()
        {
            return new ItemContainer<T>(listBox, width, height, anchor, offsetX, offsetY, container);
        }
    }
}
