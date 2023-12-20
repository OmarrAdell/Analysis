import java.util.Arrays;

public class assignment2 {

    public static void main(String[] args) {
        // Scoring matrix
        double[][] scoringMatrix = {
                {1, -0.8, -0.2, -2.3, -0.6},
                {-0.8, 1, -1.1, -0.7, -1.5},
                {-0.2, -1.1, 1, -0.5, -0.9},
                {-2.3, -0.7, -0.5, 1, -1},
                {-0.6, -1.5, -0.9, -1, Double.NEGATIVE_INFINITY}
        };

        // Test sequences
        String x = "ATGCC";
        String y = "TACGCA";
      //  String x = "TCCCAGTTATGTCAGGGGACACGAGCATGCAGAGAC";
      // String y = "AATTGCCGCCGTCGTTTTCAGCAGTTATGTCAGATC";

        Object[] result = sequenceAlignment(x, y, scoringMatrix);

        // Optimal Alignment
        System.out.println("Optimal Alignment:");
        System.out.println("Sequence 1: " + result[0]);
        System.out.println("Sequence 2: " + result[1]);
        System.out.println("Alignment Score: " + result[2]);
    }

    public static Object[] sequenceAlignment(String x, String y, double[][] scoringMatrix) {
        int n = x.length();
        int m = y.length();

        // Initialize the DP table with zeros
        double[][] dp = new double[n + 1][m + 1];
        for (int j = 1; j <= m; j++) {
            dp[0][j] = dp[0][j - 1] + scoringMatrix[4][charToIndex(y.charAt(j - 1))];
        }
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][0] + scoringMatrix[charToIndex(x.charAt(i - 1))][4];
        }
        // Fill in the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                char xChar = x.charAt(i - 1);
                char yChar = y.charAt(j - 1);

                double match = dp[i - 1][j - 1] + scoringMatrix[charToIndex(xChar)][charToIndex(yChar)];
                double delete = dp[i - 1][j] + scoringMatrix[charToIndex(xChar)][4];
                double insert = dp[i][j - 1] + scoringMatrix[4][charToIndex(yChar)];

                dp[i][j] = Math.max(match, Math.max(delete, insert));
            }
        }

        // Traceback to reconstruct the alignment
        StringBuilder alignX = new StringBuilder();
        StringBuilder alignY = new StringBuilder();
        int i = n, j = m;

        while (i > 0 || j > 0) {
            char xChar = (i > 0) ? x.charAt(i - 1) : '-';
            char yChar = (j > 0) ? y.charAt(j - 1) : '-';

            if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + scoringMatrix[charToIndex(xChar)][charToIndex(yChar)]) {
                alignX.insert(0, xChar);
                alignY.insert(0, yChar);
                i--;
                j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + scoringMatrix[charToIndex(xChar)][4]) {
                alignX.insert(0, xChar);
                alignY.insert(0, '-');
                i--;
            } else {
                alignX.insert(0, '-');
                alignY.insert(0, yChar);
                j--;
            }
        }

        double alignmentScore = dp[n][m];
        double threshold = 1e-10; // Adjust the threshold as needed
        if (Math.abs(alignmentScore) < threshold) {
        	alignmentScore = 0.0;
        }
    

        return new Object[]{alignX.toString(), alignY.toString(), alignmentScore};
    }

    private static int charToIndex(char c) {
        switch (c) {
            case 'A':
                return 0;
            case 'G':
                return 1;
            case 'T':
                return 2;
            case 'C':
                return 3;
            case '-':
                return 4;
            default:
                throw new IllegalArgumentException("Invalid character: " + c);
        }
    }
}
