package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.docs.ParsedDocFile;

public class WindowScriptInfo extends WindowDocs
{
    private final String scriptName;
    private final AttributeContainer container;

    public WindowScriptInfo(String scriptName, AttributeContainer container)
    {
        super(scriptName, ParsedDocFile.fromPath(getDocs(scriptName, container)));
        this.scriptName = scriptName;
        this.container = container;
    }

    private static String getDocs(String scriptName, AttributeContainer container)
    {
        TriggerType type = container instanceof BlockAttributes ? TriggerType.BLOCK : TriggerType.ITEM;
        return type.name().toLowerCase() + "/" + scriptName + ".txt";
    }
}
