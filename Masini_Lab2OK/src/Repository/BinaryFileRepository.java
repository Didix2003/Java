package Repository;

import Domain.Entity;

import java.io.*;
import java.util.List;

public class BinaryFileRepository <T extends Entity> extends MemoryRepository<T> {
    private String filename;

    public BinaryFileRepository(String filename) throws IOException, ClassNotFoundException {
        this.filename=filename;
        loadFile();
    }

    @Override
    public void add(T o) throws DuplicateEntityException {
        super.add(o);
        // saveFile se executa doar daca super.add() nu a aruncat exceptie
        try {
            saveFile();
        } catch (IOException e) {
            throw new DuplicateEntityException("Error saving file " + e.getMessage());
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
    private void loadFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            List<T> entitiesFromFile = (List<T>) objectInputStream.readObject();
            entities.addAll(entitiesFromFile);
        } catch (IOException | ClassNotFoundException e) {
            // Ignorați în cazul în care fișierul nu există sau conține date corupte
        }
    }
    private void saveFile() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOutputStream.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
