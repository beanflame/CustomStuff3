package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.block.*;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import cubex2.cs3.ingame.gui.common.WindowEditFloat;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.common.WindowEditScript;
import cubex2.cs3.util.BlockDrop;
import cubex2.cs3.util.IconWrapper;
import cubex2.cs3.util.ScriptWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditDisplayName.class)
    public String displayName = "Unnamed";

    @Attribute(windowClass = WindowEditInformation.class)
    public String information = null;

    @Attribute(windowClass = WindowEditCreativeTab.class, loadOnPostInit = true)
    public CreativeTabs creativeTab = CreativeTabs.tabAllSearch;

    // TODO add to creative tab window
    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean addToCreative = true;

    @Attribute(windowClass = WindowEditMaxStack.class)
    public int maxStack = 64;

    @Attribute(windowClass = WindowEditOpacity.class)
    public int opacity = 255;

    @Attribute(windowClass = WindowEditInteger.class, additionalInfo = "0-15")
    public int light = 0;

    @Attribute(windowClass = WindowEditInteger.class)
    public int flammability = 10;

    @Attribute(windowClass = WindowEditInteger.class)
    public int fireSpreadSpeed = 10;

    @Attribute(windowClass = WindowEditInteger.class)
    public int tickrate = 10;

    @Attribute(windowClass = WindowEditDrops.class)
    public BlockDrop drop = null;

    @Attribute(windowClass = WindowEditInteger.class, additionalInfo = "0-1000000")
    public int fortuneModifier = 0;

    @Attribute(windowClass = WindowEditPick.class)
    public ItemStack pick = null;

    @Attribute(windowClass = WindowEditHarvesting.class, customName = "harvesting")
    public String toolClass = null;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public int harvestLevel = 0;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean canSilkHarvest = false;

    @Attribute(windowClass = WindowEditExpDrop.class, customName = "expDrop")
    public int expDropMin = 0;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public int expDropMax = 0;

    @Attribute(windowClass = WindowEditSlipperiness.class)
    public float slipperiness = 0.6f;

    @Attribute(windowClass = WindowEditFloat.class)
    public float resistance = 0.0f;

    @Attribute(windowClass = WindowEditFloat.class)
    public float hardness = 0.0f;

    @Attribute(windowClass = WindowEditContainerItem.class)
    public ItemStack containerItem = null;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean leaveContainerItem = false;

    @Attribute(windowClass = WindowEditMaterial.class)
    public Material material = Material.ground;

    @Attribute(windowClass = WindowEditStepSound.class)
    public Block.SoundType stepSound = Block.soundTypeStone;

    @Attribute(windowClass = WindowEditTextures.class, customName = "textures")
    public IconWrapper textureBottom = new IconWrapper("");

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureTop = new IconWrapper("");

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureNorth = new IconWrapper("");

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureSouth = new IconWrapper("");

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureEast = new IconWrapper("");

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public IconWrapper textureWest = new IconWrapper("");

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Drop more if tool has fortune")
    public boolean useFortune = false;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Block blocks piston")
    public boolean blocksPiston = false;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Block is beacon base")
    public boolean isBeaconBase = false;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Fire keeps burning in top")
    public boolean isFireSource = false;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Fire damage on contact")
    public boolean isBurning = false;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Prevents leaf decaying")
    public boolean isWood = false;

    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Is collidable")
    public boolean hasCollision = true;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean transparent = false;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean semiTransparent = false;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean tileTransparent = false;

    @Attribute(windowClass = WindowEditPlacementRules.class, customName = "placement")
    public boolean canPlaceOnFloor = true;

    @Attribute(windowClass = Window.class, customName = "rotatable")
    public boolean canRotatable = false;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean canPlaceOnCeiling = true;

    @Attribute(windowClass = Window.class, hasOwnWindow = false)
    public boolean canPlaceOnWall = true;

    @Attribute(windowClass = WindowEditTileEntity.class)
    public WrappedTileEntity tileEntity = null;

    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onUpdate = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onDestroyedByPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onNeighborChange = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onAdded = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBreak = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onActivated = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onWalking = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onPlaced = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onClicked = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onCollided = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onPlacedBy = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onPlacedByPlayer = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onFallenUpon = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onBonemeal = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onRedstoneSignal = null;
    @Attribute(windowClass = WindowEditScript.class)
    public ScriptWrapper onRandomDisplayTick = null;

    protected Class<? extends Window> textureWindow = WindowEditTextures.class;

    public BlockAttributes(BaseContentPack pack)
    {
        super(pack);
    }

    public IconWrapper getTexture(int side)
    {
        switch (side)
        {
            case 0:
                return textureBottom;
            case 1:
                return textureTop;
            case 2:
                return textureNorth;
            case 3:
                return textureSouth;
            case 4:
                return textureEast;
            default:
                return textureWest;
        }
    }

    public IconWrapper getTexture(String name)
    {
        if (name.equals("bottom"))
            return textureBottom;
        if (name.equals("top"))
            return textureTop;
        if (name.equals("north"))
            return textureNorth;
        if (name.equals("south"))
            return textureSouth;
        if (name.equals("east"))
            return textureEast;
        if (name.equals("west"))
            return textureWest;
        return null;
    }

    public void postCreateBlock(Block block)
    {
        if (drop == null)
        {
            drop = new BlockDrop(block, 0);
        }
        if (pick == null)
        {
            pick = new ItemStack(block);
        }
    }

    @Override
    public Class<? extends Window> getWindowClass(AttributeData item)
    {
        if (item.field.getName().equals("textureBottom"))
            return textureWindow;
        return super.getWindowClass(item);
    }
}
