package Service;

import Domain.Entity;
import Repository.DataOverlaps;
import Repository.DuplicateEntityException;
import Repository.IRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IService<T extends Entity> {
    IRepository<T> repository;

    public IService(IRepository<T> repository)
    {
        this.repository=repository;
    }

    public void add(T entity) throws DuplicateEntityException, DataOverlaps {

        repository.add(entity);
    }
    public void remove(int id) throws IOException {
        repository.remove(id);
    }
    public void update(T entity) throws DuplicateEntityException {repository.update(entity);}
    public T find(int id)
    {
        return repository.find(id);
    }
    public Collection<T> getAll() throws DuplicateEntityException {
        return repository.getAll();
    }
}

