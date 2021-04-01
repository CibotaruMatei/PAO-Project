public class Persoana implements Comparable<Persoana> {
    final String nume;
    int varsta;

    Persoana(String nume, int varsta) {
        this.nume = nume;
        this.varsta = varsta;
    }

    Persoana() {
        this("", 0);
    }

    @Override
    public int compareTo(Persoana o) {
        return nume.compareTo(o.nume);
    }
}
