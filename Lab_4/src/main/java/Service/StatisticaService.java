package Service;

import Domain.StatisticaMasina;
import Domain.Inchiriere;
import Repository.DuplicateEntityException;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticaService {
    private final ServiceInchiriere incService;
    private final ServiceMasina carService;

    public StatisticaService(ServiceInchiriere incService, ServiceMasina carService) {
        this.incService = incService;
        this.carService = carService;
    }

    public List<StatisticaMasina> getMasiniCeleMaiDesInchiriate() throws DuplicateEntityException {
        return incService.getAll().stream()
                .collect(Collectors.groupingBy(Inchiriere::getIdmasina, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new StatisticaMasina(carService.find(entry.getKey()), entry.getValue().intValue(),entry.getValue().intValue()))
                .sorted(Comparator.comparingInt(StatisticaMasina::getNumarInchirieri).reversed())
                .collect(Collectors.toList());
    }
    public List<Pair<String, Long>> getLuniCuNumarInchirieriDescrescatoare() throws DuplicateEntityException {
        return incService.getAll().stream()
                .collect(Collectors.groupingBy(inc -> getMonthYear(inc.getData_inceput()), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> new Pair<>(String.format("%02d", Integer.parseInt(entry.getKey())), entry.getValue()))
                .collect(Collectors.toList());
    }

    private String getMonthYear(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return String.format("%02d", localDate.getMonthValue());
    }

    public List<StatisticaMasina> getMasiniInchiriateCelMaiMultTimp() throws DuplicateEntityException {
        Map<Integer, Long> durataTotalaPeMasina = incService.getAll().stream()
                .collect(Collectors.groupingBy(Inchiriere::getIdmasina,
                        Collectors.summingLong(inc -> getDurataInchiriere(inc.getData_inceput(), inc.getData_final()))));

        return durataTotalaPeMasina.entrySet().stream()
                .map(entry -> new StatisticaMasina(carService.find(entry.getKey()), 0, entry.getValue().intValue()))
                .sorted(Comparator.comparingInt(StatisticaMasina::getNumarZileInchiriate).reversed())
                .collect(Collectors.toList());
    }

    private long getDurataInchiriere(String dataInceput, String dataFinal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(dataInceput, formatter);
        LocalDate endDate = LocalDate.parse(dataFinal, formatter);
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}