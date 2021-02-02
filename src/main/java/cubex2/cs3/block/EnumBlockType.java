package cubex2.cs3.block;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.block.attributes.*;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.item.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Map;

public enum EnumBlockType
{
    BUTTON("button", BlockCSButton.class, ButtonAttributes.class),
    CARPET("carpet", BlockCSCarpet.class, CarpetAttributes.class),
    // CHEST("chest", BlockCSChest.class, BlockChestAttributes.class),
    CROSS_TEXTURE("crossTexture", BlockCSCrossTexture.class, CrossTextureAttributes.class),
    CROSS_TEXTURE_POST("crossTexturePost", BlockCSCrossTexturePost.class, CrossTexturePostAttributes.class, ItemCSBlockFacing.class),
    DOOR("door", BlockCSDoor.class, DoorAttributes.class, ItemCSDoor.class),
    FACING("facing", BlockCSFacing.class, FacingAttributes.class, ItemCSBlockFacing.class),
    FENCE("fence", BlockCSFence.class, FenceAttributes.class),
    FENCE_GATE("fenceGate", BlockCSFenceGate.class, FenceGateAttributes.class),
    FLAT("flat", BlockCSFlat.class, FlatAttributes.class),
    FLUID("fluid", BlockCSFluid.class, FluidAttributes.class, ItemCSFluid.class),
    // FURNACE("furnace", BlockCSFurnace.class, BlockFurnaceAttributes.class, ItemCSBlockFacing.class),
    GRAVITY("gravity", BlockCSGravity.class, GravityAttributes.class),
    LADDER("ladder", BlockCSLadder.class, LadderAttributes.class),
    NORMAL("normal", BlockCSNormal.class, BlockAttributes.class),
    PANE("pane", BlockCSPane.class, PaneAttributes.class),
    POST("post", BlockCSPost.class, PostAttributes.class, ItemCSBlockFacing.class),
    PRESSURE_PLATE("pressurePlate", BlockCSPressurePlate.class, PressurePlateAttributes.class),
    SLAB("slab", BlockCSStep.class, StepAttributes.class, ItemCSStep.class),
    // SLOPE("slope", BlockSlope.class, BlockSlopeAttributes.class, ItemCSBlockFacing.class),
    STAIRS("stairs", BlockCSStairs.class, StairAttributes.class),
    TORCH("torch", BlockCSTorch.class, TorchAttributes.class),
    TRAP_DOOR("trapDoor", BlockCSTrapDoor.class, TrapDoorAttributes.class),
    WALL("wall", BlockCSWall.class, WallAttributes.class),
    WHEAT("wheat", BlockCSWheat.class, WheatAttributes.class);

    public final String name;
    public final Class<? extends Block> blockClass;
    public final Class<? extends BlockAttributes> attributesClass;
    public final Class<? extends Item> itemClass;

    private EnumBlockType(String name, Class<? extends Block> blockClass, Class<? extends BlockAttributes> attributeClass)
    {
        this(name, blockClass, attributeClass, ItemCSBlock.class);
    }

    private EnumBlockType(String name, Class<? extends Block> blockClass, Class<? extends BlockAttributes> attributesClass, Class<? extends Item> itemClass)
    {
        this.name = name;
        this.blockClass = blockClass;
        this.attributesClass = attributesClass;
        this.itemClass = itemClass;
    }

    /**
     * Creates a block and its item.
     *
     * @param wrappedBlock The wrappedBlock.
     * @return The created block.
     */
    public Block createBlock(WrappedBlock wrappedBlock)
    {
        try
        {
            Block block = blockClass.getConstructor(WrappedBlock.class).newInstance(wrappedBlock);
            if (ItemBlock.class.isAssignableFrom(itemClass))
            {
                Class<? extends ItemBlock> clazz = (Class<? extends ItemBlock>) itemClass;
                GameRegistry.registerBlock(block, clazz, wrappedBlock.getName(), wrappedBlock);
            } else
            {
                GameRegistry.registerBlock(block, null, wrappedBlock.getName());
                Item item = itemClass.getConstructor(WrappedBlock.class).newInstance(wrappedBlock);
                GameRegistry.registerItem(item, wrappedBlock.getName());
            }

            return block;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public BlockAttributes createAttributeContainer(WrappedBlock wrappedBlock)
    {
        try
        {
            return attributesClass.getConstructor(BaseContentPack.class).newInstance(wrappedBlock.getPack());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Map<String, EnumBlockType> map = Maps.newHashMap();

    public static EnumBlockType get(String name)
    {
        if (map.isEmpty())
        {
            for (EnumBlockType e : values())
            {
                map.put(e.name, e);
            }
        }
        return map.get(name);
    }

    public static String[] getNames()
    {
        String[] names = new String[values().length];
        for (int i = 0; i < names.length; i++)
        {
            names[i] = values()[i].name;
        }

        return names;
    }
}
