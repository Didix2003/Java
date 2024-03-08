package Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 1000L;
    protected final int id;
    public Entity(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return id;
    }
}
