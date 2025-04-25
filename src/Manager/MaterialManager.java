package Manager;

import Oggetti.Libro;
import Oggetti.MaterialeBibliotecario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MaterialManager {

    /**
     * Salvataggio dei dati nel file specificato nei parametri
     * @param pathDati path del file su cui salvare i dati
     * @param catalogo lista dei materiali da salvare
     * @throws IOException errore nell'apertura del file su cui scrivere
     */
    public static void salvaDati(String pathDati, ArrayList<MaterialeBibliotecario> catalogo) throws IOException {
        StringBuilder dati = new StringBuilder();
        for(MaterialeBibliotecario m : catalogo){
            dati.append(m.toString()).append("\n");
        }
        if(!dati.isEmpty()){
            dati.deleteCharAt(dati.length()-1);
        }
        TextFileManager.writeStringToFile(pathDati, dati.toString());
    }

    /**
     * Ricerca dicotomica di un materiale specificato nei parametri
     * @param m materiale da trovare
     * @param catalogo lista dei materiali
     * @return indice del materiale trovato o -1
     */
    public static int findMateriale(MaterialeBibliotecario m, ArrayList<MaterialeBibliotecario> catalogo) {
        Collections.sort(catalogo);
        int start = 0;
        int end = catalogo.size()-1;

        while(start <= end){
            int mid = start + (end-start)/2;
            int compare = catalogo.get(mid).compareTo(m);
            if(compare == 0){
                return mid;
            }
            else if(compare < 0){
                start = mid+1;
            }
            else{
                end = mid-1;
            }
        }
        return -1;
    }

    /**
     * Overload della funzione precedenete --> Ricerca dicotomica di un materiale specificato tramite id
     * @param id id del materiale da trovare
     * @param catalogo lista dei materiali
     * @return indice del materiale trovato o -1
     */
    public static int findMateriale(String id, ArrayList<MaterialeBibliotecario> catalogo) {
        Collections.sort(catalogo);
        int start = 0;
        int end = catalogo.size()-1;

        while(start <= end){
            int mid = start + (end-start)/2;
            int compare = catalogo.get(mid).getId().compareTo(id);
            if(compare == 0){
                return mid;
            }
            else if(compare < 0){
                start = mid+1;
            }
            else{
                end = mid-1;
            }
        }
        return -1;
    }

    /**
     * Ricerca dicotomica di un libro specificato nei parametri
     * @param l libro da trovare
     * @param libri lista dei libri
     * @return indice del libro o -1
     */
    public static int findLibro(Libro l, ArrayList<Libro> libri) {
        Collections.sort(libri);
        int start = 0;
        int end = libri.size()-1;

        while(start <= end){
            int mid = start + (end-start)/2;
            int compare = libri.get(mid).getIsbn().compareTo(l.getIsbn());
            if(compare == 0){
                return mid;
            }
            else if(compare < 0){
                start = mid+1;
            }
            else{
                end = mid-1;
            }
        }
        return -1;
    }

    /**
     * Separazione dei libri dal resto del materiale
     * @param catalogo lista di tutto il materiale
     * @return lista con all'interno i libri presenti nel catalogo
     */
    public static ArrayList<Libro> dividiLibriDaDvd(ArrayList<MaterialeBibliotecario> catalogo){
        ArrayList<Libro> libri = new ArrayList<>();
        for(MaterialeBibliotecario m : catalogo){
            if(m instanceof Libro){
                libri.add((Libro)m);
            }
        }
        return libri;
    }


}
