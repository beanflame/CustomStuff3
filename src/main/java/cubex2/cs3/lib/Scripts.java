package cubex2.cs3.lib;

import com.google.common.collect.Maps;
import cubex2.cs3.api.scripting.TriggerType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Scripts
{
    public static final Map<TriggerType, Map<String, ScriptInfo>> infos = Maps.newHashMap();

    public static ScriptInfo getInfo(String scriptName, TriggerType type)
    {
        return infos.get(type).get(scriptName);
    }

    private static ScriptInfo addBlock(String name, String... objects)
    {
        return add(TriggerType.BLOCK, name, objects);
    }

    private static ScriptInfo addItem(String name, String... objects)
    {
        return add(TriggerType.ITEM, name, objects);
    }

    private static ScriptInfo addGui(String name, String... objects)
    {
        return add(TriggerType.GUI, name, objects);
    }

    private static ScriptInfo add(TriggerType type, String name, String[] objects)
    {
        if (!infos.containsKey(type))
            infos.put(type, new HashMap<String, ScriptInfo>());

        Arrays.sort(objects);

        ScriptInfo info = new ScriptInfo(objects);
        infos.get(type).put(name, info);
        return info;
    }

    public static final ScriptInfo BLOCK_ON_ADDED = addBlock("onAdded", "world", "position");
    public static final ScriptInfo BLOCK_ON_ACTIVATED = addBlock("onActivated", "world", "position", "player", "hitX", "hitY", "side");
    public static final ScriptInfo BLOCK_ON_BONEMEAL = addBlock("onBonemeal", "world", "position", "player");
    public static final ScriptInfo BLOCK_ON_UPDATE = addBlock("onUpdate", "world", "position");
    public static final ScriptInfo BLOCK_ON_DESTROYED_BY_PLAYER = addBlock("onDestroyedByPlayer", "world", "position", "player");
    public static final ScriptInfo BLOCK_ON_NEIGHBOR_CHANGE = addBlock("onNeighborChange", "world", "position");
    public static final ScriptInfo BLOCK_ON_REDSTONE_SIGNAL = addBlock("onRedstoneSignal", "world", "position");
    public static final ScriptInfo BLOCK_ON_BREAK = addBlock("onBreak", "world", "position");
    public static final ScriptInfo BLOCK_ON_WALKING = addBlock("onWalking", "world", "position", "entity");
    public static final ScriptInfo BLOCK_ON_PLACED = addBlock("onPlaced", "world", "position");
    public static final ScriptInfo BLOCK_ON_CLICKED = addBlock("onClicked", "world", "position", "player");
    public static final ScriptInfo BLOCK_ON_COLLIDED = addBlock("onCollided", "world", "position", "entity");
    public static final ScriptInfo BLOCK_ON_PLACED_BY_PLAYER = addBlock("onPlacedByPlayer", "world", "position", "player");
    public static final ScriptInfo BLOCK_ON_PLACED_BY = addBlock("onPlacedBy", "world", "position", "living");
    public static final ScriptInfo BLOCK_ON_FALLEN_UPON = addBlock("onFallenUpon", "world", "position", "entity");
    public static final ScriptInfo BLOCK_ON_RANDOM_DISPLAY_TICK = addBlock("onRandomDisplayTick", "world", "position");

    public static final ScriptInfo ITEM_ON_USE = addItem("onUse", "world", "player", "itemstack", "position", "hitX", "hitY", "side");
    public static final ScriptInfo ITEM_ON_EATEN = addItem("onEaten", "world", "player", "itemstack");
    public static final ScriptInfo ITEM_ON_RIGHT_CLICK = addItem("onRightClick", "world", "player", "itemstack");
    public static final ScriptInfo ITEM_ON_USE_FIRST = addItem("onUseFirst", "world", "player", "itemstack", "position", "hitX", "hitY", "side");
    public static final ScriptInfo ITEM_ON_USE_ON_PLAYER = addItem("onUseOnPlayer", "world", "player", "itemstack", "interactPlayer");
    public static final ScriptInfo ITEM_ON_USE_ON_ENTITY = addItem("onUseOnEntity", "world", "player", "itemstack", "living");
    public static final ScriptInfo ITEM_ON_BLOCK_DESTROYED = addItem("onBlockDestroyed", "world", "player", "itemstack", "position", "blockName");
    public static final ScriptInfo ITEM_ON_HIT_ENTITY = addItem("onHitEntity", "world", "player", "itemstack", "living");
    public static final ScriptInfo ITEM_ON_LEFT_CLICK_LIVING = addItem("onLeftClickLiving", "world", "player", "itemstack", "living");
    public static final ScriptInfo ITEM_ON_LEFT_CLICK_PLAYER = addItem("onLeftClickPlayer", "world", "player", "itemstack", "interactPlayer");
    public static final ScriptInfo ITEM_ON_UPDATE = addItem("onUpdate", "world", "player", "itemstack", "slotId", "isCurrentItem");
    public static final ScriptInfo ITEM_ON_CREATED = addItem("onCreated", "world", "player", "itemstack");
    public static final ScriptInfo ITEM_ON_USING = addItem("onUsing", "world", "player", "itemstack", "tickCount");
    public static final ScriptInfo ITEM_ON_STOPPED_USING = addItem("onStoppedUsing", "world", "player", "itemstack", "tickCount");
    public static final ScriptInfo ITEM_ON_DROPPED_BY_PLAYER = addItem("onDroppedByPlayer", "world", "player", "itemstack");
    public static final ScriptInfo ITEM_ON_BLOCK_START_BREAK = addItem("onBlockStartBreak", "world", "player", "itemstack", "position");
    public static final ScriptInfo ITEM_ON_ARMOR_UPDATE = addItem("onArmorUpdate", "world", "player", "itemstack");

    public static final ScriptInfo GUI_BUTTON_ON_CLICKED = addGui("button/onClicked", "player");
}
