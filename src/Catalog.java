import java.util.*;

public class Catalog {
    private static Catalog instance;
    private List<Clasa> clase;
    private List<Materie> materii;
    private SortedSet<Elev> elevi;
    private SortedSet<Profesor> profesori;

    private Catalog() {
        clase = new ArrayList<>();
        materii = new ArrayList<>();
        elevi = new TreeSet<>();
        profesori = new TreeSet<>();
    }

    public static Catalog getInstance() {
        if(instance == null)
            instance = new Catalog();
        return instance;
    }

    public Elev findElev(String s) throws ObjectNotFoundException {
        for(Elev e : elevi)
            if(s.equals(e.getNume()))
                return e;
        throw new ObjectNotFoundException(s);
    }
    public Profesor findProfesor(String s) throws ObjectNotFoundException {
        for(Profesor p : profesori)
            if(s.equals(p.getNume()))
                return p;
        throw new ObjectNotFoundException(s);
    }
    public Clasa findClasa(String s) throws ObjectNotFoundException {
        for(Clasa c : clase)
            if(s.equals(c.getNume()))
                return c;
        throw new ObjectNotFoundException(s);
    }
    public Materie findMaterie(String s) throws ObjectNotFoundException {
        for(Materie m : materii)
            if(s.equals(m.getNume()))
                return m;
        throw new ObjectNotFoundException(s);
    }

    public void addElev(Elev e) {elevi.add(e);}
    public void addProfesor(Profesor p) {profesori.add(p);}
    public void addClasa(Clasa c) {clase.add(c);}
    public void addMaterie(Materie m) {materii.add(m);}

    public SortedSet<Elev> getElevi() {
        return elevi;
    }
    public SortedSet<Profesor> getProfesori() {
        return profesori;
    }
    public List<Clasa> getClase() {
        return clase;
    }
    public List<Materie> getMaterii() {
        return materii;
    }
}
