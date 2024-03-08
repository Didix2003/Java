package Repository;

import Domain.Entity;
import Domain.IEntityFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.*;

public class FileRepository<T extends Entity> extends MemoryRepository<T> {
    private String fileName;
    private IEntityFactory<T> entityFactory;

    public FileRepository(String fileName, IEntityFactory<T> entityFactory) throws FileNotFoundException, DuplicateEntityException, IOException {
        this.fileName = fileName;
        this.entityFactory = entityFactory;
        readFromFile();
    }

    private void readFromFile() throws FileNotFoundException, DuplicateEntityException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            T entity = entityFactory.createEntity(line);
            add(entity);
        }
        scanner.close();
    }

    public void add(T o) throws DuplicateEntityException {
        super.add(o);
        try {
            saveFile();
        } catch (Exception e) {
            throw new DuplicateEntityException("Error saving object" + e.getMessage());
        }
    }

    public void remove(int entity) throws IOException {
        super.remove(entity);
        saveFile();
    }

    public void update(T entity) throws DuplicateEntityException {
        super.update(entity);
        try {
            saveFile();
        } catch (Exception e) {
            throw new DuplicateEntityException("Error saving object" + e.getMessage());
        }
    }

    public T find(int id)
    {
        return super.find(id);
    }
    private void saveFile()  {
        // TODO File is rewritten at each modification :'(
        try (FileWriter fw = new FileWriter(fileName)) {
            for (T object : entities) {
                fw.write(entityFactory.toString(object));
                fw.write("\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
