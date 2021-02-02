package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.ResourceLocation;

public class ListBoxItemResourceLocation extends ListBoxItem<ResourceLocation>
{
    private final boolean isIcon;

    public ListBoxItemResourceLocation(ResourceLocation value, boolean isIcon, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
        this.isIcon = isIcon;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        super.draw(mouseX, mouseY, renderTick);

        ClientHelper.drawResource(mc, value, getX() + 3, getY() + 3, width - 6, height - 6);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        super.drawForeground(mouseX, mouseY);

        if (isMouseOverControl(mouseX, mouseY))
        {
            String s = isIcon ? ClientHelper.resourceToIconString(value) : value.toString();

            GuiHelper.drawToolTip(new String[]{s}, mouseX, mouseY, mc.fontRenderer);
        }
    }
}
