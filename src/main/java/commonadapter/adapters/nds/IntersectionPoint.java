package commonadapter.adapters.nds;

public class IntersectionPoint {
    private String intersectionId;
    private Coordinates coordinates;

    public IntersectionPoint(String intersectionId, Coordinates coordinates) {
        this.intersectionId = intersectionId;
        this.coordinates = coordinates;
    }

    public String getIntersectionId() {
        return intersectionId;
    }

    public void setIntersectionId(String intersectionId) {
        this.intersectionId = intersectionId;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}