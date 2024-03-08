package Test;


import Domain.Masina;
import Repository.DataOverlaps;
import Repository.DuplicateEntityException;
import Repository.IRepository;
import Repository.MemoryRepository;
import Service.InchiriereMasina;
import Service.MasinaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

public class MasinaServiceTest {
    private IRepository<Masina> repository;
    private MasinaService MasinaService;

    @BeforeEach
    void setUp() {
        repository = new MemoryRepository<>();
        MasinaService = new MasinaService(repository);
    }

    @Test
    void testAdd() throws DuplicateEntityException, DataOverlaps {
        Masina entity = new Masina(1, "Dacia","Logan");
        MasinaService.add(entity);
        assertTrue(repository.getAll().contains(entity));
    }
    @Test
    void testRemove() throws IOException, DuplicateEntityException {
        int entityId = 1;
        Masina entity = new Masina(entityId, "Dacia","Logan");
        repository.add(entity);
        MasinaService.remove(entityId);
        assertFalse(repository.getAll().contains(entity));
    }
    @Test
    void testUpdate() throws DuplicateEntityException {
        Masina entity = new Masina(1, "Dacia","Logan");
        repository.add(entity);
        Masina entityupdate = new Masina(1, "ORice","Orice");
        MasinaService.update(entityupdate);
        assertTrue(repository.getAll().contains(entityupdate));
    }

    @Test
    void testFind() throws DuplicateEntityException {
        int entityId = 1;
        Masina entity = new Masina(entityId, "Dacia","Logan");
        repository.add(entity);
        Masina foundEntity = MasinaService.find(entityId);
        assertEquals(entity, foundEntity);
    }

    @Test
    void testGetAll() throws DuplicateEntityException {
        Masina entity1 = new Masina(1, "Dacia","Logan");
        Masina entity2 = new Masina(2, "Dacia","Logan");
        repository.add(entity1);
        repository.add(entity2);
        Collection<Masina> allEntities = MasinaService.getAll();
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
    }

}