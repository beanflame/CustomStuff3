package cubex2.cs3.util;

import cubex2.cs3.common.BaseContentPack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IOHelper
{
    public static NBTTagCompound readNBTFromPath(String path, BaseContentPack pack)
    {
        if (pack.isZipped())
        {
            return readNBTFromZip(pack.getDirectory(), path);
        } else
        {
            return readNBTFromFile(pack.getDirectory(), path);
        }
    }

    public static NBTTagCompound readNBTFromFile(File modDir, String path)
    {
        try
        {
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(new File(modDir, path)));
            return nbt;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static NBTTagCompound readNBTFromZip(File zipFile, String entryName)
    {
        ZipFile zip = null;
        try
        {
            zip = new ZipFile(zipFile);
            ZipEntry entry = zip.getEntry(entryName);
            if (entry != null)
            {
                NBTTagCompound nbt;
                InputStream stream = zip.getInputStream(entry);
                nbt = CompressedStreamTools.readCompressed(stream);
                return nbt;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (zip != null) zip.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void writeNBTToFile(NBTTagCompound nbt, File file)
    {
        try
        {
            CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to save data.");
        }
    }
}
