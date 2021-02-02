package cubex2.cs3.util;

import org.mozilla.javascript.Script;

public class ScriptWrapper
{
    public Script script;
    private String source;

    public ScriptWrapper(String source)
    {
        setSource(source);
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String s)
    {
        source = s;
        if (source == null)
        {
            script = null;
        }
        else
        {
            script = JavaScriptHelper.createScript(source, "Script");
        }
    }
}
