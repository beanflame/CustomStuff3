package cubex2.cs3.handler.event;

import com.sun.jmx.remote.internal.ArrayQueue;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;

import java.util.ArrayDeque;
import java.util.Queue;

public class MessageHandler {

    public static ArrayDeque messageQueue = new ArrayDeque(16);

    @SubscribeEvent
    public void onNewMessage(ServerChatEvent event) {
        messageQueue.add(event.username + "::" + event.message);
    }
}
