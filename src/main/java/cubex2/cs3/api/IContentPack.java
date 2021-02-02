package cubex2.cs3.api;

import java.io.File;
import java.util.logging.Logger;

public interface IContentPack
{
    String getName();

    File getDirectory();

    Logger getLogger();
}
