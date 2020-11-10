package generator;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Car;
import project.Driver;
import project.Road_type;
import project.Scenario;
import project.Surrounding;
import project.Time;
import project.Weather;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class BaseScenarioGeneratorTest {

    @Test
    public void testIfAllObjectsWereCreated() throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseScenarioGenerator baseScenarioGenerator = new BaseScenarioGenerator();
        Model model = baseScenarioGenerator.generate();

        assertThat(model.getScenario(), instanceOf(Scenario.class));
        assertThat(model.getWeather(), instanceOf(Weather.class));
        assertThat(model.getTime(), instanceOf(Time.class));
        assertThat(model.getRoadType(), instanceOf(Road_type.class));
        assertThat(model.getDriver(), instanceOf(Driver.class));
        assertThat(model.getVehicle(), instanceOf(Car.class));
        Map<Model.Side, ArrayList<Surrounding>> surrounding = model.getSurrounding();
        for(Object o : surrounding.get(Model.Side.LEFT))
            assertThat(o, instanceOf(Surrounding.class));
        for(Object o : surrounding.get(Model.Side.RIGHT))
        assertThat(o, instanceOf(Surrounding.class));
    }
}
