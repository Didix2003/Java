package Repository;

import Domain.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MemoryRepository <T extends Entity> implements IRepository<T> {
    List<T> entities=new ArrayList<T>();

    @Override
    public void add(T entity) throws DuplicateEntityException {
        if(entity==null)
        {
            throw new IllegalArgumentException("Entitatea nu poate fi nula!");
        }
        if(find(entity.getId())!=null)
        {
            throw new DuplicateEntityException("Entitatea deja exista!");
        }
        entities.add(entity);
    }

    @Override
    public void remove(int id) throws IOException {

        for (T entity : entities) {
            if (entity.getId() == id) {
                entities.remove(entity);
                break; //sa mi iasa din bucla
            }
        }

    }

    @Override
    public T find(int id) {
        for (T entity: entities)
        {
            if(entity.getId()==id)
            {
                return entity;
            }
        }
        return null;
    }
    @Override
    public void update(T entity) throws DuplicateEntityException {
        if(entity==null)
        {
            throw new IllegalArgumentException("Entitatea nu poate fi nula!");
        }
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId()) {
                entities.set(i, entity);
                break;
            }
        }
    }

    @Override
    public Collection<T> getAll() throws DuplicateEntityException {
        return new ArrayList<T>(entities);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(entities).iterator();
    }
}
