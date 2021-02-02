package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import cubex2.cs3.util.MathUtil;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;

public class WorldDisplay extends Control
{
    private RenderBlocks renderer = new RenderBlocks();

    private float rotation = 0.0f;

    public boolean rotate = true;
    public boolean canMoveAround = false;

    public float camX = 0.0f;
    public float camY = 5.0f;
    public float camZ = 2.0f;

    public float lookX = 0.0f;
    public float lookY = 0.0f;
    public float lookZ = 0.0f;

    public IBlockDisplayRenderer renderProvider;

    protected SimulatedWorld world;

    public WorldDisplay(SimulatedWorld world, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        setWorld(world);
    }

    public void setCam(float x, float y, float z)
    {
        camX = x;
        camY = y;
        camZ = z;
    }

    public void setLook(float x, float y, float z)
    {
        lookX = x;
        lookY = y;
        lookZ = z;
    }

    public void setWorld(SimulatedWorld world)
    {
        this.world = world;
        renderer.blockAccess = world;
        renderer.setRenderFromInside(false);
        renderer.setRenderAllFaces(false);
        renderer.flipTexture = false;
    }

    @Override
    public void keyTyped(char c, int key)
    {
        if (!canMoveAround) return;

        float[] up = new float[]{0, 1, 0};
        float[] forward = new float[]{lookX - camX, lookY - camY, lookZ - camZ};
        MathUtil.normalize(forward);

        float[] side = new float[3];
        MathUtil.cross(forward, up, side);
        MathUtil.normalize(side);

        if (key == Keyboard.KEY_UP || key == Keyboard.KEY_DOWN)
        {
            MathUtil.scale(forward, 0.1f);
            int dir = key == Keyboard.KEY_UP ? 1 : -1;
            lookX += forward[0] * dir;
            lookY += forward[1] * dir;
            lookZ += forward[2] * dir;
            camX += forward[0] * dir;
            camY += forward[1] * dir;
            camZ += forward[2] * dir;
        } else if (key == Keyboard.KEY_RIGHT || key == Keyboard.KEY_LEFT)
        {
            MathUtil.scale(side, 0.1f);
            int dir = key == Keyboard.KEY_RIGHT ? 1 : -1;
            lookX += side[0] * dir;
            lookY += side[1] * dir;
            lookZ += side[2] * dir;
            camX += side[0] * dir;
            camY += side[1] * dir;
            camZ += side[2] * dir;
        } else if (key == Keyboard.KEY_D || key == Keyboard.KEY_A)
        {
            MathUtil.rotateY(forward, key == Keyboard.KEY_A ? 2.0f : -2.0f);
            lookX = camX + forward[0];
            lookY = camY + forward[1];
            lookZ = camZ + forward[2];
        } else if (key == Keyboard.KEY_W || key == Keyboard.KEY_S)
        {
            MathUtil.rotateLine(forward, key == Keyboard.KEY_S ? -2.0f : 2.0f, side[0], side[1], side[2]);
            lookX = camX + forward[0];
            lookY = camY + forward[1];
            lookZ = camZ + forward[2];
        } else if (key == Keyboard.KEY_NEXT || key == Keyboard.KEY_PRIOR)
        {
            int dir = key == Keyboard.KEY_NEXT ? -1 : 1;
            lookY += 0.1f * dir;
            camY += 0.1f * dir;
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        GuiHelper.drawRect(getBounds(), Color.BLACK);

        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport(getX() * res.getScaleFactor(), mc.displayHeight - getY() * res.getScaleFactor() - getHeight() * res.getScaleFactor(), getWidth() * res.getScaleFactor(), getHeight() * res.getScaleFactor());

        Project.gluPerspective(70.0F, (float) getWidth() / getHeight(), 0.1F, 2000.0F);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        Project.gluLookAt(camX, camY, camZ, lookX, lookY, lookZ, 0, 1, 0);
        RenderHelper.enableStandardItemLighting();

        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);

        renderBlocks();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate()
    {
        if (rotate)
        {
            rotation += 1.5f;
        }
    }

    private void renderBlocks()
    {
        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        renderer.renderBlockAsItem(Blocks.air, 0, 1.0f);
        GL11.glPopMatrix();

        if (renderProvider != null)
        {
            renderProvider.renderBlocks(renderer);
            return;
        }

        if (world != null)
        {

            Tessellator.instance.startDrawingQuads();
            RenderHelper.enableGUIStandardItemLighting();

            for (int x = world.minX; x <= world.maxX; x++)
            {
                for (int y = world.minY; y <= world.maxY; y++)
                {
                    for (int z = world.minZ; z <= world.maxZ; z++)
                    {
                        Block block = world.getBlock(x, y, z);
                        if (block.getMaterial() != Material.air)
                        {
                            renderer.renderBlockByRenderType(block, x, y, z);
                        }
                    }
                }
            }
            Tessellator.instance.draw();

        }
    }
}
