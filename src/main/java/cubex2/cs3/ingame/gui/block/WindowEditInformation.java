package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.TextField;
import net.minecraft.item.ItemStack;

public class WindowEditInformation extends WindowEditBlockAttribute
{
    private TextField textField;
    private ItemDisplay display;

    public WindowEditInformation(WrappedBlock block)
    {
        super(block, "information", EDIT | BACK, 150, 170);

        textField = textField().top(7).fillWidth(7).height(100).add();
        textField.disableSyntaxHighlighting();
        textField.setText(container.information);
        display = itemDisplay(new ItemStack(wrappedBlock.block)).below(textField).add();
        display.setDrawSlotBackground();
    }

    @Override
    protected void applyChanges()
    {
        container.information = getNewInfo();
    }

    private String getNewInfo()
    {
        String info = textField.getText();
        if (info.length() == 0)
            info = null;
        return info;
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        String currentInfo = container.information;
        container.information = getNewInfo();
        super.drawForeground(mouseX, mouseY);
        container.information = currentInfo;
    }
}
