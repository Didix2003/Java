package Repository;

import Domain.Entity;

import java.io.IOException;
import java.util.Collection;

public interface IRepository<T extends Entity> extends Iterable<T>{
    public void add(T entity) throws DuplicateEntityException;
    public void remove(int id) throws IOException;
    public T find(int id);
    public void update(T entity) throws DuplicateEntityException;
    Collection<T> getAll() throws DuplicateEntityException;
}
