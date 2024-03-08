package Test;

import Domain.Masina;
import Repository.DuplicateEntityException;
import Repository.IRepository;
import Repository.MemoryRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

public class MemoryRepoTest {
    @Test
    public void testAdd() throws DuplicateEntityException {
        IRepository<Masina> data = new MemoryRepository<>();
        data.add(new Masina(1, "Fiat", "Punto"));
        data.add(new Masina(2, "Dacia", "1930"));
        assertEquals(2, data.getAll().size());
    }
    @Test
    public void testRemove() throws DuplicateEntityException, IOException {
        IRepository<Masina> data = new MemoryRepository<>();
        Masina masina1 = new Masina(1, "Fiat", "Punto");
        data.add(masina1);
        Masina masina2 = new Masina(2, "Dacia", "1930");
        data.add(masina2);
        data.remove(masina1.getId());
        assertEquals(1, data.getAll().size());
        assertFalse(data.getAll().contains(masina1));
        assertTrue(data.getAll().contains(masina2));
    }

    @Test
    public void testFind() throws DuplicateEntityException {
        IRepository<Masina> data = new MemoryRepository<>();
        Masina masina = new Masina(1, "Fiat", "Punto");
        data.add(masina);
        Masina foundMasina = data.find(masina.getId());
        assertNotNull(foundMasina);
        assertEquals(masina, foundMasina);
    }

    @Test
    public void testUpdate() throws DuplicateEntityException {
        // Arrange
        IRepository<Masina> data = new MemoryRepository<>();
        Masina masina = new Masina(1, "Fiat", "Punto");
        data.add(masina);
        Masina updatedMasina = new Masina(1, "Renault", "Clio");
        data.update(updatedMasina);
        assertEquals(1, data.getAll().size());
        assertFalse(data.getAll().contains(masina));
        assertTrue(data.getAll().contains(updatedMasina));
    }
    @Test
    public void testGetAll() throws DuplicateEntityException {
        // Arrange
        IRepository<Masina> data = new MemoryRepository<>();
        Masina masina1 = new Masina(1, "Fiat", "Punto");
        data.add(masina1);
        Masina masina2 = new Masina(2, "Dacia", "1930");
        data.add(masina2);

        // Act
        Collection<Masina> allMasini = data.getAll();

        // Assert
        assertEquals(2, allMasini.size());
        assertTrue(allMasini.contains(masina1));
        assertTrue(allMasini.contains(masina2));
    }

    @Test
    public void testIterator() throws DuplicateEntityException {
        // Arrange
        IRepository<Masina> data = new MemoryRepository<>();
        Masina masina1 = new Masina(1, "Fiat", "Punto");
        data.add(masina1);
        Masina masina2 = new Masina(2, "Dacia", "1930");
        data.add(masina2);

        // Act
        Iterator<Masina> iterator = data.iterator();

        // Assert
        assertTrue(iterator.hasNext());
        assertEquals(masina1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(masina2, iterator.next());
        assertFalse(iterator.hasNext());
    }

}
