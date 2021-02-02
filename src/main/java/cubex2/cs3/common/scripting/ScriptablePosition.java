package cubex2.cs3.common.scripting;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ScriptablePosition extends ScriptableObject implements Cloneable
{
    private static final long serialVersionUID = -7583400857543055489L;

    public double x;
    public double y;
    public double z;

    public double jsGet_x()
    {
        return x;
    }

    public double jsGet_y()
    {
        return y;
    }

    public double jsGet_z()
    {
        return z;
    }

    public void jsSet_x(double x)
    {
        this.x = x;
    }

    public void jsSet_y(double y)
    {
        this.y = y;
    }

    public void jsSet_z(double z)
    {
        this.z = z;
    }

    public Scriptable jsContructor(double x, double y, double z)
    {
        return new ScriptablePosition(x, y, z);
    }

    public ScriptablePosition(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ScriptablePosition()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    @Override
    public String getClassName()
    {
        return "Position";
    }

    @Override
    public Object clone()
    {
        return new ScriptablePosition(x, y, z);
    }

}
