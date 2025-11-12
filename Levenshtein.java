public class Levenshtein {

    // Calcule la distance d'édition entre deux mots
    public static int distance(String mot1, String mot2) {
        int n = mot1.length();
        int m = mot2.length();

        // tableau de taille (n+1) x (m+1)
        int[][] d = new int[n + 1][m + 1];

        // initialisation des bords
        for (int i = 0; i <= n; i++) d[i][0] = i;
        for (int j = 0; j <= m; j++) d[0][j] = j;

        // remplissage
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cout = (mot1.charAt(i - 1) == mot2.charAt(j - 1)) ? 0 : 1;

                d[i][j] = Math.min(
                        Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1),
                        d[i - 1][j - 1] + cout
                );
            }
        }

        return d[n][m];
    }
}
