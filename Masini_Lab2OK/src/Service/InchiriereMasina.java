package Service;

import Domain.Inchiriere;
import Domain.Masina;
import Repository.DataOverlaps;
import Repository.DuplicateEntityException;
import Repository.IRepository;
import Repository.MasinaRepository;

import java.io.IOException;
import java.util.*;

public class InchiriereMasina {
     static IRepository<Inchiriere> repository;

    public InchiriereMasina(IRepository<Inchiriere> repository)
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
    public void add(Inchiriere entity) throws DuplicateEntityException, DataOverlaps {

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
        String[] data_inceput = {"04/03/2023", "20/09/2023", "08/12/2022", "26/04/2023", "16/08/2023", "01/11/2022", "10/07/2023", "22/01/2023", "11/05/2023", "29/11/2022", "14/06/2022", "02/02/2023", "18/10/2022", "05/04/2023", "23/08/2023", "12/12/2022", "28/06/2022", "17/02/2023", "09/10/2022", "25/05/2023", "13/09/2023", "01/05/2022", "19/11/2022", "06/07/2023", "24/01/2023", "15/03/2022", "31/07/2023", "18/09/2022", "03/12/2022", "21/04/2023", "09/08/2022", "27/05/2023", "11/02/2022", "05/10/2022", "22/03/2023", "10/07/2022", "26/11/2022", "15/05/2023", "03/01/2023", "19/08/2022", "06/12/2022", "24/04/2023", "12/10/2022", "30/06/2023", "16/02/2022", "08/09/2022", "23/03/2023", "13/11/2022","12/01/2020", "12/02/2020", "15/03/2021", "23/07/2021", "29/07/2022","13/01/2020","14/01/2020","15/01/2020","16/02/2020","12/04/2020","19/05/2020","18/10/2020","26/11/2020","20/10/2003"};
        String[] data_final = {"01/01/2022", "15/07/2022", "30/12/2022", "05/03/2023", "22/09/2023", "11/04/2022", "03/08/2022", "19/11/2023", "07/06/2022", "14/02/2023", "20/10/2023", "08/09/2022", "26/05/2023", "10/12/2022", "02/03/2023", "18/07/2023", "04/11/2022", "21/09/2022", "15/04/2022", "09/08/2023", "27/12/2022", "16/06/2023", "12/02/2022", "08/10/2022", "05/05/2023", "23/01/2023", "14/03/2022", "30/07/2023", "17/09/2022", "01/12/2022", "09/04/2023", "22/08/2023", "06/11/2022", "29/06/2022", "14/01/2022", "03/05/2023", "11/09/2023", "28/03/2022", "19/07/2022", "25/12/2023", "13/02/2022", "07/10/2022", "24/04/2023", "18/08/2023", "02/11/2022", "20/06/2022", "09/01/2022","18/01/2020", "08/02/2020", "01/03/2021", "19/07/2021", "05/07/2022","18/01/2020", "08/02/2020", "01/03/2021", "19/07/2021", "05/07/2022","12/01/2020", "12/02/2020", "15/03/2021", "23/07/2021", "29/07/2022"};
        Random random = new Random();
        int id;


        do {
            // Generăm un ID până când găsim unul care nu există deja
            id = random.nextInt(1000);
        } while (generatedIdsInchiriere.contains(id));

        generatedIdsInchiriere.add(id);

        // Alegeți aleatoriu o mașină din lista de mașini
        int idMasina = masini.get(random.nextInt(masini.size())).getId();


        String[] dateFormats = {"dd/MM/yyyy"};

        String dataInceput;
        String dataFinal;
        do {
            dataInceput = data_inceput[random.nextInt(data_inceput.length)];
            dataFinal = data_final[random.nextInt(data_final.length)];
        }while (hasOverlappingDates(new Inchiriere(id, idMasina, dataInceput, dataFinal))|| dataInceput.compareTo(dataFinal) >= 0);

        return new Inchiriere(id, idMasina, dataInceput, dataFinal);
    }
    public void random100MInchiriere() throws DuplicateEntityException, DataOverlaps {
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
        MasinaService masinaService = new MasinaService(new MasinaRepository(dbLocation));
        try {
            return new ArrayList<>(masinaService.getAll());
        } catch (DuplicateEntityException e) {
            throw new RuntimeException("Error getting cars: " + e.getMessage());
        }
    }
}
