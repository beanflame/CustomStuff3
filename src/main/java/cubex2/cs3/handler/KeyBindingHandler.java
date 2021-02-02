package cubex2.cs3.handler;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cubex2.cs3.Config;
import cubex2.cs3.ingame.gui.GuiBase;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindingHandler
{
    public static final KeyBinding openGuiKey = new KeyBinding("CS3", Keyboard.KEY_C, "General");

    public static final KeyBindingHandler INSTANCE = new KeyBindingHandler();

    private KeyBindingHandler()
    {

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (Config.GUI_ENABLED && event.phase == TickEvent.Phase.END && event.side == Side.CLIENT && openGuiKey.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClient().isSingleplayer())
        {
            Keyboard.enableRepeatEvents(true);
            FMLClientHandler.instance().showGuiScreen(GuiBase.INSTANCE);
        }
    }
}
