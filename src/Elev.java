import java.time.LocalDate;

public class Elev extends Persoana {
    private Clasa clasa;

    public Elev(String nume, String ziNastere, Clasa c) {
        super(nume, ziNastere);
        clasa = c;
    }

    public Elev(String nume, LocalDate ziNastere, Clasa c) {
        super(nume, ziNastere);
        clasa = c;
    }

    public Clasa getClasa() {
        return clasa;
    }
    public void setClasa(Clasa clasa) {
        this.clasa = clasa;
    }

    @Override
    public String toString() {
        return "Elev " + nume + (clasa == null ? "" : (" al clasei " + clasa.getNume())) + " cu ziua de nastere " + ziNastere.toString();
    }
}
