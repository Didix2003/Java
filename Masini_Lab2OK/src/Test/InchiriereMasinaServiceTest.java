package Test;

import Domain.Inchiriere;
import Repository.DataOverlaps;
import Repository.DuplicateEntityException;
import Repository.IRepository;
import Repository.MemoryRepository;
import Service.InchiriereMasina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

public class InchiriereMasinaServiceTest {
    private IRepository<Inchiriere> repository;
    private InchiriereMasina inchiriereMasinaService;

    @BeforeEach
    void setUp() {
        repository = new MemoryRepository<>();
        inchiriereMasinaService = new InchiriereMasina(repository);
    }
    @Test
    void testHasOverlappingDates_NoOverlappingDates_ReturnsFalse() throws DuplicateEntityException, DuplicateEntityException {
        // Arrange
        Inchiriere existingEntity = new Inchiriere(1, 1, "15/07/2022", "18/07/2022");
        repository.add(existingEntity);
        Inchiriere newEntity = new Inchiriere(2, 1, "12/07/2022", "14/07/2022");
        boolean result = inchiriereMasinaService.hasOverlappingDates(newEntity);
        assertFalse(result);
    }
    @Test
    void testHasOverlappingDates_OverlappingDates_ReturnsTrue() throws DuplicateEntityException {
        // Arrange
        Inchiriere existingEntity = new Inchiriere(1, 1, "12/07/2022", "14/07/2022");
        repository.add(existingEntity);
        Inchiriere newEntity = new Inchiriere(2, 1, "12/07/2022", "14/07/2022");
        boolean result = inchiriereMasinaService.hasOverlappingDates(newEntity);
        assertTrue(result);
    }
    @Test
    void testAdd() throws DuplicateEntityException, DataOverlaps {
        Inchiriere entity = new Inchiriere(1, 1, "12/07/2022", "14/07/2022");
        inchiriereMasinaService.add(entity);
        assertTrue(repository.getAll().contains(entity));
    }
    @Test
    void testRemove() throws IOException, DuplicateEntityException {
        int entityId = 1;
        Inchiriere entity = new Inchiriere(entityId, 1, "12/07/2022", "14/07/2022");
        repository.add(entity);
        inchiriereMasinaService.remove(entityId);
        assertFalse(repository.getAll().contains(entity));
    }

    @Test
    void testUpdate() throws DuplicateEntityException {
        Inchiriere entity = new Inchiriere(1, 1, "12/07/2022", "14/07/2022");
        repository.add(entity);
        Inchiriere entityupdate = new Inchiriere(1, 1, "15/07/2022", "18/07/2022");
        inchiriereMasinaService.update(entityupdate);
        assertTrue(repository.getAll().contains(entityupdate));
    }

    @Test
    void testFind() throws DuplicateEntityException {
        int entityId = 1;
        Inchiriere entity = new Inchiriere(1, 1, "12/07/2022", "14/07/2022");
        repository.add(entity);
        Inchiriere foundEntity = inchiriereMasinaService.find(entityId);
        assertEquals(entity, foundEntity);
    }

    @Test
    void testGetAll() throws DuplicateEntityException {
        Inchiriere entity1 = new Inchiriere(1, 1, "12/07/2022", "14/07/2022");
        Inchiriere entity2 = new Inchiriere(2, 2, "14/07/2022", "18/07/2022");
        repository.add(entity1);
        repository.add(entity2);
        Collection<Inchiriere> allEntities = inchiriereMasinaService.getAll();
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
    }

}