package cubex2.cs3.gui.data;

import com.google.common.collect.Lists;
import cubex2.cs3.util.MyFunction;
import cubex2.cs3.util.NBTData;
import cubex2.cs3.util.Util;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class GuiData implements NBTData
{
    public List<ControlData> controls = Lists.newArrayList();

    public void add(ControlData data)
    {
        controls.add(data);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        Util.writeListToNBT("Controls", controls, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        Util.readListFromNBT("Controls", controls, READER, compound);
    }

    public int getSlotIndex(SlotData data)
    {
        int index = -1;
        for (ControlData cd : controls)
        {
            if (cd instanceof SlotData)
                index++;
            if (cd == data)
                break;
        }

        return index;
    }

    public int numSlots()
    {
        int num = 0;
        for (ControlData c : controls)
        {
            if (c instanceof SlotData)
            {
                num++;
            }
        }
        return num;
    }

    private static final MyFunction<NBTTagCompound, ControlData> READER = new MyFunction<NBTTagCompound, ControlData>()
    {
        @Override
        public ControlData apply(NBTTagCompound compound)
        {
            String type = compound.getString("ControlType");
            ControlData data = null;
            if (type.equals("button"))
            {
                data = new ButtonData();
            } else if (type.equals("label"))
            {
                data = new LabelData();
            } else if (type.equals("playerInventory"))
            {
                data = new PlayerInventoryData();
            } else if (type.equals("invSlot"))
            {
                data = new SlotData();
            } else if (type.equals("cookProgress"))
            {
                data = new CookProgressData();
            } else if (type.equals("fuelProgress"))
            {
                data = new FuelProgressData();
            }
            data.readFromNBT(compound);
            return data;
        }
    };
}
