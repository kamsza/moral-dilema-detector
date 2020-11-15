package commonadapter.adapters.nds;

import java.util.logging.Level;
import java.util.logging.Logger;

class Coordinates {
    private Double latitude;
    private Double longitude;
    Logger logger = Logger.getLogger(Coordinates.class.getName());

    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        if (Math.abs(latitude) > 90) {
            logger.log(Level.WARNING, String.format("Incorrect latitude given: %f Must be from range [-90,90]", latitude));
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        if (Math.abs(longitude) > 180) {
            logger.log(Level.WARNING, String.format("Incorrect longitude given: %f Must be from range [-180,180]", longitude));
        }
    }

    @Override
    public String toString() {
        String lat = this.latitude > 0 ? this.latitude + "N" : (-this.latitude) + "S";
        String lon = this.longitude > 0 ? this.longitude + "E" : (-this.longitude) + "W";
        return String.format("%s %s", lat, lon);
    }
}