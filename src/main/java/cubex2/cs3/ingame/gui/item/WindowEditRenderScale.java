package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IPlayerDisplayPlayerModifier;
import cubex2.cs3.ingame.gui.control.PlayerDisplay;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;
import net.minecraft.item.ItemStack;

public class WindowEditRenderScale extends WindowEditItemAttribute implements IPlayerDisplayPlayerModifier
{
    private PlayerDisplay display;
    private TextBox tbScale;

    private float oldScale;
    private float newScale;

    public WindowEditRenderScale(WrappedItem item)
    {
        super(item, "renderScale", EDIT | CANCEL, 150, 120);
        oldScale = container.renderScale;
        newScale = container.renderScale;

        display = playerDisplay().at(7, 7).size(50, 80).add();
        display.setPlayerModifier(this);
        display.setEquippedStack(new ItemStack(wrappedItem.item));

        tbScale = textBox().rightTo(display).width(150 - 14 - 50).add();
        tbScale.setText(String.valueOf(newScale));
        tbScale.setValidityProvider(TextBoxValidators.FLOAT);
    }

    @Override
    public void keyTyped(char c, int key)
    {
        super.keyTyped(c, key);

        if (tbScale.hasValidValue())
        {
            newScale = Float.parseFloat(tbScale.getText());
        }
    }

    @Override
    protected void applyChanges()
    {
        container.renderScale = newScale;
    }

    @Override
    public void preRender(PlayerDisplay display)
    {
        container.renderScale = newScale;
    }

    @Override
    public void postRender(PlayerDisplay display)
    {
        container.renderScale = oldScale;
    }
}
