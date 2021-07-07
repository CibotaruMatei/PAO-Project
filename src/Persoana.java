import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Persoana implements Comparable<Persoana> {
    protected final String nume;
    protected LocalDate ziNastere;

    public Persoana(String nume, String ziNastere) {
        this.nume = nume;
        this.ziNastere = LocalDate.parse(ziNastere);
    }

    public Persoana(String nume, LocalDate ziNastere) {
        this.nume = nume;
        this.ziNastere = ziNastere;
    }

    public Persoana() {
        this("", "2000-01-01");
    }

    @Override
    public int compareTo(Persoana o) {
        return nume.compareTo(o.nume);
    }

    public String getNume() {
        return nume;
    }

    public LocalDate getZiNastere() {
        return ziNastere;
    }

    public int getVarsta() {
        return (int) ChronoUnit.YEARS.between(ziNastere, LocalDate.now());
    }
}
