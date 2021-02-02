package cubex2.cs3.ingame.gui.control;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IToolTipModifier
{
    void modifyToolTip(List<String> toolTipList, ItemStack stack);
}
