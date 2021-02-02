package cubex2.cs3.common;

import net.minecraft.nbt.NBTTagCompound;

public interface Content
{
    /**
     * Add the content to the game and save the pack
     */
    void apply();

    /**
     * Apply changes and save the pack
     */
    void edit();

    /**
     * Remove the content from the game and save the pack
     */
    void remove();

    /**
     * Can the content be edited while the game is running
     *
     * @return true if editable, false otherwise
     */
    boolean canEdit();

    /**
     * Can the content be removed while the game is running
     *
     * @return true if removable, false otherwise
     */
    boolean canRemove();

    /**
     * Write the content to the NBT compound
     *
     * @param compound
     */
    void writeToNBT(NBTTagCompound compound);

    /**
     * Read the content from the NBT compound
     *
     * @param compound
     */
    boolean readFromNBT(NBTTagCompound compound);

    /**
     * This is called during the post init phase.
     */
    void postInit();
}
