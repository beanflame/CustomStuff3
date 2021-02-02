package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;

public class PlayerDisplay extends Control
{
    private ItemStack equippedStack;
    private IPlayerDisplayPlayerModifier playerModifier;

    public PlayerDisplay(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
    }

    public void setPlayerModifier(IPlayerDisplayPlayerModifier value)
    {
        playerModifier = value;
    }

    public void setEquippedStack(ItemStack stack)
    {
        equippedStack = stack;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        GuiHelper.drawRect(getBounds(), Color.BLACK);

        int x = getX() + getWidth() / 2;
        int y = getY() + getHeight() / 2 + 30;

        ItemStack curStack = mc.thePlayer.getCurrentEquippedItem();

        mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem] = equippedStack;
        if (playerModifier != null) playerModifier.preRender(this);
        GuiInventory.func_147046_a(x, y, 30, x - mouseX, y - 45 - mouseY, mc.thePlayer);
        if (playerModifier != null) playerModifier.postRender(this);
        mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem] = curStack;
    }
}
