package generator;


import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Animal;
import project.On_the_lane;
import project.On_the_road;
import project.Passenger;
import project.Road_type;
import project.Surrounding;
import project.Time;
import project.Vehicle;
import project.Weather;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;


public class RandomSubclassGeneratorTest {
    RandomSubclassGenerator randomSubclassGenerator = new RandomSubclassGenerator(MyFactorySingleton.getFactory());

    public RandomSubclassGeneratorTest() throws FileNotFoundException, OWLOntologyCreationException {}

    @Test
    public void testTypesOfReturnedClasses() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        assertThat(randomSubclassGenerator.generateVehicleSubclass(), instanceOf(Vehicle.class));
        assertThat(randomSubclassGenerator.generateWeatherSubclass(), instanceOf(Weather.class));
        assertThat(randomSubclassGenerator.generateAnimalSubclass(), instanceOf(Animal.class));
        assertThat(randomSubclassGenerator.generateTimeSubclass(), instanceOf(Time.class));
        assertThat(randomSubclassGenerator.generateRoadTypeSubclass(), instanceOf(Road_type.class));
        assertThat(randomSubclassGenerator.generateSurroundingSubclass(), instanceOf(Surrounding.class));
        assertThat(randomSubclassGenerator.generateSurroundingOnRoadSubclass(), instanceOf(On_the_road.class));
        assertThat(randomSubclassGenerator.generateSurroundingOnLaneSubclass(), instanceOf(On_the_lane.class));
        assertThat(randomSubclassGenerator.generatePassengerSubclass(), instanceOf(Passenger.class));
    }
}
