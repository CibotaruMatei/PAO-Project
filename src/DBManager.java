import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.TreeSet;

public class DBManager {
    private static DBManager instance = null;
    private Catalog catalog;

    private DBManager() { catalog = Catalog.getInstance(); }

    public static DBManager getInstance() {
        if(instance == null) instance = new DBManager();

        return instance;
    }

    private void readProfesori(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select * from profesori");

        while(rs.next()) {
            catalog.addProfesor(new Profesor(rs.getString("nume"), rs.getDate("ziNastere").toLocalDate()));
        }
    }
    private void readClase(Connection conn) throws ObjectNotFoundException, SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT c.nume nume, p.nume profesor FROM clase c LEFT JOIN profesori p ON p.ID = c.diriginteID;");

        while(rs.next()) {
            String nume = rs.getString("nume");
            String profesor = rs.getString("profesor");
            if(!rs.wasNull()) {
                catalog.addClasa(new Clasa(nume, catalog.findProfesor(profesor)));
            } else {
                catalog.addClasa(new Clasa(nume, null));
            }
        }
    }
    private void readElevi(Connection conn) throws SQLException, ObjectNotFoundException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT e.nume nume, e.ziNastere ziNastere, c.nume clasa FROM elevi e LEFT JOIN clase c ON c.ID = e.clasaID;");

        while(rs.next()) {
            String nume = rs.getString("nume");
            LocalDate ziNastere = rs.getDate("ziNastere").toLocalDate();
            String clasa = rs.getString("clasa");
            Clasa c = rs.wasNull() ? null : catalog.findClasa(clasa);
            Elev e = new Elev(nume, ziNastere, c);
            catalog.addElev(e);
            if(c != null) c.addElev(e);
        }
    }
    private void readMaterii(Connection conn) throws SQLException, ObjectNotFoundException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT c.nume clasa, m.nume nume, p.nume profesor, m.areTeza areTeza FROM materii m LEFT JOIN clase c ON m.clasaID = c.ID LEFT JOIN profesori p ON m.profesorID = p.ID;");

        while(rs.next()) {
            String clasa = rs.getString("clasa");
            Clasa c = rs.wasNull() ? null : catalog.findClasa(clasa);
            String profesor = rs.getString("profesor");
            Profesor p = rs.wasNull() ? null : catalog.findProfesor(profesor);
            String nume = rs.getString("nume");

            Materie m;
            if(rs.getBoolean("areTeza")) {
                m = new MaterieTeza(nume, p, c == null ? null : c.getElevi());
            } else {
                m = new Materie(nume, p, c == null ? new TreeSet<>() : c.getElevi());
            }
            if(c != null) c.addMaterie(m);
            catalog.addMaterie(m);
        }
    }

    public void readData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pao", "root", "root");
            readProfesori(conn);
            readClase(conn);
            readElevi(conn);
            readMaterii(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ObjectNotFoundException e) {
            System.out.println("Object not found!");
        } finally {
            System.out.println("Date citite!");
        }
    }
}
