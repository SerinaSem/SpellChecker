import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MainCorrecteur {

    // lecture d’un fichier texte (1 mot par ligne)
    public static List<String> lireFichier(String chemin) throws IOException {
        return Files.readAllLines(Paths.get(chemin));
    }

    public static void main(String[] args) throws Exception {
        long debut = System.nanoTime();

        List<String> dico = lireFichier("dico.txt");
        TrigramIndex index = new TrigramIndex();
        index.construire(dico);

        long t1 = System.nanoTime();
        System.out.println("Dictionnaire chargé : " + dico.size() + " mots");
        System.out.printf("Temps de construction : %.3f s%n", (t1 - debut) / 1e9);

        List<String> fautes = lireFichier("fautes.txt");
        System.out.println("Fichier de fautes : " + fautes.size() + " mots");

        for (String motFautif : fautes) {
            String mot = motFautif.trim().toLowerCase();

            if (index.contient(mot)) {
                System.out.println(mot + " --> correct");
            } else {
                List<String> candidats = index.candidats(mot, 100);
                candidats.sort(Comparator.comparingInt(c -> Levenshtein.distance(mot, c)));

                System.out.println("\nMot fautif : " + mot);
                for (int i = 0; i < Math.min(5, candidats.size()); i++) {
                    String cand = candidats.get(i);
                    int dist = Levenshtein.distance(mot, cand);
                    System.out.println(" --> " + cand + " (distance = " + dist + ")");
                }
            }
        }

        long fin = System.nanoTime();
        System.out.printf("\nTemps total : %.3f s%n", (fin - debut) / 1e9);
    }
}
