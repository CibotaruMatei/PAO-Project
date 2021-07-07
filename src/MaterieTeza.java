import java.util.*;

public class MaterieTeza extends Materie {
    private HashMap<Elev, Nota> teza;

    public MaterieTeza(String s, Profesor p, SortedSet<Elev> elevi) {
        super(s, p, elevi);
        teza = new HashMap<>();
        for(Elev e : elevi) {
            teza.put(e, null);
        }
    }

    public HashMap<Elev, Nota> getTeza() {
        return teza;
    }

    @Override
    public double getMedie(Elev e) {
        return (super.getMedie(e) * 3 + teza.get(e).getNota()) / 4;
    }
}
