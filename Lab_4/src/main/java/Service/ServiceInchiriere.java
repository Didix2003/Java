package Service;

import Domain.Inchiriere;
import Domain.Masina;
import Repository.DuplicateEntityException;
import Repository.IRepository;
import Repository.MasinaDbRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ServiceInchiriere {
    static IRepository<Inchiriere> repository;

    public ServiceInchiriere(IRepository<Inchiriere> repository)
    {
        this.repository=repository;
    }
    public static boolean hasOverlappingDates(Inchiriere entitate) throws DuplicateEntityException {
        Collection<Inchiriere> inchirieres=repository.getAll();
        for (Inchiriere existingEntity : inchirieres) {
            if (existingEntity.getIdmasina() == entitate.getIdmasina()) {
                if (entitate.getData_inceput().compareTo(existingEntity.getData_final()) <= 0)
                {
                    if(existingEntity.getData_inceput().compareTo(entitate.getData_final()) <= 0){
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public void add(Inchiriere entity) throws DuplicateEntityException{

        repository.add(entity);
    }
    public void remove(int id) throws IOException {
        repository.remove(id);
    }
    public void update(Inchiriere entity) throws DuplicateEntityException {repository.update(entity);}
    public Inchiriere find(int id)
    {
        return repository.find(id);
    }
    public Collection<Inchiriere> getAll() throws DuplicateEntityException {
        return repository.getAll();
    }
    private static Set<Integer> generatedIdsInchiriere = new HashSet<>();

    public static Inchiriere generateRandomInchiriere() throws DuplicateEntityException {
        List<Masina> masini = getMasini("masina.db");
        Random random = new Random();
        int id;

        do {
            id = random.nextInt(1000);
        } while (generatedIdsInchiriere.contains(id));

        generatedIdsInchiriere.add(id);

        int idMasina = masini.get(random.nextInt(masini.size())).getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate;
        LocalDate endDate;

        do {
            // Generează aleatoriu o zi între 1 și 28
            int ziInceput = random.nextInt(28) + 1;
            int ziFinal = random.nextInt(28) + 1;

            // Generează aleatoriu o lună între 1 și 12
            int lunaInceput = random.nextInt(12) + 1;
            int lunaFinal = random.nextInt(12) + 1;

            // Generează aleatoriu un an între 2000 și 2023
            int anInceput = random.nextInt(2023 - 2000 + 1) + 2000;
            int anFinal = random.nextInt(2023 - 2000 + 1) + 2000;

            String dataInceput = String.format("%02d/%02d/%04d", ziInceput, lunaInceput, anInceput);
            String dataFinal = String.format("%02d/%02d/%04d", ziFinal, lunaFinal, anFinal);

            startDate = LocalDate.parse(dataInceput, formatter);
            endDate = LocalDate.parse(dataFinal, formatter);

        } while (hasOverlappingDates(new Inchiriere(id, idMasina, startDate.format(formatter), endDate.format(formatter))) || startDate.isAfter(endDate));

        return new Inchiriere(id, idMasina, startDate.format(formatter), endDate.format(formatter));
    }
    public void random100MInchiriere() throws DuplicateEntityException{
        try {
            for (int i = 0; i < 100; i++) {
                Inchiriere inchiriere = generateRandomInchiriere();
                add(inchiriere);
            }
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }

    }
    private static List<Masina> getMasini(String dbLocation) throws DuplicateEntityException {
        ServiceMasina masinaService = new ServiceMasina(new MasinaDbRepository(dbLocation));
        try {
            return new ArrayList<>(masinaService.getAll());
        } catch (DuplicateEntityException e) {
            throw new RuntimeException("Error getting cars: " + e.getMessage());
        }
    }
}
