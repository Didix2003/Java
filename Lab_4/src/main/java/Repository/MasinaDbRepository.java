package Repository;

import Domain.Masina;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MasinaDbRepository extends MemoryRepository<Masina> implements IDbRepository<Masina> {
    private String JDBC_URL="jdbc:sqlite:masina.db";
    private final String dbLocation;


    private Connection connection=null;
    private List<Masina> data = new ArrayList<>();

    public MasinaDbRepository(String dbLocation) throws DuplicateEntityException {
        this.dbLocation = "jdbc:sqlite:"+dbLocation;
        openConnection();
        createTable();
        loadDataFromDatabase();
        //repository.initTable();
        //random100Masina();

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
            stmt.execute("CREATE TABLE IF NOT EXISTS masini(id int,marca varchar(400), model varchar(400));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initTable()throws DuplicateEntityException
    {
        List<Masina> masini=new ArrayList<>();
        masini.add(new Masina(1,"BMW","Seria5"));
        masini.add(new Masina(2,"Opel","Astra"));
        try(PreparedStatement stmt=connection.prepareStatement("INSERT INTO masini values (?,?,?);"))
        {
            for(Masina p:masini)
            {
                stmt.setInt(1,p.getId());
                stmt.setString(2,p.getMarca());
                stmt.setString(3,p.getModel());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loadDataFromDatabase() throws DuplicateEntityException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM masini;")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Masina m = new Masina(rs.getInt(1), rs.getString(2), rs.getString(3));
                super.add(m); // Adaugă în lista din memorie
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Masina> getAll() throws DuplicateEntityException {
        ArrayList<Masina> masini=new ArrayList<>();
        try(PreparedStatement stmt=connection.prepareStatement("SELECT * FROM masini;"))
        {
            ResultSet rs=stmt.executeQuery();
            while(rs.next()) {
                Masina m = new Masina(rs.getInt(1), rs.getString(2), rs.getString(3));
                masini.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return masini;
    }

    @Override
    public void add(Masina m)throws DuplicateEntityException {
        try{
            super.add(m);
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO masini values (?,?,?);")) {
                stmt.setInt(1, m.getId());
                stmt.setString(2, m.getMarca());
                stmt.setString(3, m.getModel());
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DuplicateEntityException("Nu am putut adauga masina" + e.getMessage());
            }


        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Masina masina)
    {
        try
        {
            super.update(masina);
            try (PreparedStatement stmt = connection.prepareStatement("UPDATE masini SET marca=?, model=? WHERE id=?;")) {
                stmt.setString(1, masina.getMarca());
                stmt.setString(2, masina.getModel());
                stmt.setInt(3, masina.getId());
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DuplicateEntityException("Nu am putut modifica masina" + e.getMessage());
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
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM masini where id=?;")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DuplicateEntityException("Nu am putut sterge masina" + e.getMessage());
            }
        } catch (IOException | DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

}
