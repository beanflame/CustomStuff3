package cubex2.cs3.api.scripting;

import cubex2.cs3.api.IContentPack;

import java.util.List;

public interface IScriptableObjectProvider
{
    List<Object> getGlobalScriptObjects(IContentPack contentPack);

    List<Object> getScriptObjectsForTrigger(ITriggerData data, IContentPack contentPack);
}
