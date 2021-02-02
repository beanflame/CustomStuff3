package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.DungeonMob;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.util.GeneralHelper;

public class WindowEditOrCreateDungeonMob extends WindowEditOrCreate<DungeonMob>
{
    private DropBox<String> dbMob;
    private NumericUpDown nupRarity;

    public WindowEditOrCreateDungeonMob(BaseContentPack pack)
    {
        super("New Dungeon Mob", 180, 105, pack);
    }

    public WindowEditOrCreateDungeonMob(DungeonMob editingMob, BaseContentPack pack)
    {
        super("Edit Dungeon Mob", 180, 105, editingMob, pack);
    }

    @Override
    protected void initControls()
    {
        row("Mob:");
        dbMob = row(dropBox(GeneralHelper.getMobNames()));

        row("Rarity:", Strings.INFO_DUNGEON_MOB_RARITY);
        nupRarity = row(numericUpDown());
        nupRarity.setMinValue(1);

        if (editingContent != null)
        {
            dbMob.setSelectedValue(editingContent.mob);
            nupRarity.setValue(editingContent.rarity);
        } else
        {
            dbMob.setSelectedValue("Creeper");
            nupRarity.setValue(100);
        }
    }

    @Override
    protected DungeonMob createContent()
    {
        String mob = dbMob.getSelectedValue();
        int rarity = nupRarity.getValue();
        return new DungeonMob(mob, rarity, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.mob = dbMob.getSelectedValue();
        editingContent.rarity = nupRarity.getValue();
    }
}
