import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Clasa {
    private List<Materie> materii;
    private SortedSet<Elev> elevi;
    private Profesor diriginte;
    private final String nume;

    Clasa(String s, Profesor d) {
        nume = s;
        materii = new ArrayList<>();
        elevi = new TreeSet<>();
        diriginte = d;
    }

    public void addMaterie(Materie m) {
        materii.add(m);
    }
    public Materie findMaterie(String s) throws ObjectNotFoundException {
        for(Materie m : materii)
            if(s.equals(m.getNume()))
                return m;
        throw new ObjectNotFoundException(s);
    }
    public List<Materie> getMaterii() {
        return materii;
    }

    public void addElev(Elev e) {
        elevi.add(e);
        for(Materie m : materii) {
            m.getNote().put(e, null);
        }
    }
    public String getNume() {
        return nume;
    }
    public SortedSet<Elev> getElevi() {
        return elevi;
    }

    public Profesor getDiriginte() {
        return diriginte;
    }
    public void setDiriginte(Profesor diriginte) {
        this.diriginte = diriginte;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(nume + ": diriginte " + diriginte.getNume() + "\nElevi:\n");
        for(Elev e : elevi) {
            result.append(e.getNume()).append("\n");
        }
        result.append("Materii:\n");
        for(Materie m : materii) {
            result.append(m.getNume()).append("\n");
        }
        return result.toString();
    }
}
