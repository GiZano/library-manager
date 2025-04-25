package Manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFileManager {

    /**
     * Lettura del file specificato come stringa singola
     * @param filePath posizione del file da leggere
     * @return file sottoforma di stringa
     * @throws IOException errore nell'apertura/lettura del file
     */
    public static String readFileAsString(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    /**
     * Scrittura di una stringa su un file
     * @param filePath posizione del file su cui scrivere
     * @param content stringa da scrivere sul file
     * @throws IOException errore nella scrittura/apertura del file
     */
    public static void writeStringToFile(String filePath, String content) throws IOException {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(content);
        }
    }

    /**
     * Aggiunta in coda di testo ad un file (utilizzato per il logging)
     * @param filePath percorso del file su cui aggiungere dati
     * @param content dati da aggiungere al file
     * @throws IOException errore nell'apertura/scrittura su file
     */
    public static void appendStringToFile(String filePath, String content) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(content);
        }
    }

    /**
     * Eliminazione di file se esistente
     * @param filePath percorso del file da eliminare
     * @throws IOException errore durante l'eliminazione
     */
    public static void deleteFile(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }
}
