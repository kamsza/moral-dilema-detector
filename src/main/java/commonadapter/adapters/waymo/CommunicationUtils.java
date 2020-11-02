package commonadapter.adapters.waymo;

public class CommunicationUtils {

    public static String getInternetAddress(String id) {
        return id + ":tcp -h localhost -p 10000";
    }
}
