package DilemmaDetector;

import DilemmaDetector.Consequences.ConsequenceType;

import java.util.Map;

public class ParameterizedPhilosophy {
    public static final Map<ConsequenceType, Integer> healthValue = Map.of(
            ConsequenceType.KILLED, 10,
            ConsequenceType.SEVERELY_INJURED, 5,
            ConsequenceType.LIGHTLY_INJURED, 1
    );
    public static final int materialValue = 0;
    public static final int moralValue = 0;

    public static int humanLifeFactor = 100;
    public static int animalLifeFactor = 100;
    public static int materialFactor = 100;
    public static int moralFactor = 100;

}
