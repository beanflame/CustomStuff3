package cubex2.cs3.gui.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public abstract class ProgressData extends ControlData
{
    public String name;
    public ResourceLocation texture;
    public int u;
    public int v;
    public int direction;

    public ProgressData()
    {
    }

    public ProgressData(int x, int y, int width, int height, String name, ResourceLocation texture, int u, int v, int direction)
    {
        super(x, y, width, height);
        this.name = name;
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.direction = direction;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        name = compound.getString("Name");
        texture = new ResourceLocation(compound.getString("Texture"));
        u = compound.getInteger("U");
        v = compound.getInteger("V");
        direction = compound.getByte("Direction");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("Name", name);
        compound.setString("Texture", texture.toString());
        compound.setInteger("U", u);
        compound.setInteger("V", v);
        compound.setByte("Direction", (byte) direction);
    }
}
