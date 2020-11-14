package commonadapter;

public class CommunicationUtils {

    public static final String GLOBAL_ICE_CATEGORY = "adapter";

    public static String getInternetAddress(String id) {
        return GLOBAL_ICE_CATEGORY + "/" + id + ":tcp -h localhost -p 10000";
    }
}
