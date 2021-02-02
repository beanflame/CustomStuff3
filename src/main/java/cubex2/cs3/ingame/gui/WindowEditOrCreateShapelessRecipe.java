package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.ShapelessRecipe;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class WindowEditOrCreateShapelessRecipe extends WindowEditOrCreate<ShapelessRecipe> implements IWindowClosedListener
{
    private RecipeInputDisplay[] inputDisplays;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;
    private ButtonUpDown btnIncrAmount;
    private ButtonUpDown btnDecrAmount;

    public WindowEditOrCreateShapelessRecipe(BaseContentPack pack)
    {
        super("New Shapeless Recipe", 180, 150, pack);
    }

    public WindowEditOrCreateShapelessRecipe(ShapelessRecipe recipe, BaseContentPack pack)
    {
        super("Edit Shapeless Recipe", 180, 150, recipe, pack);
    }

    @Override
    protected void initControls()
    {
        inputDisplays = new RecipeInputDisplay[9];
        for (int i = 0; i < 9; i++)
        {
            int row = i / 3;
            int col = i % 3;

            inputDisplays[i] = recipeInputDisplay().left(33 + col * 18).top(10 + row * 18).add().setDrawSlotBackground();
            inputDisplays[i].setClearOnRightClick();
        }

        resultDisplay = itemDisplay().left(121).top(28).add().setDrawSlotBackground();
        resultDisplay.setClearOnRightClick();

        btnIncrAmount = buttonUp().left(138).top(27).add();
        btnDecrAmount = buttonDown().left(138).top(36).add();

        if (editingContent != null)
        {
            for (int i = 0; i < editingContent.input.length; i++)
            {

                inputDisplays[i].setRecipeInput(editingContent.input[i]);
            }

            resultDisplay.setItemStack(editingContent.result);
        }

        pbArrow = pictureBox(Textures.CONTROLS, 218, 18).left(93).top(28).size(22, 15).add();

        updateButtons();
    }

    @Override
    protected ShapelessRecipe createContent()
    {
        ItemStack result = resultDisplay.getItemStack();
        RecipeInput[] inputs = getRecipeInput();

        return new ShapelessRecipe(inputs, result, pack);
    }

    @Override
    protected void editContent()
    {
        ItemStack result = resultDisplay.getItemStack();
        RecipeInput[] inputs = getRecipeInput();

        editingContent.result = result;
        editingContent.input = inputs;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == resultDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem(false), "result");
        } else if (c == btnIncrAmount)
        {
            int stackSize = resultDisplay.getItemStack().stackSize;
            stackSize = Math.min(resultDisplay.getItemStack().getMaxStackSize(), stackSize + (GuiBase.isShiftKeyDown() ? 5 : 1));
            resultDisplay.setStackSize(stackSize);
            updateButtons();
        } else if (c == btnDecrAmount)
        {
            int stackSize = resultDisplay.getItemStack().stackSize;
            stackSize = Math.max(1, stackSize - (GuiBase.isShiftKeyDown() ? 5 : 1));
            resultDisplay.setStackSize(stackSize);
            updateButtons();
        } else
        {
            int index = ArrayUtils.indexOf(inputDisplays, c);

            if (index != -1)
            {
                GuiBase.openWindow(new WindowSelectRecipeInput(pack), "" + index);
            } else
            {
                super.controlClicked(c, mouseX, mouseY);
            }
        }
    }

    private RecipeInput[] getRecipeInput()
    {
        List<RecipeInput> inputs = Lists.newArrayList();
        for (RecipeInputDisplay display : inputDisplays)
        {
            if (display.getRecipeInput() != null)
            {
                inputs.add(display.getRecipeInput());
            }
        }

        return inputs.toArray(new RecipeInput[inputs.size()]);
    }

    private void updateButtons()
    {
        boolean validData = resultDisplay.getItemStack() != null && getRecipeInput().length > 0;

        if (editingContent == null)
        {
            btnCreate.setEnabled(validData);
        } else
        {
            btnEdit.setEnabled(validData);
        }

        ItemStack result = resultDisplay.getItemStack();
        btnIncrAmount.setEnabled(result != null && result.stackSize < result.getMaxStackSize());
        btnDecrAmount.setEnabled(result != null && result.stackSize > 1);
    }

    @Override
    public void windowClosed(Window window)
    {
        if (window.tag.equals("result"))
        {
            WindowSelectItem w = (WindowSelectItem) window;
            if (w.getSelectedStack() != null)
            {
                resultDisplay.setItemStack(w.getSelectedStack());
                updateButtons();
            }
        } else
        {
            WindowSelectRecipeInput w = (WindowSelectRecipeInput) window;
            if (w.getSelectedInput() != null)
            {
                inputDisplays[Integer.parseInt(w.tag)].setRecipeInput(w.getSelectedInput());
                updateButtons();
            }
        }
    }
}
