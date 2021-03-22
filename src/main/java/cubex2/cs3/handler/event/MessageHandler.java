package cubex2.cs3.handler.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;

public class MessageHandler {
    public static String latestMessage = "";

    @SubscribeEvent
    public void onNewMessage(ServerChatEvent event) {
        latestMessage = event.message;
    }
}
