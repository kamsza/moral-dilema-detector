package commonadapter.adapters.nds;

public class NdsUtils {
    public static String extractTileId(String jsonFilePath) {
        int size = jsonFilePath.length();
        return jsonFilePath.substring(size - 14, size - 5);
    }
}
