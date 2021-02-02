package cubex2.cs3.util;

import cubex2.cs3.api.scripting.IScriptableObjectProvider;
import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.scripting.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class JavaScriptHelper
{
    public static BaseContentPack executingPack = null;

    public static Script createPositionScript;
    private static ScriptableObject sharedScope;

    static
    {
        sharedScope = Context.enter().initStandardObjects();
        createPositionScript = Context.getCurrentContext().compileString("var position = new Position(0, 0, 0)", "int", 0, null);
        Context.exit();

    }

    public static Scriptable getInstanceScope(Context cx, BaseContentPack pack)
    {
        Scriptable scope = cx.newObject(sharedScope);
        scope.setPrototype(sharedScope);
        scope.setParentScope(null);
        ScriptableObject.putProperty(scope, "mod", new ScriptableContentPack(pack, cx, scope));
        //ScriptableObject.putProperty(scope, "config", new ScriptableConfig(mod));

        for (IScriptableObjectProvider provider : ScriptObjectManager.providers)
        {
            List<Object> scriptObjects = provider.getGlobalScriptObjects(pack);
            if (scriptObjects != null)
            {
                for (int i = 0; i < scriptObjects.size(); i += 2)
                {
                    ScriptableObject.putProperty(scope, scriptObjects.get(i).toString(), scriptObjects.get(i + 1));
                }
            }
        }

        return scope;
    }

    public static Script createScript(String source, String sourceName)
    {
        Script script = null;
        Context cx = Context.enter();
        try
        {
            script = cx.compileString(source, sourceName, 0, null);
        } finally
        {
            Context.exit();
        }
        return script;
    }

    public static Script createScript(File source) throws Exception
    {
        Script script = null;
        Context cx = Context.enter();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(source));
            script = cx.compileReader(reader, source.getName(), 0, null);
            reader.close();
        } finally
        {
            Context.exit();
        }
        return script;
    }

    public static void executeTrigger(Script script, ITriggerData data, BaseContentPack pack)
    {
        try
        {
            executeTrigger(script, data, pack, null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static <T> T executeTrigger(Script script, ITriggerData data, BaseContentPack pack, T defaultResult)
    {
        T result = defaultResult;
        Context cx = Context.enter();
        try
        {
            Scriptable scope = JavaScriptHelper.getInstanceScope(cx, pack);
            ScriptableObject.defineClass(scope, ScriptablePosition.class);

            if (result != null)
            {
                ScriptableObject.putProperty(scope, "result", result);
            }
            if (data.getWorld() != null)
            {
                ScriptableWorld scriptableWorld = new ScriptableWorld(data.getWorld());
                scriptableWorld.setMod(pack);
                ScriptableObject.putProperty(scope, "world", scriptableWorld);
            }
            if (data.getPositionX() != null)
            {
                JavaScriptHelper.createPositionScript.exec(cx, scope);
                ScriptablePosition pos = (ScriptablePosition) ScriptableObject.getProperty(scope, "position");
                pos.x = data.getPositionX();
                pos.y = data.getPositionY();
                pos.z = data.getPositionZ();
            }
            if (data.getPlayer() != null)
            {
                ScriptableEntityPlayer player = new ScriptableEntityPlayer(data.getPlayer());
                //player.setMod(pack);
                ScriptableObject.putProperty(scope, "player", player);
            }
            if (data.getInteractPlayer() != null)
            {
                ScriptableEntityPlayer player = new ScriptableEntityPlayer(data.getInteractPlayer());
                //player.setMod(mod);
                ScriptableObject.putProperty(scope, "interactPlayer", player);
            }
            if (data.getLiving() != null)
            {
                ScriptableObject.putProperty(scope, "living", new ScriptableEntityLiving(data.getLiving()));
            }
            if (data.getItem() != null)
            {
                ScriptableObject.putProperty(scope, "item", new ScriptableEntityItem(data.getItem()));
            }
            if (data.getEntity() != null)
            {
                ScriptableObject.putProperty(scope, "entity", new ScriptableEntity(data.getEntity()));
            }
            if (data.getItemStack() != null)
            {
                ScriptableObject.putProperty(scope, "itemstack", new ScriptableItemStack(data.getItemStack()));
            }
            if (data.getHitX() != null)
            {
                ScriptableObject.putProperty(scope, "hitX", data.getHitX());
                ScriptableObject.putProperty(scope, "hitY", data.getHitY());
            }
            if (data.getSide() != null)
            {
                ScriptableObject.putProperty(scope, "side", data.getSide());
            }
            if (data.getBlockName() != null)
            {
                ScriptableObject.putProperty(scope, "blockName", data.getBlockName());
            }
            if (data.getSlotId() != null)
            {
                ScriptableObject.putProperty(scope, "slotId", data.getSlotId());
            }
            if (data.getTickCount() != null)
            {
                ScriptableObject.putProperty(scope, "tickCount", data.getTickCount());
            }
            if (data.getIsCurrentItem() != null)
            {
                ScriptableObject.putProperty(scope, "isCurrentItem", data.getIsCurrentItem());
            }
            if (data.getRedstoneSignal() != null)
            {
                ScriptableObject.putProperty(scope, "redstoneSignal", data.getRedstoneSignal());
            }
            if (data.getGuiX() != null)
            {
                ScriptableObject.putProperty(scope, "guiX", data.getGuiX());
                ScriptableObject.putProperty(scope, "guiY", data.getGuiY());
            }
            if (data.getMouseX() != null)
            {
                ScriptableObject.putProperty(scope, "mouseX", data.getMouseX());
                ScriptableObject.putProperty(scope, "mouseY", data.getMouseY());
            }
            if (data.getWidth() != null)
            {
                ScriptableObject.putProperty(scope, "width", data.getWidth());
                ScriptableObject.putProperty(scope, "height", data.getHeight());
            }
            if (data.getCraftMatrix() != null)
            {
                ScriptableObject.putProperty(scope, "craftMatrix", new ScriptableInventory(data.getCraftMatrix()));
            }

            for (IScriptableObjectProvider provider : ScriptObjectManager.providers)
            {
                List<Object> scriptObjects = provider.getScriptObjectsForTrigger(data, pack);
                if (scriptObjects != null)
                {
                    for (int i = 0; i < scriptObjects.size(); i += 2)
                    {
                        ScriptableObject.putProperty(scope, scriptObjects.get(i).toString(), scriptObjects.get(i + 1));
                    }
                }
            }

            executingPack = pack;
            script.exec(cx, scope);

            if (result != null)
            {
                result = (T) ScriptableObject.getProperty(scope, "result");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            Context.exit();
            executingPack = null;
        }

        return result;
    }
}
