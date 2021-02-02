package cubex2.cs3.util;

import cubex2.cs3.lib.Strings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class NBTHelper
{
    public static int getCSIntData(NBTTagCompound tag, String name)
    {
        if (tag == null || !tag.hasKey(Strings.INT_DATA_PREFIX + name))
            return -1;
        return tag.getInteger(Strings.INT_DATA_PREFIX + name);
    }

    public static float getCSFloatData(NBTTagCompound tag, String name)
    {
        if (tag == null || !tag.hasKey(Strings.FLOAT_DATA_PREFIX + name))
            return -1.0f;
        return tag.getFloat(Strings.FLOAT_DATA_PREFIX + name);
    }

    public static String getCSStringData(NBTTagCompound tag, String name)
    {
        if (tag == null || !tag.hasKey(Strings.STRING_DATA_PREFIX + name))
            return null;
        return tag.getString(Strings.STRING_DATA_PREFIX + name);
    }

    public static void setCSIntData(NBTTagCompound tag, String name, int value)
    {
        if (tag != null)
        {
            tag.setInteger(Strings.INT_DATA_PREFIX + name, value);
        }
    }

    public static void setCSFloatData(NBTTagCompound tag, String name, float value)
    {
        if (tag != null)
        {
            tag.setFloat(Strings.FLOAT_DATA_PREFIX + name, value);
        }
    }

    public static void setCSStringData(NBTTagCompound tag, String name, String value)
    {
        if (tag != null)
        {
            tag.setString(Strings.STRING_DATA_PREFIX + name, value);
        }
    }

    public static void dumpNBT(NBTTagCompound tag, File file)
    {
        try
        {
            FileWriter w = new FileWriter(file);
            w.write("IMPORTANT:\n");
            w.write("This is just the packs data in a readable format used for debugging stuff.\n");
            w.write("Changes made here have no effect at all. This is intended.\n\n");
            writeObject("", tag, w, 0);
            w.flush();
            w.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void writeObject(String name, Object o, FileWriter w, int depth) throws IOException
    {
        char nl = (char) Character.LINE_SEPARATOR;

        if (o instanceof NBTTagCompound)
        {
            NBTTagCompound compound1 = ((NBTTagCompound) o);
            Set set = ((NBTTagCompound) o).func_150296_c();
            w.write(getIndent(depth) + name + "{" + nl);

            for (Object tag : set)
            {
                String s = (String) tag;
                writeObject(s + ": ", compound1.getTag(s), w, depth + 1);
            }

            w.write(getIndent(depth) + "}" + nl);
        } else if (o instanceof NBTTagList)
        {
            NBTTagList tagList = (NBTTagList) o;
            w.write(getIndent(depth) + name + "{" + nl);

            for (int i = 0; i < tagList.tagCount(); i++)
            {
                writeObject("[" + i + "]: ", tagList.getCompoundTagAt(i), w, depth + 1);
            }

            w.write(getIndent(depth) + "}" + nl);
        } else
        {
            w.write(getIndent(depth) + name + o.toString() + nl);
        }
    }

    private static String getIndent(int depth)
    {
        String res = "";
        for (int i = 0; i < depth; i++) res += "   ";
        return res;
    }

    public static void writeToNBT(ItemStack stack, String name, NBTTagCompound nbt)
    {
        if (stack != null)
        {
            NBTTagCompound compound = new NBTTagCompound();
            ItemStackHelper.writeToNBTNamed(stack, compound);
            nbt.setTag(name, compound);
        }
    }

    public static ItemStack readStackFromNBT(String name, NBTTagCompound nbt)
    {
        if (nbt.hasKey(name))
        {
            return ItemStackHelper.readFromNBTNamed(nbt.getCompoundTag(name));
        }

        return null;
    }

}
