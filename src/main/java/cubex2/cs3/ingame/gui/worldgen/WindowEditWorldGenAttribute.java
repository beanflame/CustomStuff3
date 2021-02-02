package cubex2.cs3.ingame.gui.worldgen;

import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.ingame.gui.common.WindowEditContainerAttribute;
import cubex2.cs3.worldgen.attributes.WorldGenAttributes;

public class WindowEditWorldGenAttribute extends WindowEditContainerAttribute<WorldGenAttributes>
{
    protected final WrappedWorldGen wrappedWorldGen;

    public WindowEditWorldGenAttribute(WrappedWorldGen wrappedWorldGen, String title, int usedControls, int width, int height)
    {
        super(wrappedWorldGen.container, title, usedControls, width, height);
        this.wrappedWorldGen = wrappedWorldGen;
    }

    public WindowEditWorldGenAttribute(WrappedWorldGen wrappedWorldGen, String title, int width, int height)
    {
        super(wrappedWorldGen.container, title, width, height);
        this.wrappedWorldGen = wrappedWorldGen;
    }
}
