package generator;

import java.util.Random;

public class RandomDistanceGenerator {
    private final int max; // distance in cm
    private final int min; // distance in cm
    private Random random = new Random();

    public static void main(String[] args) {
        RandomDistanceGenerator rdg = new RandomDistanceGenerator();
        System.out.println(rdg.getRandomDistance());
        System.out.println(rdg.getRandomDistance(100));
    }

    public RandomDistanceGenerator() {
        this.max = 2700;
        this.min = 300;
    }

    public RandomDistanceGenerator(int max, int min) {
        this.min = min;
        this.max = max;
    }

    // return distance in cm
    public float getRandomDistance() {
        return (float) random.nextInt(max) + min;
    }

    public float getRandomDistance(int bound) {
        if (bound < min) {
            // Bound cannot be lower than minimum value
            return min;
        }
        if (bound > max) {
            return getRandomDistance();
        } else {
            return (float) random.nextInt(bound) + min;
        }
    }
}
