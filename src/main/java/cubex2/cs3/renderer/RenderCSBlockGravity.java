package cubex2.cs3.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.entity.EntityCSGravityBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderCSBlockGravity extends Render
{
    private final RenderBlocks renderBlocks = new RenderBlocks();

    public RenderCSBlockGravity()
    {
        shadowSize = 0.5F;
    }

    public void doRenderGravityBlock(EntityCSGravityBlock gravityBlock, double x, double y, double z)
    {
        World world = gravityBlock.getWorld();
        Block block = gravityBlock.getBlock();
        int i = MathHelper.floor_double(gravityBlock.posX);
        int j = MathHelper.floor_double(gravityBlock.posY);
        int k = MathHelper.floor_double(gravityBlock.posZ);

        if (block != null && block != world.getBlock(i, j, k))
        {
            //System.out.println(y);
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, (float) z);
            this.bindEntityTexture(gravityBlock);
            GL11.glDisable(GL11.GL_LIGHTING);

            renderBlocks.setRenderBoundsFromBlock(block);
            renderBlocks.renderBlockSandFalling(block, world, MathHelper.floor_double(gravityBlock.posX), MathHelper.floor_double(gravityBlock.posY), MathHelper.floor_double(gravityBlock.posZ), gravityBlock.meta);

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TextureMap.locationBlocksTexture;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float var8, float var9)
    {
        this.doRenderGravityBlock((EntityCSGravityBlock) entity, x, y, z);
    }
}
