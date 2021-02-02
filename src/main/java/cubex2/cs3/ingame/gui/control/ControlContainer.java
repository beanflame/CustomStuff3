package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.builder.*;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.tileentity.TileEntityInventory;
import cubex2.cs3.util.RecipeInput;
import cubex2.cs3.util.ScissorHelper;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ControlContainer extends Control
{
    public ControlContainer defaultBuilderContainer = this;
    public boolean defaultNoOffset = false;
    public boolean enableScissor = false;
    protected List<Control> controls = Lists.newArrayList();

    /**
     * The control that has been added last
     */
    public Control lastControl;

    public ControlContainer(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
    }

    public void addControl(Control c)
    {
        if (rootControl instanceof Window && c instanceof IValidityControl)
        {
            ((Window) rootControl).addValidityControl((IValidityControl) c);
        }

        if (c.tabStop)
        {
            rootControl.tabStopControls.add(c);
        }

        controls.add(c);
        lastControl = c;
    }

    public List<Control> getControls()
    {
        return controls;
    }

    public Control getControlAt(int x, int y)
    {
        for (int i = controls.size() - 1; i >= 0; i--)
        {
            Control c = controls.get(i);
            if (c.isMouseOverControl(x, y))
            {
                if (c instanceof ControlContainer)
                {
                    ControlContainer cc = (ControlContainer) c;
                    Control c1 = cc.getControlAt(x, y);
                    return c1 != null ? c1 : cc;
                } else
                {
                    return c;
                }
            }
        }

        if (isMouseOverControl(x, y))
        {
            return this;
        }
        return null;
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        for (Control control : controls)
        {
            control.onParentResized();
        }
    }

    @Override
    public void onUpdate()
    {
        int first = firstControl();
        int num = numControls();
        for (int i = first; i < first + num && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            c.onUpdate();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        boolean wasLocked = GuiBase.inputLockedControl != null;

        int first = firstControl();
        int num = numControls();
        for (int i = first; i < first + num && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled() && c.isVisible())
            {
                if (!c.canHandleInput())
                    continue;

                boolean clickedControl = c.isMouseOverControl(mouseX, mouseY);// c.bounds.contains(mouseX, mouseY);
                c.mouseClicked(mouseX, mouseY, button, clickedControl);
                if (clickedControl)
                {
                    c.mouseDown(mouseX, mouseY, button);
                    controlClicked(c, mouseX, mouseY, button);
                    if (button == 0)
                        controlClicked(c, mouseX, mouseY);
                }

                boolean isLocked = GuiBase.inputLockedControl != null;
                if (wasLocked && !isLocked) // control released input
                    break; // make sure this click isn't received by other controls
            }
        }
    }

    @Override
    public boolean canHandleInput()
    {
        if (GuiBase.inputLockedControl == null)
            return true;

        for (Control control : controls)
        {
            if (control == GuiBase.inputLockedControl)
                return true;
            if (control instanceof ControlContainer)
                if (control.canHandleInput())
                    return true;
        }
        return false;
    }

    /**
     * This is called when a control has been clicked
     *
     * @param c      The clicked control.
     * @param mouseX The x-coord of the mouse
     * @param mouseY The y-coord of the mouse
     * @param button The mouse button (0=left, 1=right)
     */
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {

    }

    /**
     * This is called when a control has been left-clicked
     *
     * @param c      The clicked control.
     * @param mouseX The x-coord of the mouse
     * @param mouseY The y-coord of the mouse
     */
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (parent instanceof ControlContainer)
            ((ControlContainer) parent).controlClicked(c, mouseX, mouseY);
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        boolean wasLocked = GuiBase.inputLockedControl != null;

        int first = firstControl();
        int num = numControls();
        for (int i = first; i < first + num && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled() && c.isVisible())
            {
                if (!c.canHandleInput())
                    continue;

                c.mouseUp(mouseX, mouseY, button);

                boolean isLocked = GuiBase.inputLockedControl != null;
                if (wasLocked && !isLocked) // control released input
                    break; // make sure this click isn't received by other controls
            }
        }
    }

    @Override
    public void keyTyped(char c, int key)
    {
        int first = firstControl();
        int num = numControls();
        for (int i = first; i < first + num && i < controls.size(); i++)
        {
            Control c_ = controls.get(i);
            if (c_.isEnabled() && c_.isVisible())
            {
                if (!c_.canHandleInput())
                    continue;

                c_.keyTyped(c, key);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (enableScissor)
            ScissorHelper.startScissor(mc, bounds);

        int first = firstControl();
        int num = numControls();
        for (int i = first; i < first + num && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible())
            {
                c.draw(mouseX, mouseY, renderTick);
            }
        }

        if (enableScissor)
            ScissorHelper.endScissor();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        int first = firstControl();
        int num = numControls();
        for (int i = first; i < first + num && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible())
            {
                c.drawForeground(mouseX, mouseY);
            }
        }
    }

    protected int firstControl()
    {
        return 0;
    }

    protected int numControls()
    {
        return controls.size();
    }

    private Control lastRow;
    private Control lastCol;

    /* Layout Builders */
    public Label row(String text, String infoText)
    {
        Label label = row(text);
        infoButton(infoText).rightTo(label).add();
        return label;
    }

    public Label row(String text)
    {
        return row(label(text));
    }

    public Label row(String text, int offset)
    {
        return row(label(text), offset, defaultNoOffset);
    }

    public Label row(String text, String infoText, int offset)
    {
        Label label = row(label(text), offset, defaultNoOffset);
        infoButton(infoText).rightTo(label).add();
        return label;
    }

    /**
     * Don't use any methods but size on the builder.
     */
    public <T extends Control> T row(ControlBuilder<T> builder)
    {
        return row(builder, 4, defaultNoOffset);
    }

    public <T extends Control> T row(ControlBuilder<T> builder, boolean noOffset)
    {
        return row(builder, 4, noOffset);
    }

    /**
     * Don't use any methods but size on the builder.
     */
    public <T extends Control> T row(ControlBuilder<T> builder, int offset, boolean noOffset)
    {
        if (defaultBuilderContainer != this)
            return defaultBuilderContainer.row(builder, offset, noOffset);

        if (builder.width == 0 && !(builder instanceof LabelBuilder) && !(builder instanceof CheckBoxBuilder))
            builder.right(7);

        if (lastRow == null)
            lastRow = builder.top(noOffset ? 0 : 7).left(noOffset ? 0 : 7).add();
        else
            lastRow = builder.top(lastRow, offset).left(noOffset ? 0 : 7).add();

        return (T) lastRow;
    }

    public <T extends Control> T col(ControlBuilder<T> builder)
    {
        return col(builder, 3);
    }

    public <T extends Control> T col(ControlBuilder<T> builder, int offset)
    {
        if (lastCol == null)
            lastCol = builder.left(0).fillHeight(0).add();
        else
            lastCol = builder.left(lastCol, offset).fillHeight(0).add();

        return (T) lastCol;
    }

    /* Builders */
    public LabelBuilder label(String text)
    {
        return new LabelBuilder(text, defaultBuilderContainer);
    }

    public TextBoxBuilder textBox()
    {
        return new TextBoxBuilder(defaultBuilderContainer);
    }

    public <T> DropBoxBuilder<T> dropBox(T[] values)
    {
        return new DropBoxBuilder<T>(values, defaultBuilderContainer);
    }

    public ButtonBuilder button(String text)
    {
        return new ButtonBuilder(text, defaultBuilderContainer);
    }

    public ItemDisplayBuilder itemDisplay()
    {
        return new ItemDisplayBuilder(null, defaultBuilderContainer);
    }

    public ItemDisplayBuilder itemDisplay(ItemStack stack)
    {
        return new ItemDisplayBuilder(stack, defaultBuilderContainer);
    }

    public InfoButtonBuilder infoButton(String text)
    {
        return new InfoButtonBuilder(text, defaultBuilderContainer);
    }

    public ButtonUpDownBuilder buttonUpDown(boolean up)
    {
        return new ButtonUpDownBuilder(up, defaultBuilderContainer);
    }

    public ButtonUpDownBuilder buttonUp()
    {
        return buttonUpDown(true);
    }

    public ButtonUpDownBuilder buttonDown()
    {
        return buttonUpDown(false);
    }

    public PlayerDisplayBuilder playerDisplay()
    {
        return new PlayerDisplayBuilder(defaultBuilderContainer);
    }

    public CheckBoxBuilder checkBox()
    {
        return new CheckBoxBuilder(defaultBuilderContainer);
    }

    public CheckBoxBuilder checkBox(boolean checked)
    {
        return new CheckBoxBuilder(defaultBuilderContainer).checked(checked);
    }

    public CheckBoxBuilder checkBox(String text)
    {
        return new CheckBoxBuilder(text, defaultBuilderContainer);
    }

    public CheckBoxBuilder checkBox(String text, boolean checked)
    {
        return new CheckBoxBuilder(text, defaultBuilderContainer).checked(checked);
    }

    public TextFieldBuilder textField()
    {
        return new TextFieldBuilder(defaultBuilderContainer);
    }

    public PictureBoxBuilder pictureBox(ResourceLocation texture, int u, int v)
    {
        return new PictureBoxBuilder(texture, u, v, defaultBuilderContainer);
    }

    public WorldDisplayBuilder worldDisplay(SimulatedWorld world)
    {
        return new WorldDisplayBuilder(world, defaultBuilderContainer);
    }

    public RecipeInputDisplayBuilder recipeInputDisplay()
    {
        return new RecipeInputDisplayBuilder(null, defaultBuilderContainer);
    }

    public RecipeInputDisplayBuilder recipeInputDisplay(RecipeInput input)
    {
        return new RecipeInputDisplayBuilder(input, defaultBuilderContainer);
    }

    public TabControlBuilder tabControl(int tabWidth, int tabHeight)
    {
        return new TabControlBuilder(tabWidth, tabHeight, defaultBuilderContainer);
    }

    public SliderBuilder verticalSlider(int maxValue)
    {
        return new SliderBuilder(Slider.Direction.VERTICAL, maxValue, defaultBuilderContainer);
    }

    public SliderBuilder horizontalSlider(int maxValue)
    {
        return new SliderBuilder(Slider.Direction.HORIZONTAL, maxValue, defaultBuilderContainer);
    }

    public <T> ListBoxBuilder<T> listBox(ListBoxDescription<T> desc)
    {
        return new ListBoxBuilder<T>(desc, defaultBuilderContainer);
    }

    public ContainerBuilder container()
    {
        return new ContainerBuilder(defaultBuilderContainer);
    }

    public BlockDisplayBuilder blockDisplay(Block block)
    {
        return blockDisplay(block, 0);
    }

    public BlockDisplayBuilder blockDisplay(Block block, int meta)
    {
        return new BlockDisplayBuilder(block, meta, defaultBuilderContainer);
    }

    public NumericUpDownBuilder numericUpDown()
    {
        return new NumericUpDownBuilder(defaultBuilderContainer);
    }

    public HorizontalItemListBuilder horItemList(int numItems)
    {
        return new HorizontalItemListBuilder(numItems, defaultBuilderContainer);
    }

    public ScrollContainerBuilder scrollContainer(int totalHeight)
    {
        return new ScrollContainerBuilder(totalHeight, defaultBuilderContainer);
    }

    public ButtonDocBuilder buttonDoc(String text, String path)
    {
        return new ButtonDocBuilder(text, path, defaultBuilderContainer);
    }

    public IconTextBoxBuilder iconTextBox(BaseContentPack pack, String subFolder)
    {
        return new IconTextBoxBuilder(pack, subFolder, defaultBuilderContainer);
    }

    public TextureTextBoxBuilder textureTextBox(BaseContentPack pack, String subFolder)
    {
        return new TextureTextBoxBuilder(pack, subFolder, defaultBuilderContainer);
    }

    public PlayerInventoryAreaBuilder playerInventoryArea()
    {
        return new PlayerInventoryAreaBuilder(defaultBuilderContainer);
    }

    public InventorySlotBuilder inventorySlot()
    {
        return new InventorySlotBuilder(defaultBuilderContainer);
    }

    public ImageProgressBarBuilder imageProgressBar(ResourceLocation texture, int u, int v, int direction)
    {
        return new ImageProgressBarBuilder(texture, u, v, direction, this);
    }

    public CookProgressBarBuilder cookProgressBar(String name, TileEntityInventory tile, ResourceLocation texture, int u, int v, int direction)
    {
        return new CookProgressBarBuilder(name, tile, texture, u, v, direction, this);
    }

    public FuelProgressBarBuilder fuelProgressBar(String name, TileEntityInventory tile, ResourceLocation texture, int u, int v, int direction)
    {
        return new FuelProgressBarBuilder(name, tile, texture, u, v, direction, this);
    }
}
