package cubex2.cs3.ingame.gui.control;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.ISelectElementCallback;
import cubex2.cs3.ingame.gui.WindowSelectBlock;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class HorizontalItemList extends ControlContainer implements IValueListener<Slider>, ISelectElementCallback<ItemStack>
{
    private ItemDisplay[] displays;
    private Slider slider;
    private int numItems;
    private List<ItemStack> stacks = Lists.newArrayList();

    public boolean canRemove = false;
    public boolean canAdd = false;
    public boolean blocksOnly = false;
    public boolean itemWildcards = false;

    public IValueListener<HorizontalItemList> listener;

    public HorizontalItemList(int numItems, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(18 * numItems, 28, anchor, offsetX, offsetY, parent);
        this.numItems = numItems;

        displays = new ItemDisplay[numItems];
        for (int i = 0; i < numItems; i++)
        {
            displays[i] = itemDisplay().left(i * 18 + 1).top(0).add();
            displays[i].setDrawSlotBackground();
        }

        slider = horizontalSlider(0).fillWidth(0).bottom(0).height(8).add();
        slider.setValueListener(this);
        slider.setWheelScrollParent(true);

        onValueChanged(slider);
    }

    public List<ItemStack> getStacks()
    {
        return stacks;
    }

    public void setItems(List<ItemStack> stacks)
    {
        this.stacks.clear();
        this.stacks.addAll(stacks);
        if (canAdd && stacks.size() >= numItems)
            this.stacks.add(null);
        slider.setMaxValue(Math.max(stacks.size() - numItems, 0) * 18);
        slider.updateScroll();
    }

    public void addItem(ItemStack stack)
    {
        if (stacks.size() > 1 && stacks.get(stacks.size() - 1) == null)
            stacks.remove(stacks.size() - 1);
        stacks.add(stack);
        if (canAdd && stacks.size() >= numItems)
            stacks.add(null);
        slider.setMaxValue(Math.max(stacks.size() - numItems, 0) * 18);
        slider.updateScroll();

        if (listener != null)
            listener.onValueChanged(this);
    }

    private void removeItem(ItemStack stack)
    {
        stacks.remove(stack);
        if (stacks.size() > 1 && stacks.get(stacks.size() - 1) == null && stacks.size() <= numItems)
            stacks.remove(stacks.size() - 1);
        slider.setMaxValue(Math.max(stacks.size() - numItems, 0) * 18);
        slider.updateScroll();

        if (listener != null)
            listener.onValueChanged(this);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        int index = ArrayUtils.indexOf(displays, c);
        if (index != -1)
        {
            if (button == 1 && canRemove && displays[index].getItemStack() != null)
                removeItem(displays[index].getItemStack());
            else if (button == 0 && canAdd && displays[index].getItemStack() == null)
            {
                if (blocksOnly)
                {
                    GuiBase.openWindow(new WindowSelectBlock(false, false, this, IS_NOT_IN_LIST));
                } else
                {
                    GuiBase.openWindow(new WindowSelectItem(itemWildcards, this));
                }
            }
        }
    }

    @Override
    public void onValueChanged(Slider slider)
    {
        for (int i = 0; i < displays.length; i++)
        {
            int stackIndex = Math.round(slider.getValue() / 18f) + i;
            if (stackIndex >= stacks.size())
                displays[i].setItemStack(null);
            else
                displays[i].setItemStack(stacks.get(stackIndex));
        }
    }

    @Override
    public void itemSelected(ItemStack element)
    {
        addItem(element);
    }

    private final Predicate<ItemStack> IS_NOT_IN_LIST = new Predicate<ItemStack>()
    {
        @Override
        public boolean apply(ItemStack input)
        {
            for (ItemStack stack : stacks)
            {
                if (ItemStackHelper.itemStackEqual(stack, input)) return false;
            }
            return true;
        }
    };
}
