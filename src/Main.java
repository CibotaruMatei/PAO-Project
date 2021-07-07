import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

@SuppressWarnings("StatementWithEmptyBody")
public class Main {
    private static void meniuProfesori(Scanner scanner) {
        Catalog catalog = Catalog.getInstance();
        System.out.println("Selectati comanda:\n0: Iesiti din meniu\n1. Listati profesorii\n2. Adaugati profesor\n3. Stergeti un profesor\n");
        int cmd = scanner.nextInt();

        while(cmd != 0) {
            switch (cmd) {
                case 1 -> {
                    Audit.getInstance().addTask("Listati profesorii");
                    for (Profesor p : catalog.getProfesori()) {
                        System.out.println(p.toString());
                    }
                }
                case 2 -> {
                    Audit.getInstance().addTask("Adaugati profesor");
                    System.out.println("Scrieti numele si data de nastere in formatul YYYY-MM-DD:");
                    String nume = scanner.nextLine().strip();
                    String ziNastere = scanner.nextLine().strip();
                    catalog.addProfesor(new Profesor(nume, ziNastere));
                }
                case 3 -> {
                    Audit.getInstance().addTask("Stergeti profesor");
                    System.out.println("Scrieti numele:");
                    String nume = scanner.nextLine().strip();
                    try {
                        Profesor p = catalog.findProfesor(nume);
                        for(Materie m : catalog.getMaterii()) {
                            if(m.getProfesor() == p) m.setProfesor(null);
                        }
                        for(Clasa c : catalog.getClase()) {
                            if(c.getDiriginte() == p) c.setDiriginte(null);
                        }
                        catalog.getProfesori().remove(p);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Profesorul nu a fost gasit! Inapoi la meniu");
                    }
                }
                default -> System.out.println("Invalid.");
            }
            System.out.println("Urmatoarea comanda:");
            cmd = scanner.nextInt();
        }
        System.out.println("Selectati comanda:\n0: Opriti programul\n1. Submeniu profesori\n2. Submeniu elevi\n3. Submeniu clase\n4. Submeniu materii\n");
    }

    private static void meniuElevi(Scanner scanner) {
        Catalog catalog = Catalog.getInstance();
        System.out.println("Selectati comanda:\n0: Iesiti din meniu\n1. Listati elevii\n2. Adaugati elev\n3. Stergeti un elev\n");
        int cmd = scanner.nextInt();

        while (cmd != 0) {
            switch (cmd) {
                case 1 -> {
                    Audit.getInstance().addTask("Listati elevii");
                    for (Elev e : catalog.getElevi()) {
                        System.out.println(e.toString());
                    }
                }
                case 2 -> {
                    Audit.getInstance().addTask("Adaugati elev");
                    System.out.println("Scrieti numele, clasa si data de nastere in formatul YYYY-MM-DD:");
                    String nume = scanner.nextLine().strip();
                    String clasa = scanner.nextLine().strip();
                    String ziNastere = scanner.nextLine().strip();
                    try {
                        catalog.addElev(new Elev(nume, ziNastere, catalog.findClasa(clasa)));
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Clasa invalida! Inapoi la meniu");
                    }
                }
                case 3 -> {
                    Audit.getInstance().addTask("Stergeti elev");
                    System.out.println("Scrieti numele:");
                    String nume = scanner.nextLine().strip();
                    try {
                        Elev e = catalog.findElev(nume);
                        for(Materie m : e.getClasa().getMaterii()) {
                            m.getNote().remove(e);
                            if(m instanceof MaterieTeza) ((MaterieTeza) m).getTeza().remove(e);
                        }
                        e.getClasa().getElevi().remove(e);
                        catalog.getElevi().remove(e);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Elevul nu a fost gasit! Inapoi la meniu");
                    }
                }
                default -> System.out.println("Invalid.");
            }
            System.out.println("Urmatoarea comanda:");
            cmd = scanner.nextInt();
        }
        System.out.println("Selectati comanda:\n0: Opriti programul\n1. Submeniu profesori\n2. Submeniu elevi\n3. Submeniu clase\n4. Submeniu materii\n");
    }

