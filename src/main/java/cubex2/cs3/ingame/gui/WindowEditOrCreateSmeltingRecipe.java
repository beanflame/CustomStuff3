package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.TextBoxValidators;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.lib.Validators;
import cubex2.cs3.registry.SmeltingRecipeRegistry;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateSmeltingRecipe extends WindowEditOrCreate<SmeltingRecipe> implements IWindowClosedListener
{
    private ItemDisplay inputDisplay;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;
    private TextBox tbRecipes;
    private Button btnSelectList;

    public WindowEditOrCreateSmeltingRecipe(BaseContentPack pack)
    {
        super("New Smelting Recipe", 180, 100, pack);
    }

    public WindowEditOrCreateSmeltingRecipe(SmeltingRecipe recipe, BaseContentPack pack)
    {
        super("Edit Smelting Recipe", 180, 100, recipe, pack);
    }

    @Override
    protected void initControls()
    {
        inputDisplay = itemDisplay().at(55, 20).add();
        inputDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_SMELTING_INPUT);
        inputDisplay.setDrawSlotBackground();
        inputDisplay.useSelectItemDialog(false);

        resultDisplay = itemDisplay().at(55 + 30 + 22, 20).add();
        resultDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        resultDisplay.setDrawSlotBackground();
        resultDisplay.useSelectItemDialog(false);

        label("Recipe List:").left(7).top(inputDisplay, 8).add();


        if (editingContent != null)
        {
            inputDisplay.setItemStack(editingContent.input);
            resultDisplay.setItemStack(editingContent.result);

            tbRecipes = textBox().below(lastControl).fillWidth(7).add();
            tbRecipes.setText(editingContent.recipeList);
        } else
        {
            tbRecipes = textBox().below(lastControl).left(7).right(70).add();
            tbRecipes.setText("vanilla");

            btnSelectList = button("Select").rightTo(tbRecipes).add();
        }

        tbRecipes.setValidityProvider(TextBoxValidators.NOT_EMPTY);
        tbRecipes.setEnabled(editingContent == null);

        pbArrow = pictureBox(Textures.CONTROLS, 218, 18).at(55 + 18 + 4, 20).size(22, 15).add();

        updateValidation();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnSelectList)
        {
            String[] recipeLists = ((SmeltingRecipeRegistry) pack.getContentRegistry(SmeltingRecipe.class)).getRecipeLists();
            GuiBase.openWindow(new WindowSelectString("Select Recipe List", Lists.newArrayList(recipeLists)));
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    protected SmeltingRecipe createContent()
    {
        ItemStack input = inputDisplay.getItemStack();
        ItemStack result = resultDisplay.getItemStack();

        return new SmeltingRecipe(tbRecipes.getText(), input, result, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.input = inputDisplay.getItemStack();
        editingContent.result = resultDisplay.getItemStack();
    }

    @Override
    public void windowClosed(Window window)
    {
        if (window instanceof WindowSelectString)
        {
            WindowSelectString wdw = (WindowSelectString) window;

            if (wdw.getSelectedElement() != null)
                tbRecipes.setText(wdw.getSelectedElement());
        }
    }
}
