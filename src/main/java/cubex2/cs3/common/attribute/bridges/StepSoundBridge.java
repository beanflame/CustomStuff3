package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.lib.StepSounds;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class StepSoundBridge extends AttributeBridge<Block.SoundType>
{
    @Override
    public Block.SoundType loadValueFromNBT(NBTTagCompound compound)
    {
        return StepSounds.getStepSound(compound.getString("Value"));
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, Block.SoundType value)
    {
        compound.setString("Value", StepSounds.getStepSoundName(value));
    }
}
