package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class ScenarioFactory {
    private ModelBuilder modelBuilder;

    public ScenarioFactory(Model model) throws FileNotFoundException, OWLOntologyCreationException {
        this.modelBuilder = new ModelBuilder(model);
    }

    // CAR - ANIMAL SCENARIOS
    public ScenarioFactory animalOnRoad() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        modelBuilder.addAnimal(true);
        return this;
    }

    public ScenarioFactory animalOnRoad(int[] objectsNum, double[] prob) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        modelBuilder.addAnimal(objectsNum, prob, true);
        return this;
    }

    // CAR - CAR SCENARIOS
    public ScenarioFactory carApproaching() {
        modelBuilder.addApproachedVehicle ();
        return this;
    }

    public ScenarioFactory carOvertaking() {
        modelBuilder.addOvertakenVehicle();
        return this;
    }

    // CAR - OBSTACLE SCENARIOS
    public ScenarioFactory obstacleOnRoad() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        modelBuilder.addObstacle(true);
        return this;
    }

    public ScenarioFactory obstacleOnRoad(int[] objectsNum, double[] prob) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        modelBuilder.addObstacle(objectsNum, prob, true);
        return this;
    }

    // CAR - PERSON SCENARIOS
    public ScenarioFactory pedestrianOnCrossing(int[] objectsNum, double[] prob) {
        modelBuilder.addPedestrianCrossing(objectsNum, prob);
        return this;
    }

    public ScenarioFactory pedestrianJaywalking() {
        modelBuilder.pedestrianJaywalking(true);
        return this;
    }

    public ScenarioFactory pedestrianJaywalking(int[] objectsNum, double[] prob) {
        modelBuilder.pedestrianJaywalking(objectsNum, prob, true);
        return this;
    }

    // AUXILIARY FUNCTIONS
    public Model getModel() {
        return this.modelBuilder.getModel();
    }
}
