package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.util.GuiHelper;

public class PlayerInventoryArea extends Control
{
    public PlayerInventoryArea(Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(9 * 18, 76, anchor, offsetX, offsetY, parent);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (rootControl.drawSlots)
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    int x = getX() + 1 + j * 18;
                    int y = getY() + 1 + i * 18;
                    GuiHelper.drawRect(x, y, x + 16, y + 16, 0x770000dd);
                }
            }

            for (int i = 0; i < 9; i++)
            {
                int x = getX() + 1 + i * 18;
                int y = getY() + getHeight() - 17;
                GuiHelper.drawRect(x, y, x + 16, y + 16, 0x770000dd);
            }
        }
    }
}