    private static void meniuClase(Scanner scanner) {
        Catalog catalog = Catalog.getInstance();
        System.out.println("Selectati comanda:\n0: Iesiti din meniu\n1. Listati clasele\n2. Adaugati clasa\n3. Stergeti o clasa\n");
        int cmd = scanner.nextInt();

        while(cmd != 0) {
            switch (cmd) {
                case 1 -> {
                    Audit.getInstance().addTask("Listati clasele");
                    for (Clasa c : catalog.getClase()) {
                        System.out.println(c.toString());
                    }
                }
                case 2 -> {
                    Audit.getInstance().addTask("Adaugati clasa");
                    System.out.println("Scrieti numele si dirigintele:");
                    String nume = scanner.nextLine().strip();
                    String profesor = scanner.nextLine().strip();
                    try {
                        catalog.addClasa(new Clasa(nume, catalog.findProfesor(profesor)));
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Profesorul nu a fost gasit! Inapoi la meniu");
                    }
                }
                case 3 -> {
                    Audit.getInstance().addTask("Stergeti clasa");
                    System.out.println("Atentie! I se vor sterge toate materiile. Sunteti sigur? (y/n)");
                    String response;
                    while(!Arrays.asList(new String[]{"y", "n"}).contains(response = scanner.nextLine().strip().toLowerCase())) {}
                    if(response.equals("n")) break;

                    System.out.println("Scrieti numele:");
                    String nume = scanner.nextLine().strip();
                    try {
                        Clasa c = catalog.findClasa(nume);
                        for(Elev e : c.getElevi()) {
                            e.setClasa(null);
                        }
                        c.getMaterii().clear();
                        catalog.getClase().remove(c);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Clasa nu a fost gasita! Inapoi la meniu");
                    }
                }
                default -> System.out.println("Invalid.");
            }
            System.out.println("Urmatoarea comanda:");
            cmd = scanner.nextInt();
        }
        System.out.println("Selectati comanda:\n0: Opriti programul\n1. Submeniu profesori\n2. Submeniu elevi\n3. Submeniu clase\n4. Submeniu materii\n");
    }

    private static void meniuMaterii(Scanner scanner) {
        Catalog catalog = Catalog.getInstance();
        System.out.println("Selectati comanda:\n0: Iesiti din meniu\n1. Listati materiile\n2. Adaugati materie\n3. Stergeti o materie\n");
        int cmd = scanner.nextInt();

        while(cmd != 0) {
            switch (cmd) {
                case 1 -> {
                    Audit.getInstance().addTask("Listati materiile");
                    for (Clasa c : catalog.getClase()) {
                        System.out.println(c.getNume() + ":");
                        for(Materie m : c.getMaterii()) {
                            System.out.println(m.toString());
                        }
                    }
                }
                case 2 -> {
                    Audit.getInstance().addTask("Adaugati materie");
                    System.out.println("Scrieti clasa, numele, profesorul si daca are teza sau nu (y/n):");
                    String clasa = scanner.nextLine().strip();
                    String nume = scanner.nextLine().strip();
                    String profesor = scanner.nextLine().strip();
                    String response;
                    try {
                        Materie m;
                        while(!Arrays.asList(new String[]{"y", "n"}).contains(response = scanner.nextLine().strip().toLowerCase())) {}
                        if(response.equals("n")) {
                            m = new Materie(nume, catalog.findProfesor(profesor), catalog.findClasa(clasa).getElevi());
                        } else {
                            m = new MaterieTeza(nume, catalog.findProfesor(profesor), catalog.findClasa(clasa).getElevi());
                        }

                        catalog.addMaterie(m);
                        catalog.findClasa(clasa).addMaterie(m);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Clasa nu a fost gasita! Inapoi la meniu");
                    }
                }
                case 3 -> {
                    Audit.getInstance().addTask("Stergeti materie");
                    System.out.println("Atentie! I se vor sterge toate notele. Sunteti sigur? (y/n)");
                    String response;
                    while(!Arrays.asList(new String[]{"y", "n"}).contains(response = scanner.nextLine().strip().toLowerCase())) {}
                    if(response.equals("n")) break;

                    System.out.println("Scrieti numele:");
                    String nume = scanner.nextLine().strip();
                    try {
                        Materie m = catalog.findMaterie(nume);
                        for(ArrayList<Nota> n : m.getNote().values()) {
                            n.clear();
                        }
                        m.getNote().clear();
                        if(m instanceof MaterieTeza) ((MaterieTeza) m).getTeza().clear();
                        catalog.getMaterii().remove(m);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Materia nu a fost gasita! Inapoi la meniu");
                    }
                }
                default -> System.out.println("Invalid.");
            }
            System.out.println("Urmatoarea comanda:");
            cmd = scanner.nextInt();
        }
        System.out.println("Selectati comanda:\n0: Opriti programul\n1. Submeniu profesori\n2. Submeniu elevi\n3. Submeniu clase\n4. Submeniu materii\n");
    }

    private static void meniuPrincipal() {
        System.out.println("Selectati comanda:\n0: Opriti programul\n1. Submeniu profesori\n2. Submeniu elevi\n3. Submeniu clase\n4. Submeniu materii\n");
        Scanner scanner = new Scanner(System.in);
        int cmd = scanner.nextInt();

        while(cmd != 0) {
            switch (cmd) {
                case 1 -> meniuProfesori(scanner);
                case 2 -> meniuElevi(scanner);
                case 3 -> meniuClase(scanner);
                case 4 -> meniuMaterii(scanner);
                default -> System.out.println("Invalid.");
            }
            cmd = scanner.nextInt();
        }
    }

    public static void main(String[] args) {
        //DataManager dm = DataManager.getInstance();
        DBManager db = DBManager.getInstance();
        db.readData();
        meniuPrincipal();
        //dm.readData();
        //meniuPrincipal();

        //dm.writeData();
    }
}
