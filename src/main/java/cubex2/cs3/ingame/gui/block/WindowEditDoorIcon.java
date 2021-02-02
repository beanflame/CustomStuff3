package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.IconTextBox;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Color;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class WindowEditDoorIcon extends WindowEditBlockAttribute
{
    private IconTextBox textBox;

    public WindowEditDoorIcon(WrappedBlock block)
    {
        super(block, "icon", 150, 100);

        Label lblIcon = label("Icon name:").at(7, 7).add();
        infoButton(Strings.INFO_TEXURE_FILE).rightTo(lblIcon).add();
        textBox = iconTextBox(block.getPack(), "items").below(lblIcon).height(16).fillWidth(7).add();
        textBox.setMaxLength(256);

        String iconString = wrappedBlock.container.getTexture("doorIcon").iconString;
        if (iconString.contains(":") && iconString.split(":")[0].equals(wrappedBlock.getPack().id.toLowerCase()))
            iconString = iconString.split(":")[1];
        textBox.setText(iconString);
    }

    @Override
    protected void applyChanges()
    {
        String text = textBox.getText().trim();

        String modId = text.contains(":") ? text.split(":")[0] : wrappedBlock.getPack().id.toLowerCase();
        String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

        wrappedBlock.container.getTexture("doorIcon").iconString = modId + ":" + textureName;

        wrappedBlock.blockItem.setTextureName(modId + ":" + textureName);

        ClientHelper.refreshResources(mc);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        super.draw(mouseX, mouseY, renderTick);

        if (textBox.getLocation() != null)
        {
            mc.renderEngine.bindTexture(textBox.getLocation());
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(1 / 16.0F, 1 / 16.0F, 1.0F);
            drawTexturedModalRect((getX() + 7 + 1) * 16, (getY() + 40 + 1) * 16, 0, 0, 256, 256);
            GL11.glScalef(16.0F, 16.0F, 1.0F);
        }

        if (new Rectangle(getX() + 7, getY() + 40, 18, 18).contains(mouseX, mouseY))
        {
            GuiHelper.drawItemToolTip(new ItemStack(wrappedBlock.blockItem), mouseX, mouseY, mc.fontRenderer);
        }

        GuiHelper.drawBorder(getX() + 7, getY() + 40, getX() + 7 + 18, getY() + 40 + 18, Color.DARK_GREY);
    }
}
