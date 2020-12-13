package commonadapter.adapters.nds;

import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;

class Coordinates {
    private Double latitude;
    private Double longitude;

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
            Logger.printLogMessage(String.format("Incorrect latitude given: %f Must be from range [-90,90]", latitude), LogMessageType.ERROR);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        if (Math.abs(longitude) > 180) {
            Logger.printLogMessage(String.format("Incorrect longitude given: %f Must be from range [-180,180]", longitude), LogMessageType.ERROR);
        }
    }

    @Override
    public String toString() {
        String lat = this.latitude > 0 ? this.latitude + "N" : (-this.latitude) + "S";
        String lon = this.longitude > 0 ? this.longitude + "E" : (-this.longitude) + "W";
        return String.format("%s %s", lat, lon);
    }


    public static Coordinates calculateTileCoordinates(String tileId) {
        int tileDec = Integer.parseInt(tileId);
        StringBuilder tileBin = new StringBuilder(Integer.toBinaryString(tileDec));

        //removing first three bits (tile level)
        StringBuilder sb = new StringBuilder(tileBin.toString());
        for (int i = 0; i < 5; i++) {
            sb.deleteCharAt(0);
        }
        tileBin = new StringBuilder(sb.toString());

        //adding "0"'s to tile number
        while (tileBin.length() < 27) {
            tileBin.insert(0, "0");
        }

        //decoding Morton code
        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();
        for (int i = 0; i < tileBin.length(); i++) {
            if (i % 2 == 0) {
                x.append(tileBin.charAt(i));
            } else {
                y.append(tileBin.charAt(i));
            }
        }

        //shifting coordinates
        String newX = "";
        String newY = "";
        boolean changedX = false;
        boolean changedY = false;


        if (String.valueOf(x.charAt(0)).equals("1")) {
            changedX = true;
            newX = decodeComplementTwo(x.toString());
        }

        if (String.valueOf(y.charAt(0)).equals("1")) {
            changedY = true;
            newY = decodeComplementTwo(y.toString());
        }

        if (changedX) {
            x = new StringBuilder(newX);
        }

        if (changedY) {
            y = new StringBuilder(newY);
        }

        x.append("000000000000000000");
        y.append("000000000000000000");

        //converting to decimal
        long decimalValueX = Long.parseLong(x.toString(), 2);
        long decimalValueY = Long.parseLong(y.toString(), 2);

        //converting to coordinates

        Coordinates finalCoor = convertToCoordinates(decimalValueX, decimalValueY);

        if (changedX) {
            finalCoor.setLongitude(-finalCoor.getLongitude());
        }

        if (changedY) {
            finalCoor.setLatitude(-finalCoor.getLatitude());
        }

        return finalCoor;
    }

    public static Coordinates calculateIntersectionCoordinates(Coordinates tileCenterCoordinates, int x_shift, int y_shift) {
        double x = tileCenterCoordinates.getLongitude();
        double y = tileCenterCoordinates.getLatitude();

        long decimalX = (long) (((x / 180) * 2147483648.0) + x_shift);
        long decimalY = (long) (((y / 90) * 1073741824.0) + y_shift);

        return convertToCoordinates(decimalX, decimalY);
    }

    private static String decodeComplementTwo(String coor) {
        long tmp2 = Long.parseLong(coor, 2);
        tmp2 = tmp2 - 1;
        String transformedCoor = Long.toBinaryString(tmp2);
        StringBuilder newCoor = new StringBuilder();
        for (int i = 0; i < transformedCoor.length(); i++) {
            if (String.valueOf(transformedCoor.charAt(i)).equals("0")) {
                newCoor.append("1");
            } else {
                newCoor.append("0");
            }
        }
        return newCoor.toString();
    }

    private static Coordinates convertToCoordinates(long x, long y) {
        double coorXratio = x / 2147483648.0;
        double coorYratio = y / 1073741824.0;

        double coorX = coorXratio * 180;
        double coorY = coorYratio * 90;

        return new Coordinates(coorX, coorY);
    }
}