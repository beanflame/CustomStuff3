package cubex2.cs3.common.scripting;

import cubex2.cs3.util.GeneralHelper;
import net.minecraft.entity.item.EntityItem;

public class ScriptableEntityItem extends ScriptableEntity
{
    private EntityItem item;

    public ScriptableEntityItem(EntityItem entity)
    {
        super(entity);
        item = entity;
    }

    public String getItemName()
    {
        return GeneralHelper.getItemName(item.getEntityItem().getItem());
    }

    public int getItemDamageValue()
    {
        return item.getEntityItem().getItemDamage();
    }

    public int getItemStackSize()
    {
        return item.getEntityItem().stackSize;
    }

}
