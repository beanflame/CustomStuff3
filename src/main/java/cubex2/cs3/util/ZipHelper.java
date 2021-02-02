package cubex2.cs3.util;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

public class ZipHelper
{
    public static String readEntryContent(File zipFile, String entryName) throws IOException
    {
        FileInputStream fin = new FileInputStream(zipFile);
        ZipInputStream zin = new ZipInputStream(fin);
        ZipEntry ze;
        String ret = null;
        while ((ze = zin.getNextEntry()) != null)
        {
            if (ze.getName().equals(entryName))
            {
                StringWriter sw = new StringWriter();
                for (int c = zin.read(); c != -1; c = zin.read())
                {
                    sw.write(c);
                }
                ret = sw.getBuffer().toString();
                zin.closeEntry();
                sw.close();
                break;
            }
            zin.closeEntry();
        }
        zin.close();
        fin.close();
        return ret == null ? "" : ret;
    }

    public static List<String> listEntries(File zipFile) throws IOException
    {
        FileInputStream fin = new FileInputStream(zipFile);
        ZipInputStream zin = new ZipInputStream(fin);
        ZipEntry ze;
        List<String> entries = Lists.newArrayList();
        while ((ze = zin.getNextEntry()) != null)
        {
            entries.add(ze.getName());
            zin.closeEntry();
        }
        zin.close();
        fin.close();
        return entries;
    }

}
