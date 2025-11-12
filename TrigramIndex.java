import java.util.*;

public class TrigramIndex {
    private Map<String, List<String>> index = new HashMap<>();
    private Set<String> dictionnaire = new HashSet<>();

    // ajoute un mot dans l’index
    public void ajouterMot(String mot) {
        dictionnaire.add(mot);
        String w = "<" + mot + ">";
        for (int i = 0; i < w.length() - 2; i++) {
            String tri = w.substring(i, i + 3);
            index.computeIfAbsent(tri, k -> new ArrayList<>()).add(mot);
        }
    }

    // construit l’index depuis une liste
    public void construire(List<String> mots) {
        for (String m : mots) ajouterMot(m.toLowerCase());
    }

    // sélectionne les mots qui partagent le plus de trigrammes
    public List<String> candidats(String mot, int max) {
        mot = mot.toLowerCase();
        Map<String, Integer> compte = new HashMap<>();
        String w = "<" + mot + ">";

        for (int i = 0; i < w.length() - 2; i++) {
            String tri = w.substring(i, i + 3);
            List<String> mots = index.get(tri);
            if (mots == null) continue;
            for (String m : mots) compte.merge(m, 1, Integer::sum);
        }

        // tri des meilleurs
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(compte.entrySet());
        entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(max, entries.size()); i++)
            result.add(entries.get(i).getKey());
        return result;
    }

    public boolean contient(String mot) {
        return dictionnaire.contains(mot.toLowerCase());
    }
}
