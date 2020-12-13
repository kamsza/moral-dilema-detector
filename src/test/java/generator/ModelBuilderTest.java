package generator;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Animal;
import project.Living_entity;
import project.Non_living_entity;
import project.On_the_lane;
import project.On_the_road;
import project.Pedestrian_crossing;
import project.Person;
import project.Vehicle;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class ModelBuilderTest {
    BaseScenarioGenerator baseScenarioGenerator = new BaseScenarioGenerator();

    public ModelBuilderTest() throws FileNotFoundException, OWLOntologyCreationException {
    }

    @Test
    public void addAnimalTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException, OWLOntologyCreationException {
        Model model = baseScenarioGenerator.generate();
        model = new ModelBuilder(model).addAnimal(new int[] {1}, new double[] {1.0}).getModel();

        ArrayList<Object> entities = getAllEntities(model);
        assertEquals(1, entities.size());
        assertThat(entities.get(0), instanceOf(Animal.class));

        assertEquals(1, getAllVehicles(model).size());
        assertTrue(getAllObjects(model).isEmpty());
    }

    @Test
    public void addVehicleTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException, OWLOntologyCreationException {
        Model model = baseScenarioGenerator.generate();
        model = new ModelBuilder(model).addVehicles(new int[] {1}, new double[] {1.0}).getModel();

        ArrayList<Object> vehicles = getAllVehicles(model);
        // we should have main vehicle and added vegicle
        assertEquals(2, vehicles.size());
        assertThat(vehicles.get(0), instanceOf(Vehicle.class));
        assertThat(vehicles.get(1), instanceOf(Vehicle.class));

        assertTrue(getAllEntities(model).isEmpty());
        assertTrue(getAllObjects(model).isEmpty());
    }

    @Test
    public void addObstacleTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException, OWLOntologyCreationException {
        Model model = baseScenarioGenerator.generate();
        model = new ModelBuilder(model).addObstacle(new int[] {1}, new double[] {1.0}).getModel();

        ArrayList<Object> objects = getAllObjects(model);
        assertEquals(1, objects.size());
        assertTrue(objects.get(0) instanceof On_the_road || objects.get(0) instanceof On_the_lane);

        assertTrue(getAllEntities(model).isEmpty());
        assertEquals(1, getAllVehicles(model).size());

        assertEquals(1, getAllVehicles(model).size());
        assertTrue(getAllEntities(model).isEmpty());
    }

    @Test
    public void addPedestrianCrossingTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException, OWLOntologyCreationException {
        Model model = baseScenarioGenerator.generate();
        model = new ModelBuilder(model).addPedestrianCrossing(new int[] {1}, new double[] {1.0}).getModel();

        ArrayList<Object> entities = getAllEntities(model);
        assertEquals(1, entities.size());
        assertThat(entities.get(0), instanceOf(Person.class));

        ArrayList<Object> objects = getAllObjects(model);
        for(Object o : objects)
            assertThat(o, instanceOf(Pedestrian_crossing.class));

        assertEquals(1, getAllVehicles(model).size());
    }

    @Test
    public void addPedestrianJaywalkingTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException, OWLOntologyCreationException {
        Model model = baseScenarioGenerator.generate();
        model = new ModelBuilder(model).pedestrianJaywalking(new int[] {1}, new double[] {1.0}).getModel();

        ArrayList<Object> entities = getAllEntities(model);
        assertEquals(1, entities.size());
        assertThat(entities.get(0), instanceOf(Person.class));

        assertEquals(1, getAllVehicles(model).size());
        assertTrue(getAllObjects(model).isEmpty());
    }

    private ArrayList<Object> getAllEntities(Model model) {
        ArrayList<Object> entities = new ArrayList<>();

        for(ArrayList<Living_entity> lane_entities : model.getMainRoad().getEntities().values())
            entities.addAll(lane_entities);

        return entities;
    }

    private ArrayList<Object> getAllVehicles(Model model) {
        ArrayList<Object> vehicles = new ArrayList<>();

        for(ArrayList<Vehicle> lane_vehicles : model.getMainRoad().getVehicles().values())
            vehicles.addAll(lane_vehicles);

        return vehicles;
    }

    private ArrayList<Object> getAllObjects(Model model) {
        ArrayList<Object> objects = new ArrayList<>();

        for(ArrayList<Non_living_entity> object : model.getMainRoad().getObjects().values())
            objects.addAll(object);

        return objects;
    }
}
