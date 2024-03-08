package UI;

import Domain.Entity;
import Domain.Inchiriere;
import Domain.Masina;
import Domain.StatisticaMasina;
import Repository.DuplicateEntityException;
import Service.ServiceInchiriere;
import Service.ServiceMasina;
import Service.StatisticaService;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class Console {
    ServiceMasina serviceMasina;
    ServiceInchiriere serviceInchiriere;
    StatisticaService statisticaService;

    public Console(ServiceMasina serviceMasina,ServiceInchiriere serviceInchiriere,StatisticaService statisticaService)
    {
        this.statisticaService=statisticaService;
        this.serviceMasina=serviceMasina;
        this.serviceInchiriere=serviceInchiriere;
    }

    private void updateMasina() throws DuplicateEntityException {
        System.out.println("Introduceți ID-ul entității pe care doriți să o actualizați:");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumă newline
        if (serviceMasina.find(id) != null) {
            System.out.println("Masina gasita!");
            System.out.println("Noua marcă: ");
            String nouaMarca = scanner.nextLine();
            System.out.println("Noul model: ");
            String noulModel = scanner.nextLine();
            Masina masina = new Masina(id, nouaMarca, noulModel);
            serviceMasina.update(masina);
            System.out.println("Entitate actualizată cu succes");
        }
        else {
            System.out.println("Mașina cu ID-ul " + id + " nu a fost găsită.");
        }
    }
    private void updateInchiriere() throws DuplicateEntityException{
        System.out.println("Introduceți ID-ul entității pe care doriți să o actualizați:");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumă newline
        if (serviceInchiriere.find(id) != null) {

            System.out.println("Inchiriere gasita!");
            System.out.println("Id-ul masinii: ");
            int moulid_masina=scanner.nextInt();
            System.out.println("Noua data de inceput: ");
            String nouaData_Inceput = scanner.nextLine();
            System.out.println("Noua data de final: ");
            String nouaData_final = scanner.nextLine();
            if(serviceMasina.find(moulid_masina)==null)
            {
                System.out.println("Masina cu acest id nu exista");
            }
            else {
                Inchiriere inchiriere = new Inchiriere(id, moulid_masina, nouaData_Inceput, nouaData_final);
                serviceInchiriere.update(inchiriere);
                System.out.println("Entitate actualizată cu succes");
            }
        }
        else {
            System.out.println("Inchirierea cu ID-ul " + id + " nu a fost găsită.");
        }
    }
    public void runMenu() throws DuplicateEntityException, IOException{
        while(true)
        {
            printMenu();
            String option;
            Scanner scanner=new Scanner(System.in);
            option=scanner.next();
            switch(option) {
                case "1": {
                    try
                    {
                        int id=scanner.nextInt();
                        String marca=scanner.next();
                        String model=scanner.next();
                        Masina masina=new Masina(id,marca,model);
                        serviceMasina.add(masina);
                    }
                    catch (DuplicateEntityException ex){
                        System.out.println(ex.toString());
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex.toString());
                    }

                    break;


                }
                case "a":{
                    serviceMasina.random100Masina();
                    System.out.println("S-au adaugat cu succes!");
                    break;
                }
                case "2": {
                    Collection<Masina> masini=serviceMasina.getAll();
                    for (Masina m: masini)
                        System.out.println(m);
                    break;
                }
                case "3":{
                    updateMasina();
                    break;

                }
                case "4":
                {
                    System.out.println("Introduceți ID-ul entității pe care doriți să-l stergeti:");
                    int id=scanner.nextInt();
                    if(serviceMasina.find(id)!=null) {
                        serviceMasina.remove(id);
                        System.out.println("Stergerea masinii s-a finalizat!");
                    }
                    else {
                        System.out.println("Mașina cu ID-ul " + id + " nu a fost găsită.");
                    }
                    break;
                }
                case "5":
                {
                    System.out.println("Introduceți ID-ul entității pe care doriți să-l cautati:");
                    int id=scanner.nextInt();
                    System.out.println(serviceMasina.find(id));
                    break;
                }
                case "6":
                {
                    try
                    {

                        int id=scanner.nextInt();
                        int id_masina=scanner.nextInt();
                        String data_inceput=scanner.next();
                        String data_final=scanner.next();
                        Inchiriere inchiriere=new Inchiriere(id,id_masina,data_inceput,data_final);
                        Masina masina =serviceMasina.find(inchiriere.getIdmasina());
                        if (serviceInchiriere.hasOverlappingDates(inchiriere)) {
                            System.out.println("Închirierea se suprapune cu alte închirieri existente cu acest id.");
                        }
                        else if(masina == null)
                        {
                            System.out.println("Masina cu acest id nu exista");
                        }
                        else {
                            serviceInchiriere.add(inchiriere);
                            System.out.println("Închiriere adăugată cu succes.");
                        }
                    }
                    catch (DuplicateEntityException ex){
                        System.out.println(ex.toString());
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex.toString());
                    }

                    break;
                }
                case "b":
                {
                    serviceInchiriere.random100MInchiriere();
                    System.out.println("S-au adaugat cu succes!");
                    break;
                }
                case "7":
                {
                    Collection<Inchiriere> inchirieres=serviceInchiriere.getAll();
                    for (Inchiriere i: inchirieres)
                        System.out.println(i);
                    break;
                }
                case "8":
                {
                    updateInchiriere();
                    break;
                }
                case "9":
                {
                    System.out.println("Introduceți ID-ul entității pe care doriți să-l stergeti:");
                    int id=scanner.nextInt();
                    if(serviceInchiriere.find(id)!=null) {
                        serviceInchiriere.remove(id);
                        System.out.println("Stergerea inchirierii s-a finalizat!");
                    }
                    else {
                        System.out.println("Inchirierea cu ID-ul " + id + " nu a fost găsită.");
                    }
                    break;
                }
                case "10":
                {
                    System.out.println("Introduceți ID-ul entității pe care doriți să-l cautati:");
                    int id=scanner.nextInt();
                    System.out.println(serviceInchiriere.find(id));
                    break;
                }
                case "r1":
                {
                    System.out.println("Cele mai des închiriate mașini: ");
                    List<StatisticaMasina> masiniStatistica = statisticaService.getMasiniCeleMaiDesInchiriate();

                    // Afișați rezultatele în consolă
                    for (StatisticaMasina statisticaMasina : masiniStatistica) {
                        System.out.println("Masina: " + statisticaMasina.getMasina());
                        System.out.println("Număr închirieri: " + statisticaMasina.getNumarInchirieri());
                        System.out.println("---");
                    }
                    break;

                }
                case "r2":
                {
                    System.out.println("Numărul de închirieri efectuate în fiecare lună a anului: ");
                    List<Pair<String, Long>> luniStatistica = statisticaService.getLuniCuNumarInchirieriDescrescatoare();

                    for (Pair<String, Long> pair : luniStatistica) {
                        System.out.println("Luna: " + pair.getKey());
                        System.out.println("Număr închirieri: " + pair.getValue());
                        System.out.println("---");
                    }
                    break;

                }
                case "r3":
                {
                    System.out.println("Mașinile care au fost închiriate cel mai mult timp: ");
                    List<StatisticaMasina> masiniStatistica = statisticaService.getMasiniInchiriateCelMaiMultTimp();

                    // Afișați rezultatele în consolă
                    for (StatisticaMasina statisticaMasina : masiniStatistica) {
                        System.out.println("Masina: " + statisticaMasina.getMasina());
                        System.out.println("Număr zile închirieri: " + statisticaMasina.getNumarZileInchiriate());
                        System.out.println("---");
                    }
                    break;

                }

                case "x":{
                    System.out.println("Iesire din meniu.");
                    return;
                }
                default:{
                    System.out.println("Optiune invalida.");
                }
            }

        }
    }
    private void printMenu()
    {
        System.out.println("1. Adauga masina");
        System.out.println("a. Adauga 100 de masini random");
        System.out.println("2. Afiseaza toate masinile");
        System.out.println("3. Actualizeaza masini");
        System.out.println("4. Sterge masina");
        System.out.println("5. Cauta masina");
        System.out.println("6. Adauga inchiriere");
        System.out.println("b. Adauga 100 de inchirieri");
        System.out.println("7. Afiseaza toate inchirierile");
        System.out.println("8. Actualizeaza inchiriere");
        System.out.println("9. Sterge inchiriere");
        System.out.println("10. Cauta inchiriere");
        System.out.println("r1. Cele mai des închiriate mașini.");
        System.out.println("r2. Numărul de închirieri efectuate în fiecare lună a anului.");
        System.out.println("r3. Mașinile care au fost închiriate cel mai mult timp.");
        System.out.println("x. Exit");
    }
}
