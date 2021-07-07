import java.time.LocalDate;

public class Nota implements Comparable<Nota> {
    private int nota;
    private LocalDate date;
    private Elev elev;
    private Materie materie;

    public Nota(int i, Elev e, Materie m) {
        nota = i;
        date = LocalDate.now();
        elev = e;
        materie = m;
    }

    @Override
    public int compareTo(Nota o) {
        return date.compareTo(o.date);
    }

    public int getNota() {
        return nota;
    }
    public void setNota(int nota) {
        this.nota = nota;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Elev getElev() {
        return elev;
    }
    public void setElev(Elev elev) {
        this.elev = elev;
    }
    public Materie getMaterie() {
        return materie;
    }
    public void setMaterie(Materie materie) {
        this.materie = materie;
    }
}
