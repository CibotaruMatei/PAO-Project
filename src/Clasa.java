import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Clasa {
    private List<Materie> materii;
    private SortedSet<Elev> elevi;
    private Profesor diriginte;
    private String nume;

    Clasa(String s) {
        nume = s;
        materii = new ArrayList<>();
        elevi = new TreeSet<>();
        diriginte = null;
    }

    public void addElev(String s, int v) {
        elevi.add(new Elev(s, v));
    }

    public boolean isElev(String s) {
        for(Elev e : elevi)
            if (e.nume.equals(s))
                return true;
        return false;
    }

    void addMaterie() {

    }

    void addMaterie(String s) {

    }
}
