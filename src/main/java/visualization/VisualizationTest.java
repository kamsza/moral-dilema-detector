package visualization;

import generator.BaseScenarioGenerator;
import generator.Model;
import generator.ScenarioFactory;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class VisualizationTest {

    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseScenarioGenerator baseScenarioGenerator = new BaseScenarioGenerator();
        for(int i = 0; i < 50; i++) {
            Model baseModel = baseScenarioGenerator.generate();

            ScenarioFactory scenarioFactory = new ScenarioFactory(baseModel);
            Model model1 = scenarioFactory
                    .animalOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .obstacleOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .pedestrianOnCrossing(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .pedestrianJaywalking(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .getModel();
            Visualization.getImage(model1);
        }
    }
}