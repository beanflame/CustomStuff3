package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.util.ScriptWrapper;
import net.minecraft.nbt.NBTTagCompound;

public class ScriptWrapperBridge extends AttributeBridge<ScriptWrapper>
{
    @Override
    public ScriptWrapper loadValueFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("Value"))
        {
            return new ScriptWrapper(compound.getString("Value"));
        }

        return null;
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, ScriptWrapper value)
    {
        if (value != null && value.getSource() != null)
        {
            compound.setString("Value", value.getSource());
        }
    }
}
