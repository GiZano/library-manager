package Oggetti;

public class Dvd extends MaterialeBibliotecario{
    private String regista;
    private int durataMinuti;

    public Dvd(String id, String titolo, boolean disponibile, String regista, int durataMinuti) {
        super(id, titolo, disponibile);
        this.regista = regista;
        this.durataMinuti = durataMinuti;
    }

    @Override
    public String toString() {
        return "Dvd,"+super.toString()+","+regista+","+durataMinuti;
    }
}
