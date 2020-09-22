package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import visualization.Visualization;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class BuildersTest {
    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseScenarioGenerator baseScenarioGenerator = new BaseScenarioGenerator(MyFactorySingleton.getFactory(), MyFactorySingleton.baseIRI);
        Model baseModel = baseScenarioGenerator.generate();
        Visualization.getImage(baseModel);

        ScenarioFactory scenarioFactory = new ScenarioFactory(baseModel);
        Model model1 = scenarioFactory
                .animalOnRoad()
                .pedestrianOnCrossing()
                .obstacleOnRoad()
                .getModel();
        Visualization.getImage(model1);

        Model model2 = new ModelBuilder(model1)
                .addAnimals(3)
                .addCar()
                .getModel();
        Visualization.getImage(model2);
    }
}
