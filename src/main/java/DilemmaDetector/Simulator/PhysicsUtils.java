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

    public static double Kmph2ToMps2(double n) {
        return n/3.6/3600;
    }

    public static Vector2 GetRelativeSpeed(Vector2 vec1, Vector2 vec2)
    {
        return new Vector2(vec1).sub(vec2);
    }
}
