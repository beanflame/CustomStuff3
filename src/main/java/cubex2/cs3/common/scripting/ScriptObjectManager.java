package cubex2.cs3.common.scripting;

import com.google.common.collect.Lists;
import cubex2.cs3.api.scripting.IScriptableObjectManager;
import cubex2.cs3.api.scripting.IScriptableObjectProvider;

import java.util.List;

public class ScriptObjectManager implements IScriptableObjectManager
{
    public static List<IScriptableObjectProvider> providers = Lists.newArrayList();

    @Override
    public void registerProvider(IScriptableObjectProvider provider)
    {
        providers.add(provider);
    }

}
