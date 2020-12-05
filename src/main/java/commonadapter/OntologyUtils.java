package commonadapter;

public class OntologyUtils {

    public static final String IRI_PREFIX = "http://www.w3.org/2003/11/";

    public static String getPrefixedId(String id) {
        return OntologyUtils.IRI_PREFIX + id;
    }

}
