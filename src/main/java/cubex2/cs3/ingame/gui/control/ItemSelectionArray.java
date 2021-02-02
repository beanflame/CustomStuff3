package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.Filter;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

public class ItemSelectionArray extends ControlContainer implements IValueListener<Slider>
{
    private Slider slider;
    private final ItemDisplay[] displays;
    private TextBox tbSearch;

    private final ArrayList<ItemStack> stacks;
    private final ArrayList<ItemStack> allStacks;
    private int selectedIndex = -1;
    private int currentScroll = 0;
    private int maxScroll;

    private final int rows = 7;
    private final int columns = 7;
    private final int size = 23;

    public Filter<ItemStack> filter;
    public IValueListener<ItemSelectionArray> listener;

    public ItemSelectionArray(ArrayList<ItemStack> stacks, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);

        this.allStacks = stacks;
        this.stacks = Lists.newArrayList(stacks);

        displays = new ItemDisplay[rows * columns];

        updateMaxScroll();

        tbSearch = textBox().fillWidth(0).bottom(0).add();

        slider = verticalSlider(calculateTotalHeight()).top(0).height(rows * size - 5).right(0).width(20).add();
        slider.setValueListener(this);
        slider.setWheelScrollEverywhere(true);
        slider.setWheelScrollStep(size);

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < columns; col++)
            {
                displays[col + row * columns] = itemDisplay().left(col * size).top(row * size + 1).add().setDrawSlotBackground();
            }
        }

        updateItems();
    }

    private int calculateTotalHeight()
    {
        if (maxScroll <= rows - 1)
            return 0;

        int numRows = stacks.size() / columns + (stacks.size() % columns != 0 ? 1 : 0);
        if (numRows > rows)
            numRows -= rows - 1;
        return numRows * (size - 1) + (numRows - 1) * 1;
    }

    private void updateItems()
    {
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < columns; col++)
            {
                int index = col + (row + currentScroll) * columns;
                ItemStack stack = index >= stacks.size() ? null : stacks.get(index);

                displays[col + row * columns].setItemStack(stack);
                displays[col + row * columns].setEnabled(stack != null);
                displays[col + row * columns].setVisible(stack != null);
            }
        }
    }

    public ItemStack getSelectedStack()
    {
        return selectedIndex < 0 ? null : stacks.get(selectedIndex);
    }

    public TextBox getSearchBox()
    {
        return tbSearch;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c instanceof ItemDisplay)
        {
            int index = ArrayUtils.indexOf(displays, c);
            if (index != -1)
            {
                selectedIndex = index + currentScroll * columns;
                if (listener != null)
                {
                    listener.onValueChanged(this);
                }
            }
        }
    }

    @Override
    public void keyTyped(char c, int key)
    {
        String prev = tbSearch.getText();
        super.keyTyped(c, key);
        String now = tbSearch.getText();
        if (!prev.equals(now))
        {
            filterChanged(tbSearch.getText());
        }
    }

    private void filterChanged(String filterText)
    {
        stacks.clear();
        for (ItemStack stack : allStacks)
        {
            if (filter.matches(stack, filterText))
                stacks.add(stack);
        }

        selectedIndex = -1;
        if (listener != null)
            listener.onValueChanged(this);
        updateMaxScroll();
        currentScroll = 0;
        slider.setMaxValue(calculateTotalHeight());

        updateItems();
    }

    private void updateMaxScroll()
    {
        maxScroll = stacks.size() / columns;
        if ((stacks.size() % columns) != 0)
            maxScroll += 1;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        super.draw(mouseX, mouseY, renderTick);

        if (selectedIndex != -1)
        {
            int displayIndex = selectedIndex - currentScroll * columns;
            if (displayIndex >= 0 && displayIndex < displays.length)
            {
                ItemDisplay display = displays[displayIndex];
                GuiHelper.drawBorder(display.getX() - 2, display.getY() - 2, display.getX() + display.getWidth() + 2, display.getY() + display.getHeight() + 2, Color.RED);
            }
        }
    }

    @Override
    public void onValueChanged(Slider control)
    {
        currentScroll = MathHelper.clamp_int(control.getValue() / size, 0, maxScroll);
        updateItems();
    }
}
