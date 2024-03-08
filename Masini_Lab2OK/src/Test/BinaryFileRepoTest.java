package Test;

import Domain.Inchiriere;
import Domain.Masina;
import Repository.BinaryFileRepository;
import Repository.DuplicateEntityException;
import Repository.IRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class BinaryFileRepoTest {
    private static final String fileName = "test_binary_repo.bin";
    private IRepository<Inchiriere> binaryFileRepository;
    @BeforeEach
    void setUp() throws IOException, ClassNotFoundException {
        binaryFileRepository = new BinaryFileRepository<>(fileName);
    }
    @Test
    void testAdd() throws DuplicateEntityException {
        Inchiriere entity = new Inchiriere(1, 1, "12/07/2022","14/07/2022");
        binaryFileRepository.add(entity);
        assertTrue(binaryFileRepository.getAll().contains(entity));
    }
    @Test
    void testRemove() throws IOException, DuplicateEntityException {
        // Arrange
        int entityId = 2;
        Inchiriere entity = new Inchiriere(entityId, 2, "12/07/2022","14/07/2022");
        binaryFileRepository.add(entity);
        binaryFileRepository.remove(entityId);
        assertFalse(binaryFileRepository.getAll().contains(entity));
    }

    @Test
    void testUpdate() throws DuplicateEntityException {
        Inchiriere entity = new Inchiriere(3, 3, "12/07/2022","14/07/2022");
        Inchiriere entityupdate = new Inchiriere(3, 3, "16/07/2022","19/07/2022");
        binaryFileRepository.add(entity);
        binaryFileRepository.update(entityupdate);
        assertTrue(binaryFileRepository.getAll().contains(entityupdate));
    }

    @Test
    void testFind() throws DuplicateEntityException {
        int entityId = 4;
        Inchiriere entity = new Inchiriere(entityId, 4, "12/07/2022","14/07/2022");
        binaryFileRepository.add(entity);
        Inchiriere foundEntity = binaryFileRepository.find(entityId);
        assertEquals(entity, foundEntity);
    }

}
