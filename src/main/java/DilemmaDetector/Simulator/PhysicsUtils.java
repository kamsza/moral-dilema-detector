package DilemmaDetector.Simulator;

public class PhysicsUtils {
    public static double CmToMeters(double n){
        return n / 100;
    }

    public static double KmphToMeters(double n){
        return n/3.6;
    }

    public static double MetersToKmph(double n){
        return n * 3.6;
    }
}
