package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.common.WindowEditFloat;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.common.WindowEditScript;
import cubex2.cs3.ingame.gui.item.*;
import cubex2.cs3.util.IconWrapper;
import cubex2.cs3.util.ScriptWrapper;
import cubex2.cs3.util.ToolClass;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class ItemAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditDisplayName.class)
    public String displayName = "Unnamed";

    @Attribute(windowClass = WindowEditInformation.class)
    public String information = null;

    @Attribute(windowClass = WindowEditIcon.class)
    public IconWrapper icon = new IconWrapper("");

    @Attribute(windowClass = WindowEditMaxStack.class)
    public int maxStack = 64;

    @Attribute(windowClass = WindowEditInteger.class)
    public int maxUsingDuration = 0;

    @Attribute(windowClass = WindowEditInteger.class)
    public int damage = 0;

    @Attribute(windowClass = WindowEditInteger.class)
    public int maxDamage = 0;

    @Attribute(windowClass = WindowEditToolClass.class)
    public ToolClass[] toolClasses = new ToolClass[0];

    /*// TODO create window
    public ItemStack[] effectiveBlocks = new ItemStack[0];

    // TODO create window
    public Block[] harvestBlocks = new Block[0];*/

    @Attribute(windowClass = WindowEditInteger.class)
    public int enchantability = 0;

    @Attribute(windowClass = WindowEditFloat.class)
    public float efficiency = 1.0f;

    @Attribute(windowClass = WindowEditFull3D.class)
    public boolean full3d = false;

    @Attribute(windowClass = WindowEditHasEffect.class)
    public boolean hasEffect = false;

    @Attribute(windowClass = WindowEditCreativeTab.class, loadOnPostInit = true)
    public CreativeTabs creativeTab = CreativeTabs.tabAllSearch;

    @Attribute(windowClass = WindowEditUsingAction.class)
    public EnumAction usingAction = EnumAction.none;

    @Attribute(windowClass = WindowEditRenderScale.class)
    public float renderScale = 1.0f;

    @Attribute(windowClass = WindowEditContainerItem.class)
    public ItemStack containerItem = null;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean leaveContainerItem = false;

    @Attribute(windowClass = WindowEditAnvilMaterial.class)
    public ItemStack anvilMaterial = null;

    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onRightClick = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUse = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUseFirst = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUseOnEntity = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUseOnPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBlockDestroyed = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onHitEntity = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onLeftClickLiving = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onLeftClickPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUpdate = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onCreated = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUsing = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onStoppedUsing = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onDroppedByPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBlockStartBreak = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onEaten = null;

    public ItemAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
