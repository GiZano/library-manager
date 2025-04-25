package Oggetti;

public class Libro extends MaterialeBibliotecario{
    private String autore;
    private String isbn;

    public Libro(String id, String titolo, boolean disponibile, String autore, String isbn) {
        super(id, titolo, disponibile);
        this.autore = autore;
        this.isbn = isbn;
    }

    public String getIsbn(){ return isbn; }

    @Override
    public String toString() {
        return "Libro,"+super.toString()+","+autore+","+isbn;
    }
}
