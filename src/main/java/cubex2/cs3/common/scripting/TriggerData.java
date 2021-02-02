package cubex2.cs3.common.scripting;

import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TriggerData implements ITriggerData
{
    private String triggerName;
    private String blockName;
    private TriggerType triggerType;
    private World world;
    private Integer x;
    private Integer y;
    private Integer z;
    private EntityPlayer player;
    private EntityPlayer interactPlayer;
    private EntityLivingBase living;
    private EntityItem item;
    private Entity entity;
    private ItemStack itemStack;
    private Float hitX;
    private Float hitY;
    private Integer side;
    private Integer slotId;
    private Integer tickCount;
    private Boolean isCurrentItem;
    private Boolean redstoneSignal;
    private Integer mouseX;
    private Integer mouseY;
    private Integer guiX;
    private Integer guiY;
    private Integer width;
    private Integer height;
    private IInventory craftMatrix;

    public TriggerData(String name, TriggerType type)
    {
        triggerName = name;
        triggerType = type;
    }

    public TriggerData(String name, TriggerType type, World world, int x, int y, int z)
    {
        this(name, type);
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TriggerData(String name, TriggerType type, World world, EntityPlayer player, ItemStack itemStack)
    {
        this(name, type);
        this.world = world;
        this.player = player;
        this.itemStack = itemStack;
    }

    @Override
    public String getTriggerName()
    {
        return triggerName;
    }

    @Override
    public String getBlockName()
    {
        return blockName;
    }

    @Override
    public TriggerType getTriggerType()
    {
        return triggerType;
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public Integer getPositionX()
    {
        return x;
    }

    @Override
    public Integer getPositionY()
    {
        return y;
    }

    @Override
    public Integer getPositionZ()
    {
        return z;
    }

    @Override
    public EntityPlayer getPlayer()
    {
        return player;
    }

    @Override
    public EntityPlayer getInteractPlayer()
    {
        return interactPlayer;
    }

    @Override
    public EntityLivingBase getLiving()
    {
        return living;
    }

    @Override
    public EntityItem getItem()
    {
        return item;
    }

    @Override
    public Entity getEntity()
    {
        return entity;
    }

    @Override
    public ItemStack getItemStack()
    {
        return itemStack;
    }

    @Override
    public Float getHitX()
    {
        return hitX;
    }

    @Override
    public Float getHitY()
    {
        return hitY;
    }

    @Override
    public Integer getSide()
    {
        return side;
    }

    @Override
    public Integer getSlotId()
    {
        return slotId;
    }

    @Override
    public Integer getTickCount()
    {
        return tickCount;
    }

    @Override
    public Boolean getIsCurrentItem()
    {
        return isCurrentItem;
    }

    @Override
    public Boolean getRedstoneSignal()
    {
        return redstoneSignal;
    }

    @Override
    public Integer getMouseX()
    {
        return mouseX;
    }

    @Override
    public Integer getMouseY()
    {
        return mouseY;
    }

    @Override
    public Integer getGuiX()
    {
        return guiX;
    }

    @Override
    public Integer getGuiY()
    {
        return guiY;
    }

    @Override
    public Integer getWidth()
    {
        return width;
    }

    @Override
    public Integer getHeight()
    {
        return height;
    }

    public IInventory getCraftMatrix()
    {
        return craftMatrix;
    }

    public TriggerData setTriggerName(String triggerName)
    {
        this.triggerName = triggerName;
        return this;
    }

    public TriggerData setTriggerType(TriggerType triggerType)
    {
        this.triggerType = triggerType;
        return this;
    }

    public TriggerData setWorld(World world)
    {
        this.world = world;
        return this;
    }

    public TriggerData setPosition(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public TriggerData setPlayer(EntityPlayer player)
    {
        this.player = player;
        return this;
    }

    public TriggerData setInteractPlayer(EntityPlayer interactPlayer)
    {
        this.interactPlayer = interactPlayer;
        return this;
    }

    public TriggerData setLiving(EntityLivingBase living)
    {
        this.living = living;
        return this;
    }

    public TriggerData setItem(EntityItem item)
    {
        this.item = item;
        return this;
    }

    public TriggerData setEntity(Entity entity)
    {
        this.entity = entity;
        return this;
    }

    public TriggerData setItemStack(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        return this;
    }

    public TriggerData setSide(int side)
    {
        this.side = side;
        return this;
    }

    public TriggerData setSideAndHit(int side, float hitX, float hitY, float hitZ)
    {
        int lookDir = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        this.side = side;
        if (side == 0 || side == 1)
        {
            switch (lookDir)
            {
                case 0:
                    this.hitY = 1 - hitZ;
                    this.hitX = 1 - hitX;
                    break;
                case 1:
                    this.hitY = hitZ;
                    this.hitX = 1 - hitZ;
                    break;
                case 2:
                    this.hitY = hitZ;
                    this.hitX = hitX;
                    break;
                case 3:
                    this.hitY = 1 - hitZ;
                    this.hitX = hitZ;
                    break;
            }
        }
        switch (side)
        {
            case 0: // Y-
                switch (lookDir)
                {
                    case 0:
                        this.hitY = 1 - hitZ;
                        this.hitX = 1 - hitX;
                        break;
                    case 1:
                        this.hitY = hitX;
                        this.hitX = 1 - hitZ;
                        break;
                    case 2:
                        this.hitY = hitZ;
                        this.hitX = hitX;
                        break;
                    case 3:
                        this.hitY = 1 - hitX;
                        this.hitX = hitZ;
                        break;
                }
                break;
            case 1: // Y+
                switch (lookDir)
                {
                    case 0:
                        this.hitY = hitZ;
                        this.hitX = 1 - hitX;
                        break;
                    case 1:
                        this.hitY = 1 - hitX;
                        this.hitX = 1 - hitZ;
                        break;
                    case 2:
                        this.hitY = 1 - hitZ;
                        this.hitX = hitX;
                        break;
                    case 3:
                        this.hitY = hitX;
                        this.hitX = hitZ;
                        break;
                }
                break;
            case 2: // Z-
                this.hitX = 1 - hitX;
                this.hitY = hitY;
                break;
            case 3: // Z+
                this.hitX = hitX;
                this.hitY = hitY;
                break;
            case 4: // X-
                this.hitX = hitZ;
                this.hitY = hitY;
                break;
            case 5: // X+
                this.hitX = 1 - hitZ;
                this.hitY = hitY;
                break;
        }
        return this;
    }

    public TriggerData setBlockName(String blockName)
    {
        this.blockName = blockName;
        return this;
    }

    public TriggerData setSlotId(int slotId)
    {
        this.slotId = slotId;
        return this;
    }

    public TriggerData setTickCount(int tickCount)
    {
        this.tickCount = tickCount;
        return this;
    }

    public TriggerData setIsCurrentItem(boolean isCurrentItem)
    {
        this.isCurrentItem = isCurrentItem;
        return this;
    }

    public TriggerData setRedstoneSignal(boolean redstoneSignal)
    {
        this.redstoneSignal = redstoneSignal;
        return this;
    }

    public TriggerData setMousePosition(int mouseX, int mouseY)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        return this;
    }

    public TriggerData setGuiPosition(int guiX, int guiY)
    {
        this.guiX = guiX;
        this.guiY = guiY;
        return this;
    }

    public TriggerData setWidthAndHeight(int width, int height)
    {
        this.width = width;
        this.height = height;
        return this;
    }

    public TriggerData setCraftMatrix(IInventory craftMatrix)
    {
        this.craftMatrix = craftMatrix;
        return this;
    }
}

