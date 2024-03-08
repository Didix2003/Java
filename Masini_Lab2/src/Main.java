import Domain.*;
import Repository.*;
import Service.IService;
import Service.InchiriereMasina;
import Service.MasinaService;
import UI.Console;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Provider;
import java.util.Objects;

//TODO:Adauga inca un id la inchiriere
public class Main {
    public static void main(String[] args) throws DuplicateEntityException, IOException, ClassNotFoundException {

        //IRepository<Masina> repository1=new MemoryRepository<>();
        //IRepository<Inchiriere> repository2=new MemoryRepository<>();

        //IEntityFactory<Masina> masinaFactory=new MasinaFactory();
        //IRepository<Masina> repository1=new FileRepository<>("masina.txt",masinaFactory);
        //IEntityFactory<Inchiriere> inchiriereFactory=new InchiriereFactory();
        //IRepository<Inchiriere> repository2=new FileRepository<>("inchiriere.txt",inchiriereFactory);

       // IRepository<Masina> repository1 = new BinaryFileRepository<>("data.bin");
       //IRepository<Inchiriere> repository2 = new BinaryFileRepository<>("datainchiriere.bin");

        IRepository<Masina> repository1 =new MemoryRepository<>();
        IEntityFactory<Masina> masinaIEntityFactory=new MasinaFactory();
        IRepository<Inchiriere> repository2 =new MemoryRepository<>();
        IEntityFactory<Inchiriere> inchiriereIEntityFactory=new InchiriereFactory();
        Settings setari = Settings.getInstance();
        if (Objects.equals(setari.getRepoType(), "memory")) {
            repository1 = new MemoryRepository<>();
            repository2=new MemoryRepository<>();
        } else if (Objects.equals(setari.getRepoType(), "text")) {
            repository1 = new FileRepository<>(setari.getRepoFile(), masinaIEntityFactory);
            repository2=new FileRepository<>(setari.getRepoFile2(),inchiriereIEntityFactory);
        } else if (Objects.equals(setari.getRepoType(),"bin")) {
            repository1=new BinaryFileRepository<>(setari.getRepoFile());
            repository2=new BinaryFileRepository<>(setari.getRepoFile2());
        } else {
            throw new UnsupportedOperationException("Tip de repository necunoscut: " + setari.getRepoType());
        }
        MasinaService serviceMasina= new MasinaService(repository1);
        InchiriereMasina serviceInchiriere= new InchiriereMasina(repository2);
        Console console=new Console(serviceMasina,serviceInchiriere);

        console.runMenu();


    }
}