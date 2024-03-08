package com.example.lab_4;

import Domain.Inchiriere;
import Domain.Masina;
import Domain.Settings;
import Domain.StatisticaMasina;
import Repository.*;
import Service.ServiceInchiriere;
import Service.ServiceMasina;
import Service.StatisticaService;
import UI.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    TextField idTextField=new TextField();
    TextField idTextFieldInc=new TextField();
    TextField idTextField2=new TextField();
    TextField data_inceputTextField=new TextField();
    TextField data_finalTextField=new TextField();
    TextField marcaTextField=new TextField();
    TextField modelTextField=new TextField();

    public void MasinaJavaFx(ServiceMasina carService,BorderPane borderPane) throws DuplicateEntityException {

        VBox mainVerticalBox=new VBox();
        mainVerticalBox.setPadding(new Insets(10));
        mainVerticalBox.setSpacing(10);

        ObservableList<Masina> masini= FXCollections.observableArrayList(carService.getAll());
        ListView<Masina> listView=new ListView<Masina>(masini);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Masina car=listView.getSelectionModel().getSelectedItem();
                idTextField.setText(Integer.toString(car.getId()));
                marcaTextField.setText(car.getMarca());
                modelTextField.setText(car.getModel());
            }
        });

        mainVerticalBox.getChildren().add(listView);

        GridPane carsGridPane= new GridPane();
        Label idLabel=new Label();
        idLabel.setText("Id: ");
        idLabel.setPadding(new Insets(10,0,10,0));

        Label marcaLabel=new Label();
        marcaLabel.setText("Marca: ");
        marcaLabel.setPadding(new Insets(10,0,10,0));

        Label modelLabel=new Label();
        modelLabel.setText("Model: ");
        modelLabel.setPadding(new Insets(10,0,10,0));



        carsGridPane.add(idLabel,0,0);
        carsGridPane.add(idTextField,1,0);

        carsGridPane.add(marcaLabel,0,1);
        carsGridPane.add(marcaTextField,1,1);

        carsGridPane.add(modelLabel,0,2);
        carsGridPane.add(modelTextField,1,2);

        mainVerticalBox.getChildren().add(carsGridPane);

        HBox buttonsHorizontalBox=new HBox();
        buttonsHorizontalBox.setSpacing(10);
        mainVerticalBox.getChildren().add(buttonsHorizontalBox);

        Button addButton = new Button("Add car");

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idTextField.getText());
                    String marca = marcaTextField.getText();
                    String model = modelTextField.getText();
                    Masina masina=new Masina(id,marca,model);
                    carService.add(masina);
                    masini.setAll(carService.getAll());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        buttonsHorizontalBox.getChildren().add(addButton);


        Button updateButton = new Button("Update car");
        updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idTextField.getText());
                    String marca = marcaTextField.getText();
                    String model = modelTextField.getText();
                    if (carService.find(id) != null) {
                        Masina masina = new Masina(id, marca, model);
                        carService.update(masina);
                    }
                    else {
                        Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                        errorPopUp.setTitle("Error");
                        errorPopUp.setContentText("Masina cu acest id " +id+ " nu exista!");
                        errorPopUp.show();
                    }
                    masini.setAll(carService.getAll());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        buttonsHorizontalBox.getChildren().add(updateButton);

        Button deleteButton = new Button("Delete car");
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idTextField.getText());
                    if(carService.find(id)!=null) {
                        carService.remove(id);
                    }
                    else {
                        Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                        errorPopUp.setTitle("Error");
                        errorPopUp.setContentText("Masina cu acest id " +id+ " nu exista!");
                        errorPopUp.show();
                    }
                    masini.setAll(carService.getAll());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });

        buttonsHorizontalBox.getChildren().add(deleteButton);
        Button generateMasinaButton = new Button("Genereaza Masina");
        generateMasinaButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    carService.random100Masina();
                    masini.setAll(carService.getAll());
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }


            }
        });
        buttonsHorizontalBox.getChildren().add(generateMasinaButton);
        borderPane.setLeft(mainVerticalBox);
    }
    public void InchiriereJavaFx(ServiceInchiriere incService,BorderPane borderPane) throws DuplicateEntityException {

        VBox mainVerticalBox=new VBox();
        mainVerticalBox.setPadding(new Insets(10));
        mainVerticalBox.setSpacing(5);


        ObservableList<Inchiriere> inchirieres= FXCollections.observableArrayList(incService.getAll());
        ListView<Inchiriere> listView=new ListView<Inchiriere>(inchirieres);
        listView.setMaxHeight(400);
        listView.setMaxWidth(600);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Inchiriere inc=listView.getSelectionModel().getSelectedItem();
                if (inc != null) {
                    idTextFieldInc.setText(Integer.toString(inc.getId()));
                    idTextField2.setText(Integer.toString(inc.getIdmasina()));
                    data_inceputTextField.setText(inc.getData_inceput());
                    data_finalTextField.setText(inc.getData_final());
                }
            }
        });

        mainVerticalBox.getChildren().add(listView);

        GridPane incsGridPane= new GridPane();
        Label idLabel=new Label();
        idLabel.setText("Id: ");
        idLabel.setPadding(new Insets(10,0,10,0));

        Label idMasinaLabel=new Label();
        idMasinaLabel.setText("Id_Masina: ");
        idMasinaLabel.setPadding(new Insets(10,0,10,0));

        Label data_inceputLabel=new Label();
        data_inceputLabel.setText("Data_Inceput: ");
        data_inceputLabel.setPadding(new Insets(10,0,10,0));

        Label data_finalLabel=new Label();
        data_finalLabel.setText("Data_Final: ");
        data_finalLabel.setPadding(new Insets(10,0,10,0));



        incsGridPane.add(idLabel,0,0);
        incsGridPane.add(idTextFieldInc,1,0);

        incsGridPane.add(idMasinaLabel,0,1);
        incsGridPane.add(idTextField2,1,1);

        incsGridPane.add(data_inceputLabel,0,2);
        incsGridPane.add(data_inceputTextField,1,2);

        incsGridPane.add(data_finalLabel,0,3);
        incsGridPane.add(data_finalTextField,1,3);

        mainVerticalBox.getChildren().add(incsGridPane);

        HBox buttonsHorizontalBox=new HBox();
        buttonsHorizontalBox.setSpacing(10);
        mainVerticalBox.getChildren().add(buttonsHorizontalBox);

        Button addButton = new Button("Add inchiriere");

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idTextFieldInc.getText());
                    int id_masina = Integer.parseInt(idTextField2.getText());
                    String data_inceput = data_inceputTextField.getText();
                    String data_final = data_finalTextField.getText();
                    Inchiriere inchiriere=new Inchiriere(id,id_masina,data_inceput,data_final);
                    Masina masina =ServiceMasina.find(inchiriere.getIdmasina());
                    if (incService.hasOverlappingDates(inchiriere)) {
                        Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                        errorPopUp.setTitle("Error");
                        errorPopUp.setContentText("Închirierea se suprapune cu alte închirieri existente cu acest id!");
                        errorPopUp.show();
                    }
                    else if(masina == null)
                    {

                        Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                        errorPopUp.setTitle("Error");
                        errorPopUp.setContentText("Masina cu acest id nu exista!");
                        errorPopUp.show();
                    }
                    else {
                        incService.add(inchiriere);
                        inchirieres.setAll(incService.getAll());
                        System.out.println("Închiriere adăugată cu succes.");
                    }

                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        buttonsHorizontalBox.getChildren().add(addButton);


        Button updateButton = new Button("Update Inchiriere");
        updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idTextFieldInc.getText());
                    int id_masina = Integer.parseInt(idTextField2.getText());
                    String data_inceput = data_inceputTextField.getText();
                    String data_final = data_finalTextField.getText();
                    if (incService.find(id_masina)==null)
                    {
                        Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                        errorPopUp.setTitle("Error");
                        errorPopUp.setContentText("Masina cu acest id " +id_masina+ " nu exista!");
                        errorPopUp.show();
                    }
                    else if (incService.find(id) != null) {
                        Inchiriere inchiriere = new Inchiriere(id, id_masina, data_inceput, data_final);
                        incService.update(inchiriere);
                    }
                    else {
                        Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                        errorPopUp.setTitle("Error");
                        errorPopUp.setContentText("Inchirierea cu acest id " +id+ " nu exista!");
                        errorPopUp.show();
                    }
                    inchirieres.setAll(incService.getAll());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        buttonsHorizontalBox.getChildren().add(updateButton);

        Button deleteButton = new Button("Delete Inchiriere");
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idTextFieldInc.getText());
                    if(incService.find(id)!=null) {
                        incService.remove(id);
                        System.out.println("Stergerea Inchirierii s-a finalizat!");
                    }
                    else {
                        Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                        errorPopUp.setTitle("Error");
                        errorPopUp.setContentText("Inchirierea cu acest id " +id+ " nu exista!");
                        errorPopUp.show();
                    }
                    inchirieres.setAll(incService.getAll());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        buttonsHorizontalBox.getChildren().add(deleteButton);
        Button generateInchiriereButton = new Button("Genereaza Inchiriere");
        generateInchiriereButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    incService.random100MInchiriere();
                    inchirieres.setAll(incService.getAll());
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }


            }
        });
        buttonsHorizontalBox.getChildren().add(generateInchiriereButton);

        borderPane.setCenter(mainVerticalBox);
        BorderPane.setMargin(mainVerticalBox, new Insets(0, 90, 0, 0));
    }


    @Override
    public void start(Stage stage) throws IOException, DuplicateEntityException {

        IRepository<Masina> carRepo =new MemoryRepository<>();
        IRepository<Inchiriere> incRepo =new MemoryRepository<>();

        Settings setari = Settings.getInstance();
        if (Objects.equals(setari.getRepoType(), "memory")) {
            carRepo = new MemoryRepository<>();
            incRepo = new MemoryRepository<>();
        }
        else if(Objects.equals(setari.getRepoType(),"db"))
        {
            carRepo=new MasinaDbRepository(setari.getRepoFile());
            incRepo=new InchiriereDbRepository(setari.getRepoFile2());

        }
        else {
            throw new UnsupportedOperationException("Tip de repository necunoscut: " + setari.getRepoType());
        }
        ServiceMasina carService = new ServiceMasina(carRepo);
        ServiceInchiriere incService=new ServiceInchiriere(incRepo);
        StatisticaService statisticaService=new StatisticaService(incService,carService);

        /*try {
            Masina masina=new Masina(1, "BMW","Seria5");
            Masina masina1=new Masina(2, "Audi","A4");
            Masina masina2=new Masina(3, "Audi","A6");
            Masina masina3=new Masina(4, "Opel","Astra");
            Masina masina4=new Masina(5, "Ford","Focus");
            Masina masina5=new Masina(6, "Fiat","Punto");
            carService.add(masina);
            carService.add(masina1);
            carService.add(masina2);
            carService.add(masina3);
            carService.add(masina4);
            carService.add(masina5);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }*/
        BorderPane borderPane = new BorderPane();



        MasinaJavaFx(carService,borderPane);
        InchiriereJavaFx(incService,borderPane);

        /*VBox thirdVBox = new VBox();
        thirdVBox.setPadding(new Insets(10));
        thirdVBox.setSpacing(10);
        List<StatisticaMasina> masiniStatistica = statisticaService.getMasiniCeleMaiDesInchiriate();
        Label label=new Label("Numarul de inchirieri pt fiecare masina.");
        thirdVBox.getChildren().add(label);
        TextField txt=new TextField();
        try {

            for (StatisticaMasina statisticaMasina : masiniStatistica) {
                txt = new TextField(statisticaMasina.getMasina().toString() + " - " +
                        "Numar Inchirieri: " + statisticaMasina.getNumarInchirieri());
                thirdVBox.getChildren().add(txt);
                txt.setMinWidth(310);
            }
        } catch (Exception e) {
            txt.clear();
        }

        VBox fourVBox=new VBox();
        fourVBox.setPadding(new Insets(10));
        fourVBox.setSpacing(10);
        List<StatisticaMasina> statisticaMasina=statisticaService.getMasiniInchiriateCelMaiMultTimp();
        Label label2=new Label("Mașinile care au fost închiriate cel mai mult timp.");
        fourVBox.getChildren().add(label2);
        TextField txt2=new TextField();
        try {
            for (StatisticaMasina statisticaMasina2 : statisticaMasina) {
                txt2 = new TextField(statisticaMasina2.getMasina().toString() + " - " +
                        "Numar zile Inchirieri: " + statisticaMasina2.getNumarZileInchiriate());
                fourVBox.getChildren().add(txt2);
                txt2.setMinWidth(330);
            }
        } catch (Exception e) {
            txt2.clear();
        }


        VBox fiveVBox = new VBox();
        fiveVBox.setPadding(new Insets(10));
        fiveVBox.setSpacing(10);

        List<Pair<String, Long>> luniStatistica = statisticaService.getLuniCuNumarInchirieriDescrescatoare();
        Label label3 = new Label("Numărul de închirieri efectuate în fiecare lună a anului.");
        fiveVBox.getChildren().add(label3);
        TextField txt3=new TextField();
        try {

            for (Pair<String, Long> pair : luniStatistica) {
                String luna = pair.getKey();
                Long numarInchirieri = pair.getValue();

                txt3 = new TextField(luna + " - Număr închirieri: " + numarInchirieri);
                fiveVBox.getChildren().add(txt3);
                txt3.setMinWidth(200);
            }
        } catch (Exception e) {
            txt3.clear();

        }
        HBox hBox = new HBox(thirdVBox,fourVBox,fiveVBox);
        borderPane.setRight(hBox);*/


        Button refreshButton = new Button("Refresh");
        refreshButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent mouseEvent) {
                //Primul Raport
                VBox thirdVBox = new VBox();
                thirdVBox.setPadding(new Insets(10));
                thirdVBox.setSpacing(10);

                List<StatisticaMasina> masiniStatistica = null;
                try {
                    masiniStatistica = statisticaService.getMasiniCeleMaiDesInchiriate();
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }

                Label label = new Label("Numarul de inchirieri pt fiecare masina.");
                thirdVBox.getChildren().add(label);

                ListView<String> listView1 = new ListView<>();
                thirdVBox.getChildren().add(listView1);

                try {
                    // Construiește o listă de șiruri pentru a fi afișată în ListView
                    List<String> items = masiniStatistica.stream()
                            .map(statisticaMasina -> statisticaMasina.getMasina().toString() + " - Numar Inchirieri: " + statisticaMasina.getNumarInchirieri())
                            .collect(Collectors.toList());

                    // Adaugă lista în ListView
                    listView1.getItems().addAll(items);
                } catch (Exception e) {
                    // Tratează excepțiile, de exemplu, afișează un mesaj de eroare
                    listView1.getItems().clear();
                }
                //Al doilea
                VBox fourVBox = new VBox();
                fourVBox.setPadding(new Insets(10));
                fourVBox.setSpacing(10);

                List<StatisticaMasina> statisticaMasina = null;
                try {
                    statisticaMasina = statisticaService.getMasiniInchiriateCelMaiMultTimp();
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }

                Label label2 = new Label("Mașinile care au fost închiriate cel mai mult timp.");
                fourVBox.getChildren().add(label2);

                ListView<String> listView2 = new ListView<>();
                fourVBox.getChildren().add(listView2);

                try {
                    // Construiește o listă de șiruri pentru a fi afișată în ListView
                    List<String> items2 = statisticaMasina.stream()
                            .map(statisticaMasina2 -> statisticaMasina2.getMasina().toString() + " - Numar zile Inchirieri: " + statisticaMasina2.getNumarZileInchiriate())
                            .collect(Collectors.toList());

                    // Adaugă lista în ListView
                    listView2.getItems().addAll(items2);
                } catch (Exception e) {
                    listView2.getItems().clear();
                    e.printStackTrace();
                }

                //Al treilea
                VBox fiveVBox = new VBox();
                fiveVBox.setPadding(new Insets(10));
                fiveVBox.setSpacing(10);

                List<Pair<String, Long>> luniStatistica = null;
                try {
                    luniStatistica = statisticaService.getLuniCuNumarInchirieriDescrescatoare();
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }

                Label label3 = new Label("Numărul de închirieri efectuate în fiecare lună a anului.");
                fiveVBox.getChildren().add(label3);

                ListView<String> listView3 = new ListView<>();
                fiveVBox.getChildren().add(listView3);

                try {
                    // Construiește o listă de șiruri pentru a fi afișată în ListView
                    List<String> items = luniStatistica.stream()
                            .map(pair -> pair.getKey() + " - Număr închirieri: " + pair.getValue())
                            .collect(Collectors.toList());
                    listView3.getItems().addAll(items);
                } catch (Exception e) {
                    // Tratează excepțiile, de exemplu, afișează un mesaj de eroare
                    listView3.getItems().clear();
                }
                VBox hBox = new VBox(thirdVBox,fourVBox,fiveVBox);
                borderPane.setRight(hBox);
            }
        });
        HBox hBox=new HBox(refreshButton);

        refreshButton.setMinSize(50,50);
        refreshButton.setPadding(new Insets(10));
        refreshButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        borderPane.setBottom(hBox);

        Scene scene = new Scene(borderPane, 1200, 700);
        stage.setTitle("Inchiriere Masini!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws DuplicateEntityException, IOException, ClassNotFoundException {
        if (args.length > 0 && args[0].equals("run")) {
            Main.main();
        } else {
            launch(HelloApplication.class);
        }
    }
}