package commonadapter.adapters.nds;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinatesTest {


    @Test
    public void calculateTileCoordinates(){

        Coordinates test1 = new Coordinates(11.66748046875,48.09814453125);
        Coordinates test2 = new Coordinates(11.6455078125,48.1201171875);
        Coordinates test3 = new Coordinates(11.66748046875,48.1201171875);
        Coordinates test4 = new Coordinates(16.435546875,47.4609375);
        Coordinates test5 = new Coordinates(16.3037109375,47.724609375);
        Coordinates test6 = new Coordinates(16.611328125,47.5048828125);

        assertEquals(test1.getLatitude(), Coordinates.calculateTileCoordinates("545554855").getLatitude(), 0.0);
        assertEquals(test1.getLongitude(), Coordinates.calculateTileCoordinates("545554855").getLongitude(), 0.0);

        assertEquals(test2.getLatitude(), Coordinates.calculateTileCoordinates("545554860").getLatitude(), 0.0);
        assertEquals(test2.getLongitude(), Coordinates.calculateTileCoordinates("545554860").getLongitude(), 0.0);

        assertEquals(test3.getLatitude(), Coordinates.calculateTileCoordinates("545554861").getLatitude(), 0.0);
        assertEquals(test3.getLongitude(), Coordinates.calculateTileCoordinates("545554861").getLongitude(), 0.0);

        assertEquals(test4.getLatitude(), Coordinates.calculateTileCoordinates("545554000").getLatitude(), 0.0);
        assertEquals(test4.getLongitude(), Coordinates.calculateTileCoordinates("545554000").getLongitude(), 0.0);

        assertEquals(test5.getLatitude(), Coordinates.calculateTileCoordinates("545554100").getLatitude(), 0.0);
        assertEquals(test5.getLongitude(), Coordinates.calculateTileCoordinates("545554100").getLongitude(), 0.0);

        assertEquals(test6.getLatitude(), Coordinates.calculateTileCoordinates("545554200").getLatitude(), 0.0);
        assertEquals(test6.getLongitude(), Coordinates.calculateTileCoordinates("545554200").getLongitude(), 0.0);
    }

}
