import com.opencsv.CSVWriter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Audit {
    private static Audit instance;
    private ArrayList<Pair<String, LocalDateTime>> tasks;

    private Audit() {
        tasks = new ArrayList<>();
    }

    public static Audit getInstance() {
        if(instance == null) instance = new Audit();
        return instance;
    }

    public void addTask(String s) {
        tasks.add(new ImmutablePair<>(s, LocalDateTime.now()));
    }

    public void writeTasks() {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("src/data/audit.csv"));
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(new String[]{"nume_actiune", "timestamp"});

            for(Pair<String, LocalDateTime> p : tasks) {
                csvWriter.writeNext(new String[]{p.getKey(), p.getValue().toString()});
            }

            csvWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
