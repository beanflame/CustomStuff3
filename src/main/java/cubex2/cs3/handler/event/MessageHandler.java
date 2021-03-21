package cubex2.cs3.handler.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.ServerChatEvent;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageHandler {

//    public static ArrayDeque<String> messageQueue = new ArrayDeque<String>(16);
//
//    @SubscribeEvent
//    public void onNewMessage(ServerChatEvent event) {
//        messageQueue.add(event.username + "::" + event.message);
//    }
//
//    public String getPlayerMassage(String name) {
//        Iterator<String> iterator = messageQueue.iterator();
//        while (iterator.hasNext()) {
//            return iterator.next().contains(name) ? iterator.next() : null;
//        }
//        return null;
//    }
    public static Map<String, String> messageMap = new HashMap<String, String>();

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        messageMap.put(event.player.getDisplayName(), "");
    }

    @SubscribeEvent
    public void onNewMessage(ServerChatEvent event) {
        messageMap.put(event.username, event.message);
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerEvent.PlayerLoggedOutEvent event) {
        messageMap.remove(event.player.getDisplayName());
    }
}
