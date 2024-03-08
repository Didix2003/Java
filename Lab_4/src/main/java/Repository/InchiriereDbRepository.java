package Repository;

import Domain.Inchiriere;
import Domain.Masina;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class InchiriereDbRepository extends MemoryRepository<Inchiriere> implements IDbRepository<Inchiriere>{
    private String JDBC_URL="jdbc:sqlite:inchiriere.db";
    private final String dbLocation;

    private Connection connection=null;
    private List<Inchiriere> data = new ArrayList<>();

    public InchiriereDbRepository(String dbLocation) throws DuplicateEntityException {
        this.dbLocation = "jdbc:sqlite:"+dbLocation;
        openConnection();
        createTable();
        loadDataFromDatabase();
        //initTable();

    }

    public void openConnection()
    {
        SQLiteDataSource ds=new SQLiteDataSource();
        ds.setUrl(dbLocation);

        try {
            if (connection==null||connection.isClosed())
            {
                connection=ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection()
    {
        if (connection!=null)
        {
            try
            {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable()
    {
        try(final Statement stmt=connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS inchiriere(id int, idmasina int,data_inceput varchar(400), data_final varchar(400));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initTable()
    {
        List<Inchiriere> inchirieres=new ArrayList<>();
        inchirieres.add(new Inchiriere(1,1,"12/07/2020","15/07/2020"));
        inchirieres.add(new Inchiriere(2,2,"20/08/2021","25/08/2021"));
        try(PreparedStatement stmt=connection.prepareStatement("INSERT INTO inchiriere values (?,?,?,?);"))
        {
            for(Inchiriere p:inchirieres)
            {
                stmt.setInt(1,p.getId());
                stmt.setInt(2,p.getIdmasina());
                stmt.setString(3,p.getData_inceput());
                stmt.setString(4,p.getData_final());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromDatabase() throws DuplicateEntityException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM inchiriere;")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inchiriere m=new Inchiriere(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4));
                super.add(m); // Adaugă în lista din memorie
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Inchiriere> getAll()
    {
        ArrayList<Inchiriere> inchirieres=new ArrayList<>();
        try(PreparedStatement stmt=connection.prepareStatement("SELECT * FROM inchiriere;"))
        {
            ResultSet rs=stmt.executeQuery();
            while(rs.next())
            {
                Inchiriere m=new Inchiriere(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4));
                inchirieres.add(m);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inchirieres;
    }
    public void add(Inchiriere m)
    {
        try{
            super.add(m);
            try(PreparedStatement stmt=connection.prepareStatement("INSERT INTO inchiriere values (?,?,?,?);"))
            {
                stmt.setInt(1,m.getId());
                stmt.setInt(2,m.getIdmasina());
                stmt.setString(3,m.getData_inceput());
                stmt.setString(4,m.getData_final());
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Inchiriere inchiriere)
    {
        try
        {
            super.update(inchiriere);
            try (PreparedStatement stmt = connection.prepareStatement("UPDATE inchiriere SET data_final=?, data_inceput=?, idmasina=? WHERE id=?;")) {
                stmt.setString(1, inchiriere.getData_final());
                stmt.setString(2, inchiriere.getData_inceput());
                stmt.setInt(3, inchiriere.getIdmasina());
                stmt.setInt(4, inchiriere.getId());
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DuplicateEntityException("Nu am putut modifica aceasta inchiriere" + e.getMessage());
            }
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }
    public void remove(int id)
    {
        try
        {
            super.remove(id);
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM inchiriere where id=?;")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DuplicateEntityException("Nu am putut sterge aceasta inchiriere" + e.getMessage());
            }
        } catch (IOException | DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }


}
