package cubex2.cs3.common;

public abstract class BaseContent implements Content
{
    protected final BaseContentPack pack;
    private boolean applied = false;

    public BaseContent(BaseContentPack pack)
    {
        this.pack = pack;
    }

    public BaseContentPack getPack()
    {
        return pack;
    }

    @Override
    public void apply()
    {
        if (applied)
        {
            throw new IllegalStateException("Content is already applied!");
        }

        pack.getContentRegistry(this).add(this);
        pack.save();

        applied = true;
    }

    @Override
    public void edit()
    {
        pack.save();
    }

    @Override
    public void remove()
    {
        pack.getContentRegistry(this).remove(this);
        pack.save();
    }

    @Override
    public boolean canEdit()
    {
        return true;
    }

    @Override
    public boolean canRemove()
    {
        return true;
    }

    public void postInit()
    {

    }
}
