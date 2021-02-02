package cubex2.cs3;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config
{
    public static boolean GUI_ENABLED = true;

    public static void init(FMLPreInitializationEvent event)
    {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        try
        {
            cfg.load();
            Property prop = cfg.get(Configuration.CATEGORY_GENERAL, "disableGui", false, "If set to true, you won't be able to open the gui to create stuff.\n" +
                    "The key will still show up ingame but it won't do anything.");
            GUI_ENABLED = !prop.getBoolean(false);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            cfg.save();
        }
    }
}
