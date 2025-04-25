package Oggetti;

public abstract class MaterialeBibliotecario implements Comparable<MaterialeBibliotecario> {
    protected String id;
    protected String titolo;
    protected boolean disponibile;

    public MaterialeBibliotecario(String id, String titolo, boolean disponibile) {
        this.id = id;
        this.titolo = titolo;
        this.disponibile = disponibile;
    }

    public String getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public boolean getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public String toString() {
        return id+","+titolo+","+disponibile;
    }

    @Override
    public int compareTo(MaterialeBibliotecario o) {
        return id.compareTo(o.id);
    }

}
