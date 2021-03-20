package cubex2.cs3.handler.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;

import java.util.ArrayDeque;

public class MessageHandler {

    public static ArrayDeque<String> messageQueue = new ArrayDeque<String>(16);

    @SubscribeEvent
    public void onNewMessage(ServerChatEvent event) {
        messageQueue.add(event.username + "::" + event.message);
    }
}
