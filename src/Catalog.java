import java.util.*;

public class Catalog {
    private static Catalog instance;
    private List<Clasa> clase;
    private SortedSet<Elev> elevi;
    private SortedSet<Profesor> profesori;

    private Catalog() {
        clase = new ArrayList<>();
        elevi = new TreeSet<>();
        profesori = new TreeSet<>();
    }

    public static Catalog getInstance() {
        if(instance == null)
            instance = new Catalog();
        return instance;
    }

    public Elev findElev(String s) {
        for(Elev e : elevi)
            if(s.equals(e.nume))
                return e;
        return null;
    }

    public Profesor findProfesor(String s) {
        for(Profesor p : profesori)
            if(s.equals(p.nume))
                return p;
        return null;
    }
}
