package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class ScenarioFactory {
    private ModelBuilder modelBuilder;

    public ScenarioFactory(Model model) throws FileNotFoundException, OWLOntologyCreationException {
        this.modelBuilder = new ModelBuilder(model);
    }

    public ScenarioFactory animalOnRoad() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        modelBuilder.addAnimal();
        return this;
    }

    public ScenarioFactory carApproaching() {
        // TODO: dodac auto bezposrednio przed naszym pojazdem
        modelBuilder.addCar();
        return this;
    }

    public ScenarioFactory carOvertaking() {
        // TODO: dodac auto, ktore nas wyprzedza na lewym pasie
        modelBuilder.addCar();
        return this;
    }

    public ScenarioFactory obstacleOnRoad() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        modelBuilder.addObstacle();
        return this;
    }

    public ScenarioFactory pedestrianOnCrossing() {
        modelBuilder.addPedestrianCrossing();
        return this;
    }

    public ScenarioFactory pedestrianJaywalking() {
        modelBuilder.pedestrianJaywalking();
        return this;
    }

    public Model getModel() {
        return this.modelBuilder.getModel();
    }
}
