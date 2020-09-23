package generator;

import org.nfunk.jep.function.Str;
import project.Animal;
import project.Bicycle;
import project.Lane;
import project.Motorbike;
import project.Pedestrian_crossing;
import project.Person;
import project.Truck;
import project.Vehicle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SizeManager {
    public static final int METERS_TO_PX = 20;
    private static final Map<String,Float> lengthMap;
    private static final Map<String,Float> widthMap;

    static {
        Map<String,Float> length = new HashMap<>();
        Map<String,Float> width = new HashMap<>();

        width.put("lane", 45F);

        length.put("obstacle", 20F);
        width.put("obstacle", 50F);

        length.put("person", 60F);
        width.put("person", 60F);

        length.put("animal", 20F);
        width.put("animal", 60F);

        length.put("vehicle", 500F);
        width.put("vehicle", 200F);

        length.put("truck", 770F);
        width.put("truck", 230F);

        length.put("motorbike", 300F);
        width.put("motorbike", 50F);

        length.put("bicycle", 270F);
        width.put("bicycle", 50F);

        length.put("pedestrian_crossing", 200F);
        width.put("pedestrian_crossing", 400F);

        lengthMap = Collections.unmodifiableMap(length);
        widthMap = Collections.unmodifiableMap(width);
    }

    public float getLength(String str) {
        return lengthMap.getOrDefault(str, 0F);
    }

    public float getWidth(String str) {
        return widthMap.getOrDefault(str, 0F);
    }

    public static float metersToPx(float meters) {
        return meters * METERS_TO_PX;
    }

    public static float centimetersToPx(float centimeters) {
        return centimeters * METERS_TO_PX / 100;
    }
}