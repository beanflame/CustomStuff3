package cubex2.cs3.common.scripting;

import cubex2.cs3.common.BaseContentPack;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class ScriptableContentPack
{
    private BaseContentPack pack;
    private Context context;
    private Scriptable scope;

    public ScriptableContentPack(BaseContentPack pack, Context cx, Scriptable scope)
    {
        this.pack = pack;
        context = cx;
        this.scope = scope;
    }

}

