import java.util.*;

public class Materie {
    private final String nume;
    private Profesor profesor;
    private HashMap<Elev, ArrayList<Nota>> note;

    public Materie(String s, Profesor p, SortedSet<Elev> elevi) {
        nume = s;
        profesor = p;
        note = new HashMap<>();
        for(Elev e : elevi) {
            note.put(e, new ArrayList<>());
        }
    }

    public HashMap<Elev, ArrayList<Nota>> getNote() {
        return note;
    }

    public void addNota(int i, Elev e) {
        note.get(e).add(new Nota(i, e, this));
    }

    public double getMedie(Elev e) {
        double medie = 0;
        for(Nota n : note.get(e)) {
            medie += n.getNota();
        }
        return medie / note.get(e).size();
    }

    public String getNume() {
        return nume;
    }
    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return nume + " este predata de " + profesor.getNume();
    }
}
