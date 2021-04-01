import java.util.ArrayList;
import java.util.HashMap;

public class Materie {
    Profesor profesor;
    HashMap<Elev, ArrayList<Integer>> note;

    Materie() {
        this((Profesor) null);
    }

    Materie(String s) {
        this(Catalog.getInstance().findProfesor(s));
    }

    Materie(Profesor p) {
        profesor = p;
    }

    void addNota(int i, String s) {
        addNota(i, Catalog.getInstance().findElev(s));
    }

    void addNota(int i, Elev e) {
        note.get(e).add(i);
    }
}
