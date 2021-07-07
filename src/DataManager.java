import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataManager {
    private static DataManager instance = null;
    private Catalog catalog;

    private DataManager() { catalog = Catalog.getInstance(); }

    public static DataManager getInstance() {
        if(instance == null) instance = new DataManager();

        return instance;
    }

    public void readProfesori() throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(Files.newBufferedReader(Paths.get("src/data/profesori.csv")));
        String[] record;

        while ((record = reader.readNext()) != null) {
            catalog.addProfesor(new Profesor(record[0], record[1]));
        }
    }
    public void readClase() throws IOException, CsvValidationException, ObjectNotFoundException {
        CSVReader reader = new CSVReader(Files.newBufferedReader(Paths.get("src/data/clase.csv")));
        String[] record;

        while ((record = reader.readNext()) != null) {
            if(!record[1].equals("null")) {
                catalog.addClasa(new Clasa(record[0], catalog.findProfesor(record[1])));
            } else {
                catalog.addClasa(new Clasa(record[0], null));
            }
        }
    }
    public void readElevi() throws IOException, CsvValidationException, ObjectNotFoundException {
        CSVReader reader = new CSVReader(Files.newBufferedReader(Paths.get("src/data/elevi.csv")));
        String[] record;

        while ((record = reader.readNext()) != null) {
            Clasa c = record[2].equals("null") ? null : catalog.findClasa(record[2]);
            Elev e = new Elev(record[0], record[1], c);
            catalog.addElev(e);
            if(c != null) c.addElev(e);
        }
    }
    public void readMaterii() throws IOException, CsvValidationException, ObjectNotFoundException {
        CSVReader reader = new CSVReader(Files.newBufferedReader(Paths.get("src/data/materii.csv")));
        String[] record;

        while ((record = reader.readNext()) != null) {
            Clasa clasa = catalog.findClasa(record[0]);
            Materie m;
            if(record[3].equals("True")) {
                m = new MaterieTeza(record[1], catalog.findProfesor(record[2]), clasa.getElevi());
            } else {
                m = new Materie(record[1], catalog.findProfesor(record[2]), clasa.getElevi());
            }
            clasa.addMaterie(m);
            catalog.addMaterie(m);
        }
    }

    public void readData() {
        try {
            readProfesori();
            readClase();
            readElevi();
            readMaterii();
        } catch (IOException e) {
            System.out.println("CSV file not found!");
        } catch (CsvValidationException e) {
            System.out.println("CSV file corrupted!");
        } catch (ObjectNotFoundException e) {
            System.out.println("Object not found: " + e);
        } finally {
            System.out.println("Date citite!");
        }
    }

    private void writeProfesori() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("src/data/profesori.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);
        //csvWriter.writeNext(new String[] {"nume","ziNastere"});

        for(Profesor p : catalog.getProfesori()) {
            csvWriter.writeNext(new String[]{p.getNume(), p.getZiNastere().toString()});
        }

        csvWriter.close();
        writer.close();
    }
    private void writeClase() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("src/data/clase.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);
        //csvWriter.writeNext(new String[] {"nume", "diriginte"});

        for(Clasa c : catalog.getClase()) {
            csvWriter.writeNext(new String[]{c.getNume(), c.getDiriginte().getNume()});
        }

        csvWriter.close();
        writer.close();
    }
    private void writeElevi() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("src/data/elevi.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);
        //csvWriter.writeNext(new String[] {"nume", "ziNastere", "clasa"});

        for(Elev e : catalog.getElevi()) {
            csvWriter.writeNext(new String[] {e.getNume(), e.getZiNastere().toString(), e.getClasa().getNume()});
        }

        csvWriter.close();
        writer.close();
    }
    private void writeMaterii() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("src/data/materii.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);
        //csvWriter.writeNext(new String[] {"clasa", "nume", "profesor", "areTeza"});

        for(Clasa c : catalog.getClase()) {
            for(Materie m : c.getMaterii()) {
                csvWriter.writeNext(new String[] {c.getNume(), m.getNume(), m.getProfesor() == null ? "null" : m.getProfesor().getNume(), m instanceof MaterieTeza ? "True" : "False"});
            }
        }

        csvWriter.close();
        writer.close();
    }

    public void writeData() {
        try {
            writeProfesori();
            writeClase();
            writeElevi();
            writeMaterii();
            Audit.getInstance().writeTasks();
        } catch (IOException e) {
            System.out.println("CSV file not found!");
        } finally {
            System.out.println("Date scrise!");
        }
    }
}
