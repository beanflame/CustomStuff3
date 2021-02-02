package cubex2.cs3.common.attribute;

import com.google.common.collect.Maps;
import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.common.attribute.bridges.*;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.gui.data.ShiftClickRules;
import cubex2.cs3.tileentity.data.FurnaceModules;
import cubex2.cs3.util.BlockDrop;
import cubex2.cs3.util.IconWrapper;
import cubex2.cs3.util.ScriptWrapper;
import cubex2.cs3.util.ToolClass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public abstract class DefaultAttributeBridges
{
    private static Map<Class<?>, Class<? extends AttributeBridge>> defaultBridges = Maps.newHashMap();

    public static Class<? extends AttributeBridge> getDefaultBridge(Class<?> attributeClass)
    {
        if (defaultBridges.containsKey(attributeClass))
        {
            return defaultBridges.get(attributeClass);
        }
        return NullAttributeBridge.class;
    }

    public static <T> void register(Class<T> attributeClass, Class<? extends AttributeBridge<T>> handlerClass)
    {
        defaultBridges.put(attributeClass, handlerClass);
    }

    static
    {
        register(String.class, StringBridge.class);
        register(IconWrapper.class, IconWrapperBridge.class);
        register(int.class, IntegerBridge.class);
        register(boolean.class, BooleanBridge.class);
        register(CreativeTabs.class, CreativeTabBridge.class);
        register(ScriptWrapper.class, ScriptWrapperBridge.class);
        register(EnumAction.class, EnumActionBridge.class);
        register(float.class, FloatBridge.class);
        register(ItemStack.class, ItemStackBridge.class);
        register(Material.class, MaterialBridge.class);
        register(Block.SoundType.class, StepSoundBridge.class);
        register(BlockDrop.class, BlockDropBridge.class);
        register(String[].class, StringArrayBridge.class);
        register(ToolClass[].class, ToolClassArrayBridge.class);
        register(ItemStack[].class, ItemStackArrayBridge.class);
        register(ItemArmor.ArmorMaterial.class, ArmorMaterialBridge.class);
        register(Block.class, BlockBridge.class);
        register(Potion.class, PotionBridge.class);
        register(WrappedTileEntity.class, WrappedTileEntityBridge.class);
        register(GuiData.class, GuiDataBridge.class);
        register(ShiftClickRules.class, ShiftClickRulesBridge.class);
        register(FurnaceModules.class, FurnaceModulesBridge.class);
        register(ResourceLocation.class, ResourceLocationBridge.class);
    }
}
