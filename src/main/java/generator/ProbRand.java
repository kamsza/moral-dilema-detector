package generator;


import java.util.Random;

public class ProbRand {
    /**
     * Returns random element form array arr
     * Each element can be taken with probability prob
     * Probabilities in prob array must sum to 1 or less
     */
    static int randInt(int[] arr, double[] prob) {
        Random rand = new Random();
        int n = arr.length;

        if(prob.length != n)
            throw new IllegalArgumentException("Both arguments must have the same array size");

        double probSum = 0.0;
        for (int i = 0; i < n; i++)
            probSum += prob[i];

        if(probSum > 1.0)
            throw new IllegalArgumentException("Sum of probabilities must be less than or equal to 1.0");

        if(rand.nextDouble() > probSum)
            return -1;

        int[] prefix = new int[n+1];
        prefix[0] = 0;
        for (int i = 1; i <= n; i++)
            prefix[i] = prefix[i - 1] + (int)(prob[i] * 100);

        int r = rand.nextInt(3456) % prefix[n] + 1;
        for (int i = 1; i <= n; i++)
            if(prefix[i-1] < r && prefix[i] >= r)
                return arr[i];
        return -1;
    }
}
