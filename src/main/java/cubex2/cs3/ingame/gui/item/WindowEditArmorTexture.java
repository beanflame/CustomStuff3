package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.IPlayerDisplayPlayerModifier;
import cubex2.cs3.ingame.gui.control.PlayerDisplay;
import cubex2.cs3.ingame.gui.control.TextureTextBox;
import cubex2.cs3.item.ItemCSArmor;
import cubex2.cs3.item.attributes.ArmorAttributes;
import net.minecraft.item.ItemStack;

public class WindowEditArmorTexture extends WindowEditItemAttribute implements IPlayerDisplayPlayerModifier
{
    private TextureTextBox textBox;
    private PlayerDisplay display;
    private ItemCSArmor item;
    private ArmorAttributes container;

    private ItemStack[] armorStacks = new ItemStack[4];
    private String prevTexture;

    public WindowEditArmorTexture(WrappedItem item)
    {
        super(item, "texture", EDIT | CANCEL, 150, 180);
        this.item = (ItemCSArmor) item.item;
        container = (ArmorAttributes) item.container;
        prevTexture = container.texture;

        textBox = textureTextBox(item.getPack(), "models/armor").at(7, 7).fillWidth(7).add();
        textBox.setMaxLength(256);
        textBox.setText(container.texture);

        display = playerDisplay().below(textBox).size(50, 80).add();
        display.setPlayerModifier(this);
    }

    @Override
    protected void applyChanges()
    {
        container.texture = textBox.getText();
    }

    @Override
    public void preRender(PlayerDisplay display)
    {
        for (int i = 0; i < 4; i++)
        {
            armorStacks[i] = mc.thePlayer.inventory.armorInventory[i];
            mc.thePlayer.inventory.armorInventory[i] = item.armorType == i ? new ItemStack(item) : null;
        }
        container.texture = textBox.getText();
    }

    @Override
    public void postRender(PlayerDisplay display)
    {
        for (int i = 0; i < 4; i++)
        {
            mc.thePlayer.inventory.armorInventory[i] = armorStacks[i];
        }
        container.texture = prevTexture;
    }
}
