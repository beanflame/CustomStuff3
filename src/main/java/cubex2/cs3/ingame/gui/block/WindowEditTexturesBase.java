package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.ingame.gui.control.builder.LabelBuilder;
import cubex2.cs3.util.ClientHelper;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class WindowEditTexturesBase extends WindowEditBlockAttribute
{
    protected static final String[] DEFAULT_TEXTURES = new String[]{"bottom", "top", "north", "south", "east", "west"};

    private String[] textures;

    private Label[] labels;
    private IconTextBox[] textBoxes;

    private CheckBox cbTransparent;
    private CheckBox cbSemiTransparent;
    private CheckBox cbRotatable;
    private CheckBox cbTileTransparent;
    protected CheckBox lastCheckBox;

    protected WorldDisplay worldDisplay;
    protected SimulatedWorld world;

    public WindowEditTexturesBase(WrappedBlock block, String[] textures)
    {
        this(block, textures, false, true, false, true);
    }

    public WindowEditTexturesBase(WrappedBlock block, String[] textures, boolean transparent, boolean semiTransparent, boolean tileTransparent)
    {
        this(block, textures, transparent, semiTransparent, tileTransparent, false);
    }

    public WindowEditTexturesBase(WrappedBlock block, String[] textures, boolean transparent, boolean semiTransparent, boolean tileTransparent, boolean singleCol)
    {
        super(block, "textures", (singleCol ? 0 : 150) + 153, 200);
        this.textures = textures;

        world = new SimulatedWorld(-1, -1, -2, 2, 0, 1);
        world.setBlock(wrappedBlock.block, 0, 0, 0);
        for (int i = -1; i < 3; i++)
        {
            for (int j = -2; j < 2; j++)
            {
                world.setBlock(Blocks.stone, i, -1, j);
            }
        }

        labels = new Label[textures.length];
        textBoxes = new IconTextBox[textures.length];

        for (int i = 0; i < labels.length; i++)
        {
            LabelBuilder lb = label(textures[i]);
            if (i == 0) lb = (LabelBuilder) lb.at(7, 7);
            else if (!singleCol && i == 1) lb = (LabelBuilder) lb.at(7 + 153, 7);
            else lb = (LabelBuilder) lb.below(textBoxes[i - (singleCol ? 1 : 2)]);
            labels[i] = lb.add();

            textBoxes[i] = iconTextBox(block.getPack(), "blocks").below(labels[i]).height(13).width(150 - 14 - 18).add();
            textBoxes[i].setMaxLength(256);

            String textureString = container.getTexture(textures[i]).getTextForGui(wrappedBlock.getPack());
            textBoxes[i].setText(textureString);
        }

        Control below = singleCol ? textBoxes[textBoxes.length - 1] : textBoxes[4];
        int belowDist = 9;

        Control below2 = singleCol ? textBoxes[textBoxes.length - 1] : textBoxes[4];
        int belowDist2 = 20;


        if (transparent)
        {
            cbTransparent = checkBox("Transparent").below(below, belowDist).add();
            cbTransparent.setIsChecked(container.transparent);

            below = cbTransparent;
            belowDist = 7;
            lastCheckBox = cbTransparent;
        }

        if (semiTransparent)
        {
            cbSemiTransparent = checkBox("Semi Transparent").below(below, belowDist).add();
            cbRotatable = checkBox("Rotatable").below(below2, belowDist2).add();
            cbSemiTransparent.setIsChecked(container.semiTransparent);
            cbRotatable.setIsChecked(true);

            below = cbSemiTransparent;
            belowDist = 7;

            below2 = cbRotatable;
            belowDist2 = 9;
            lastCheckBox = cbSemiTransparent;
        }

        if (tileTransparent)
        {
            cbTileTransparent = checkBox("Tile Transparent").below(below, belowDist).add();
            cbTileTransparent.setIsChecked(container.tileTransparent);
            lastCheckBox = cbTileTransparent;
        }

        if (singleCol)
            worldDisplay = worldDisplay(world).fillWidth(7).bottom(btnCancel, 3).height(153 - 14 - 50).add();
        else
            worldDisplay = worldDisplay(world).right(7).bottom(btnCancel, 3).size(75, 75).add();
        worldDisplay.rotate = false;
        if (singleCol)
        {
            worldDisplay.setCam(-0.25f, 1.5f, 0.5f);
            worldDisplay.setLook(0.5f, 0.5f, 0.5f);
        } else
        {
            worldDisplay.camY = 2.0f;
            worldDisplay.camX = 0.5f;
            worldDisplay.lookX = 1.0f;
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (cbTileTransparent != null && cbTransparent != null && cbTileTransparent.getIsChecked() && !cbTransparent.getIsChecked())
        {
            cbTransparent.setIsChecked(true);
        }

        handleDefaultButtonClick(c);
    }

    @Override
    protected void applyChanges()
    {
        for (int i = 0; i < textBoxes.length; i++)
        {
            String text = textBoxes[i].getText().trim();

            String modId = text.contains(":") ? text.split(":")[0] : wrappedBlock.getPack().id.toLowerCase();
            String textureName = text.contains(":") && text.indexOf(':') != text.length() - 1 ? text.split(":")[1] : text;

            if (textureName.length() > 0)
                container.getTexture(textures[i]).iconString = modId + ":" + textureName;
        }

        if (cbTransparent != null)
            container.transparent = cbTransparent.getIsChecked();
        if (cbSemiTransparent != null)
            container.semiTransparent = cbSemiTransparent.getIsChecked();
        if (cbTileTransparent != null)
            container.tileTransparent = cbTileTransparent.getIsChecked();

        ClientHelper.refreshResources(mc);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        boolean prevTile = container.tileTransparent;
        boolean prevTrans = container.transparent;
        boolean prevSemi = container.semiTransparent;
        if (cbTileTransparent != null)
            container.tileTransparent = cbTileTransparent.getIsChecked();
        if (cbTransparent != null)
            container.transparent = cbTransparent.getIsChecked();
        if (cbSemiTransparent != null)
            container.semiTransparent = cbSemiTransparent.getIsChecked();
        preDraw();
        super.draw(mouseX, mouseY, renderTick);
        postDraw();
        container.tileTransparent = prevTile;
        container.transparent = prevTrans;
        container.semiTransparent = prevSemi;

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        for (int i = 0; i < textBoxes.length; i++)
        {
            if (textBoxes[i] == null || textBoxes[i].getText() == null || textBoxes[i].getText().length() == 0)
                continue;

            int posX = textBoxes[i].getX() + textBoxes[i].getWidth() + 3;
            int posY = textBoxes[i].getY() - 1;

            if (textBoxes[i].getLocation() != null)
            {
                mc.renderEngine.bindTexture(textBoxes[i].getLocation());
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glScalef(1 / 16.0F, 1 / 16.0F, 1.0F);
                drawTexturedModalRect(posX * 16, posY * 16, 0, 0, 256, 256);
                GL11.glScalef(16.0F, 16.0F, 1.0F);
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    protected void preDraw()
    {

    }

    protected void postDraw()
    {

    }


}
