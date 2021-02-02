package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.MobSpawn;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.lib.Biomes;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;

public class WindowEditOrCreateMobSpawn extends WindowEditOrCreate<MobSpawn>
{
    private DropBox<String> dbMob;
    private DropBox<EnumCreatureType> dbType;
    private NumericUpDown nupRate;
    private NumericUpDown nupMin;
    private NumericUpDown nupMax;
    private ListBox<BiomeGenBase> lbBiomes;

    public WindowEditOrCreateMobSpawn(BaseContentPack pack)
    {
        super("New Mob Spawn", 180, 225, pack);
    }

    public WindowEditOrCreateMobSpawn(MobSpawn spawn, BaseContentPack pack)
    {
        super("Edit Mob Spawn", 180, 225, spawn, pack);
    }

    @Override
    protected void initControls()
    {
        row("Mob:");
        dbMob = row(dropBox(GeneralHelper.getMobNames()));
        dbMob.setSelectedValue("Creeper");

        row("Spawn Type:");
        dbType = row(dropBox(EnumCreatureType.values()));
        dbType.setSelectedValue(EnumCreatureType.monster);

        row("Rate:", Strings.INFO_MOB_SPAWN_RATE);
        nupRate = row(numericUpDown());
        nupRate.setMinValue(1);

        row("Min Count:", Strings.INFO_MOB_SPAWN_MIN);
        /*ControlContainer c = row(container().height(9));
        c.col(c.label("Min Count:         ").width((180 - 14) / 2));
        c.col(c.label("Max Count:").width((180 - 14) / 2));*/

        ControlContainer c = row(container().height(20));
        nupMin = c.col(c.numericUpDown().width((180 - 14 - 2) / 2), 3);
        nupMin.setValue(1);
        nupMax = c.col(c.numericUpDown().width((180 - 14 - 4) / 2), 3);
        nupMax.setValue(1);

        label("Max Count:").left(nupMax, 0, true).bottom(nupMax, 4).add();
        infoButton(Strings.INFO_MOB_SPAWN_MAX).rightTo(lastControl).add();

        row("Biomes:");
        ListBoxDescription<BiomeGenBase> desc = new ListBoxDescription<BiomeGenBase>();
        desc.multiSelect = true;
        desc.elements = Biomes.getBiomes();
        desc.rows = 3;
        desc.sorted = true;
        desc.comparator = Biomes.COMPARATOR;
        lbBiomes = listBox(desc).below(lastControl).fillWidth(7).add();
        lbBiomes.disableGlobalScrolling();

        if (editingContent != null)
        {
            dbMob.setSelectedValue(editingContent.mob);
            dbType.setSelectedValue(editingContent.type);
            nupRate.setValue(editingContent.rate);
            nupMin.setValue(editingContent.min);
            nupMax.setValue(editingContent.max);
            lbBiomes.select(editingContent.biomes);
        } else
        {
            lbBiomes.select(Biomes.getBiomes());
        }
    }

    @Override
    protected MobSpawn createContent()
    {
        String mob = dbMob.getSelectedValue();
        EnumCreatureType type = dbType.getSelectedValue();
        int rate = nupRate.getValue();
        int min = nupMin.getValue();
        int max = nupMax.getValue();
        List<BiomeGenBase> biomes = Lists.newArrayList(lbBiomes.getSelectedItems());
        return new MobSpawn(mob, rate, min, max, type, biomes, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.mob = dbMob.getSelectedValue();
        editingContent.type = dbType.getSelectedValue();
        editingContent.rate = nupRate.getValue();
        editingContent.min = nupMin.getValue();
        editingContent.max = nupMax.getValue();
        if (editingContent.max < editingContent.min)
            editingContent.max = editingContent.min;
        editingContent.biomes.clear();
        editingContent.biomes.addAll(lbBiomes.getSelectedItems());
    }
}
