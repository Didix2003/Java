package Domain;

public class InchiriereFactory implements IEntityFactory<Inchiriere>
{

    @Override
    public Inchiriere createEntity(String line) {
        int id= Integer.parseInt(line.split(",")[0]);
        int id_masina= Integer.parseInt(line.split(",")[1]);
        String data_inceput=line.split(",")[2];
        String data_final=line.split(",")[3];

        return new Inchiriere(id,id_masina,data_inceput,data_final);
    }

    @Override
    public String toString(Inchiriere object) {
        return object.getId()+","+object.getIdmasina()+","+object.getData_inceput()+"," +object.getData_final();
    }
}
