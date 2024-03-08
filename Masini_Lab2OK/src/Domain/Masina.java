package Domain;

import  Domain.Entity;

public class Masina extends Entity
{
    private static final long serialVersionUID = 1000L;
    private String marca;
    private String model;
    public Masina(int id,String marca,String model) {
        super(id);
        this.marca=marca;
        this.model=model;
    }
    public String getModel()
    {
        return model;
    }
    public String getMarca()
    {
        return marca;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String toString()
    {
        return "ID: "+id+", marca: "+marca+" , model: " +model;
    }
}
