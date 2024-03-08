package Domain;

public class StatisticaMasina {
    private Masina masina;
    private int numarInchirieri;
    private final int numarZileInchiriate;

    public StatisticaMasina(Masina masina, int numarInchirieri, int numarZileInchiriate) {
        this.masina = masina;
        this.numarInchirieri = numarInchirieri;
        this.numarZileInchiriate = numarZileInchiriate;
    }

    public Masina getMasina() {
        return masina;
    }
    public int getNumarInchirieri() {
        return numarInchirieri;
    }
    public int getNumarZileInchiriate() {
        return numarZileInchiriate;
    }

    @Override
    public String toString() {
        return "StatisticaMasina{" +
                "masina=" + masina +
                ", numarInchirieri=" + numarInchirieri +
                '}';
    }
}