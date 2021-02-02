package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.ISelectElementCallback;
import cubex2.cs3.ingame.gui.common.WindowSelectTexture;
import cubex2.cs3.util.ClientHelper;
import net.minecraft.util.ResourceLocation;

public class TextureTextBox extends ControlContainer implements ISelectElementCallback<String>
{
    private final BaseContentPack pack;
    private final String subFolder;
    private TextBox tb;
    private Button btnDialog;

    private ResourceLocation location = null;
    private String prevText = null;

    public TextureTextBox(BaseContentPack pack, String subFolder, int width, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, 14, anchor, offsetX, offsetY, parent);
        this.pack = pack;
        this.subFolder = subFolder;

        tb = textBox().height(-1).left(0).top(0).bottom(0).right(0).add();

        btnDialog = button("...").top(1).right(1).bottom(1).width(18).height(-1).add();
    }

    public void setText(String text)
    {
        tb.setText(text);
    }

    public String getText()
    {
        return tb.getText();
    }

    public void setMaxLength(int length)
    {
        tb.setMaxLength(length);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnDialog)
        {
            GuiBase.openWindow(new WindowSelectTexture(ClientHelper.getPackIcons(pack, subFolder), this));
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

    @Override
    public void itemSelected(String element)
    {
        tb.setText(element);
    }

    public ResourceLocation getLocation()
    {
        String text = tb.getText().trim();
        if (location == null || prevText == null || !prevText.equals(text))
        {
            String modId = text.contains(":") ? text.split(":")[0] : pack.id.toLowerCase();
            String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;
            if (!textureName.endsWith(".png"))
                textureName += ".png";

            if (textureName.startsWith("textures/" + subFolder + "/"))
                location = new ResourceLocation(modId + ":" + textureName);
            else
                location = new ResourceLocation(modId + ":" + "textures/" + subFolder + "/" + textureName);

            prevText = text;
        }

        return location;
    }
}
