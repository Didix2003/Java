package UI;

import Domain.*;
import Repository.*;
import Service.ServiceInchiriere;
import Service.ServiceMasina;
import Service.StatisticaService;
import UI.Console;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Provider;
import java.util.Objects;

//TODO:Adauga inca un id la inchiriere
public class Main {
    public static void main() throws DuplicateEntityException, IOException,ClassNotFoundException {

        //IRepository<Masina> repository1=new MemoryRepository<>();
        //IRepository<Inchiriere> repository2=new MemoryRepository<>();

        //IEntityFactory<Masina> masinaFactory=new MasinaFactory();
        //IRepository<Masina> repository1=new FileRepository<>("masina.txt",masinaFactory);
        //IEntityFactory<Inchiriere> inchiriereFactory=new InchiriereFactory();
        //IRepository<Inchiriere> repository2=new FileRepository<>("inchiriere.txt",inchiriereFactory);

        // IRepository<Masina> repository1 = new BinaryFileRepository<>("data.bin");
        //IRepository<Inchiriere> repository2 = new BinaryFileRepository<>("datainchiriere.bin");

        IRepository<Masina> repository1 =new MemoryRepository<>();
        IRepository<Inchiriere> repository2 =new MemoryRepository<>();
        Settings setari = Settings.getInstance();
        if (Objects.equals(setari.getRepoType(), "memory")) {
            repository1 = new MemoryRepository<>();
            repository2 = new MemoryRepository<>();
        }
        else if(Objects.equals(setari.getRepoType(),"db"))
        {
            repository1=new MasinaDbRepository(setari.getRepoFile());
            repository2=new InchiriereDbRepository(setari.getRepoFile2());

        }
        else {
            throw new UnsupportedOperationException("Tip de repository necunoscut: " + setari.getRepoType());
        }
        ServiceMasina serviceMasina= new ServiceMasina(repository1);
        ServiceInchiriere serviceInchiriere= new ServiceInchiriere(repository2);
        StatisticaService statisticaService=new StatisticaService(serviceInchiriere, serviceMasina);
        Console console=new Console(serviceMasina,serviceInchiriere,statisticaService);

        console.runMenu();
        /*MasinaRepository repository1=new MasinaRepository();
        InchiriereDbRepository repository2=new InchiriereDbRepository();
        MasinaService serviceMasina= new MasinaService(repository1);
        InchiriereMasina serviceInchiriere= new InchiriereMasina(repository2);
        Console console=new Console(serviceMasina,serviceInchiriere);

        console.runMenu();*/
        ((MasinaDbRepository) repository1).closeConnection();
        ((InchiriereDbRepository) repository2).closeConnection();




    }
}