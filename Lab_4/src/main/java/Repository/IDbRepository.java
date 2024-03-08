package Repository;

import Domain.Entity;
import Domain.Masina;

import java.util.ArrayList;

public interface IDbRepository<T extends Entity>  extends IRepository<T> {

    void openConnection();
    void closeConnection();
    void createTable();
    void initTable()throws DuplicateEntityException;

}
