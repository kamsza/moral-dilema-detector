package commonadapter.adapters.nds;

public class NdsUtils {
    public static String extractTileId(String jsonFilePath) {
        int size = jsonFilePath.length();
        return jsonFilePath.substring(size - 14, size - 5);
    }

    public static String extractRoadTileName(String jsonFilePath) {
        int size = jsonFilePath.length();
        return jsonFilePath.substring(size - 26, size - 15);
    }

    public static String extractLaneTileName(String jsonFilePath) {
        int size = jsonFilePath.length();
        return jsonFilePath.substring(size - 23, size - 15);
    }
}
