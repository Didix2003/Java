    package Service;

    import Domain.Entity;
    import Domain.Masina;
    import Repository.DataOverlaps;
    import Repository.DuplicateEntityException;
    import Repository.IRepository;

    import java.io.IOException;
    import java.util.*;

    public class MasinaService {
        IRepository<Masina> repository;

        public MasinaService(IRepository<Masina> repository)
        {
            this.repository=repository;
        }

        public void add(Masina entity) throws DuplicateEntityException, DataOverlaps {

            repository.add(entity);
        }
        public void remove(int id) throws IOException {
            repository.remove(id);
        }
        public void update(Masina entity) throws DuplicateEntityException {repository.update(entity);}
        public Masina find(int id)
        {
            return repository.find(id);
        }
        public Collection<Masina> getAll() throws DuplicateEntityException {
            return repository.getAll();
        }
        private static Set<Integer> generatedIds = new HashSet<>();
        private static Masina generateRandomMasina()throws DuplicateEntityException {
            String[] marci = {"Ford", "Toyota", "Honda", "Chevrolet", "Volkswagen"};
            String[] modele = {"Focus", "Corolla", "Civic", "Malibu", "Golf"};

            Random random = new Random();
            int id;
            do {
                // Generăm un ID până când găsim unul care nu există deja
                id = random.nextInt(1000);
            } while (generatedIds.contains(id));

            generatedIds.add(id);
            String marca = marci[random.nextInt(marci.length)];
            String model = modele[random.nextInt(modele.length)];

            return new Masina(id,marca, model);
        }
        public void random100Masina() throws DuplicateEntityException, DataOverlaps {
            try {
                for (int i = 0; i < 100; i++) {
                    Masina masina = generateRandomMasina();
                    add(masina);
                }
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }

        }
    }
