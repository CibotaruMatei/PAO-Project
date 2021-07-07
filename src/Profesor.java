import java.time.LocalDate;

public class Profesor extends Persoana {
    public Profesor(String nume, String ziNastere) {
        super(nume, ziNastere);
    }

    public Profesor(String nume, LocalDate ziNastere) {
        super(nume, ziNastere);
    }

    public Profesor() {
        super();
    }

    @Override
    public String toString() {
        return "Profesor " + nume + " cu ziua de nastere " + ziNastere.toString();
    }
}
