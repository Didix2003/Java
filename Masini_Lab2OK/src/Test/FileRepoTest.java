package Test;

import Domain.MasinaFactory;
import Repository.FileRepository;
import Repository.DuplicateEntityException;
import Domain.Masina;
import Domain.IEntityFactory;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class FileRepoTest {

    private static final String TEST_FILE_NAME = "test_file_repo.txt";

    IEntityFactory<Masina> masinaFactory = new MasinaFactory() {
    };
    FileRepository<Masina> fileRepository;
    {
        try {
            fileRepository = new FileRepository<>(TEST_FILE_NAME, masinaFactory);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);}}

    @Test
    public void testAdd() throws DuplicateEntityException, IOException {
        Masina masina = new Masina(1, "TestMarca", "TestModel");
        fileRepository.add(masina);
        assertTrue(fileContainsEntity(TEST_FILE_NAME, masina));}
    @Test
    public void testRemove() throws DuplicateEntityException, IOException {
        Masina masina2 = new Masina(2, "TestMarca2", "TestModel2");
        fileRepository.add(masina2);
        fileRepository.remove(masina2.getId());
        assertTrue(!fileContainsEntity(TEST_FILE_NAME, masina2));}
    @Test
    public void testUpdate() throws DuplicateEntityException, IOException {
        Masina masina3 = new Masina(3, "TestMarca3", "TestModel3");
        Masina updatedMasina = new Masina(3, "UpdatedMarca", "UpdatedModel");
        fileRepository.add(masina3);
        fileRepository.update(updatedMasina);
        assertTrue(fileContainsEntity(TEST_FILE_NAME, updatedMasina));}

    private boolean fileContainsEntity(String fileName, Masina masina) throws IOException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Masina existingMasina = masinaFactory.createEntity(line);
            if (masina.getId() == existingMasina.getId() &&
                    masina.getMarca().equals(existingMasina.getMarca()) &&
                    masina.getModel().equals(existingMasina.getModel())) {
                scanner.close();
                return true;
            }
        }
        scanner.close();
        return false;}
}