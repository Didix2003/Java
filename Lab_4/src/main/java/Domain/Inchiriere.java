package Domain;

import java.security.PublicKey;

public class Inchiriere extends Entity {
    private int idmasina;
    private String data_inceput;
    public String data_final;
    public Inchiriere(int id,int idmasina, String data_inceput, String data_final) {
        super(id);
        this.idmasina=idmasina;
        this.data_inceput=data_inceput;
        this.data_final=data_final;
    }
    public int getIdmasina(){return idmasina;}
    public String getData_inceput()
    {
        return data_inceput;
    }
    public String getData_final()
    {
        return data_final;
    }
    public void setData_inceput(String data_inceput) {
        this.data_inceput = data_inceput;
    }
    public void setIdmasina(int idmasina){this.idmasina=idmasina;}
    public void setData_final(String data_final) {
        this.data_final = data_final;
    }
    public String toString()
    {
        return "Id_inchiriere: "+id+", Id_Masina: "+idmasina+", data_inceput: "+data_inceput+" , data_final: " +data_final;
    }
}
